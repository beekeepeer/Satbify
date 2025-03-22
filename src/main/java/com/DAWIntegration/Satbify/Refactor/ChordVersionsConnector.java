package com.DAWIntegration.Satbify.Refactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

/**
 * This class select the best sequence of chord versions from a two-dimensional list.
 * It evaluates each possible combination utilizing a set of rules.
 */

public class ChordVersionsConnector {

    public static ChordVersionsConnector getInstance() {
        return new ChordVersionsConnector();
    }

    public List<FatChord> connectChords(List<List<FatChord>> versions, List<Note> allKS) {
        
        FatChord[][] chordArray = versions.stream().map(x -> x.stream()
                .toArray(FatChord[]::new)).toArray(FatChord[][]::new);

        return new ArrayList<>(Arrays.asList(findBestConnected(chordArray)));
    }
    // Main algorithm method with dynamic programming approach
    private FatChord[] findBestConnected(FatChord[][] arr) {
        int l = arr.length;
        int[] outIndexes = new int[l];
        FatChord[] out = new FatChord[l];
        // DP arrays to store the minimum smoothness and the path to reconstruct
        int[][] dp = new int[l][];
        int[][] path = new int[l][];
        initializeArrays(arr, l, dp, path);
        zeroSmoothness(arr, dp);
        fillWithSmoothness(arr, l, dp, path);
        int bestLastIndex = getBestLastIndex(arr, l, dp);
        return getCurrentIndex(arr, l, outIndexes, out, path, bestLastIndex);
    }

        // Base case: for the first chord set, there's no previous chord, so smoothness is 0
    private void zeroSmoothness(FatChord[][] arr, int[][] dp) {
        for (int j = 0; j < arr[0].length; j++) {
            dp[0][j] = 0; // No smoothness cost for the first chord
        }
    }

        // Initialize dp and path arrays
    private void initializeArrays(FatChord[][] arr, int l, int[][] dp, int[][] path) {
        for (int i = 0; i < l; i++) {
            if (arr[i].length == 0) {
                throw new UnsupportedOperationException("THIS CHORD HAS NO VERSIONS!!!!!!!!");
            }
            dp[i] = new int[arr[i].length];
            path[i] = new int[arr[i].length];
            // Fill dp[i] with a large number (since we are minimizing)
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
    }

        // Fill dp array using previously computed values
    private void fillWithSmoothness(FatChord[][] arr, int l, int[][] dp, int[][] path) {
        for (int i = 1; i < l; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                for (int k = 0; k < arr[i - 1].length; k++) {
                    int currentSmoothness = dp[i - 1][k] + calculateSmoothness(arr[i - 1][k], arr[i][j]);
                    if (currentSmoothness < dp[i][j] && !haveParallels(arr[i - 1][k], arr[i][j])) { // eliminate parallels
                        dp[i][j] = currentSmoothness;
                        path[i][j] = k; // Record the index that led to this minimum
                    }
                }
            }
        }
    }

        // Reconstruct the best path
    private FatChord[] getCurrentIndex(FatChord[][] arr, int l, int[] outIndexes, FatChord[] out, int[][] path, int bestLastIndex) {
        int currentIndex = bestLastIndex;
        for (int i = l - 1; i >= 0; i--) {
            outIndexes[i] = currentIndex;
            currentIndex = path[i][currentIndex];
        }
        // Build the result based on the outIndexes
        for (int i = 0; i < l; i++) {
            out[i] = arr[i][outIndexes[i]];
        }
        return out;
    }

        // Find the best final chord with the minimum smoothness
    private int getBestLastIndex(FatChord[][] arr, int l, int[][] dp) {
        int minSmoothness = Integer.MAX_VALUE;
        int bestLastIndex = -1;
        for (int j = 0; j < arr[l - 1].length; j++) {
            if (dp[l - 1][j] < minSmoothness) {
                minSmoothness = dp[l - 1][j];
                bestLastIndex = j;
            }
        }
        return bestLastIndex;
    }

    private int calculateSmoothness(FatChord a, FatChord b) {
        int sd, ad, td, bd, power = 3, result = 0;
        sd = a.getSoprano() - b.getSoprano();
        ad = a.getAlto() - b.getAlto();
        td = a.getTenor() - b.getTenor();
        bd = a.getBass() - b.getBass();

        if ((sd > 0 && bd > 0) || (sd < 0 && bd < 0)) // decrease probability of parallel s and b
            result += 150;

        result = result + (int) ((Math.pow(Math.abs(sd), power)
                + Math.pow(Math.abs(ad), power)
                + Math.pow(Math.abs(td), power)
                + Math.pow(Math.abs(bd), power)

        ));
        return result;
    }

    private boolean haveParallels(FatChord a, FatChord b) {
        // differences:
        int sd = a.getSoprano() - b.getSoprano();
        int ad = a.getAlto() - b.getAlto();
        int td = a.getTenor() - b.getTenor();
        int bd = a.getBass() - b.getBass();

        int[] intervals = { 7, 12 }; // over any octaves
        for (int forbidden : intervals) {
            if ((a.getSoprano() - a.getAlto()) % forbidden == 0
                    && sd == ad) {
                return true;
            }
            if ((a.getSoprano() - a.getTenor()) % forbidden == 0
                    && sd == td) {
                return true;
            }
            if ((a.getSoprano() - a.getBass()) % forbidden == 0
                    && sd == bd) {
                return true;
            }
            if ((a.getAlto() - a.getTenor()) % forbidden == 0
                    && ad == td) {
                return true;
            }
            if ((a.getAlto() - a.getBass()) % forbidden == 0
                    && ad == bd) {
                return true;
            }
            if ((a.getTenor() - a.getBass()) % forbidden == 0
                    && td == bd) {
                return true;
            }
        }
        return false;
    }
}
