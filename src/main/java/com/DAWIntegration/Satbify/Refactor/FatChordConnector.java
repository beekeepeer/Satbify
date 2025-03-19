package com.DAWIntegration.Satbify.Refactor;

import static com.DAWIntegration.Satbify.Chords.smoothBass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FatChordConnector {

    public ArrayList<FatChord> connect(List<List<FatChord>> in){
        FatChord[][] chordArray = in.stream().map(x -> x.stream()
                .toArray(FatChord[]::new)).toArray(FatChord[][]::new);

        return new ArrayList<>(Arrays.asList(findBestConnected(chordArray)));
    }


public FatChord[] findBestConnected(FatChord[][] arr) {
    int l = arr.length;
    int[] outIndexes = new int[l];
    FatChord[] out = new FatChord[l];
    // DP arrays to store the minimum smoothness and the path to reconstruct
    int[][] dp = new int[l][];
    int[][] path = new int[l][];
    // Initialize dp and path arrays
    for (int i = 0; i < l; i++) {
        if(arr[i].length == 0) {
            throw new UnsupportedOperationException("THIS CHORD HAS NO VERSIONS!!!!!!!!");
        }
        dp[i] = new int[arr[i].length];
        path[i] = new int[arr[i].length];
        // Fill dp[i] with a large number (since we are minimizing)
        Arrays.fill(dp[i], Integer.MAX_VALUE);
    }
    // Base case: for the first chord set, there's no previous chord, so smoothness is 0
    for (int j = 0; j < arr[0].length; j++) {
        dp[0][j] = 0; // No smoothness cost for the first chord
    }
    // Fill dp array using previously computed values
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
    // Find the best final chord with the minimum smoothness
    int minSmoothness = Integer.MAX_VALUE;
    int bestLastIndex = -1;
    for (int j = 0; j < arr[l - 1].length; j++) {
        if (dp[l - 1][j] < minSmoothness) {
            minSmoothness = dp[l - 1][j];
            bestLastIndex = j;
        }
    }
    // Reconstruct the best path
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

    private int calculateSmoothness(FatChord a, FatChord b){
        int sd, ad, td, bd, power = 3, result = 0;
        sd = a.getSoprano() - b.getSoprano();
        ad = a.getAlto() - b.getAlto();
        td = a.getTenor() - b.getTenor();
        bd = a.getBass() - b.getBass();

        if ((sd > 0 && bd > 0)  ||  (sd < 0 && bd < 0))     // decrease probability of parallel s and b
            result += 150;

        result = result + (int)((Math.pow(Math.abs(sd), power)
                + Math.pow(Math.abs(ad), power)
                + Math.pow(Math.abs(td), power)

        ));
        if (smoothBass) {
            result = (int) (result + Math.pow(Math.abs(bd), power));
        } else if (Math.abs(bd) > 6)
            result += 20;

        return result;
    }
    private boolean haveParallels(FatChord a, FatChord b) {
        //differences:
        int sd = a.getSoprano() - b.getSoprano();
        int ad = a.getAlto() - b.getAlto();
        int td = a.getTenor() - b.getTenor();
        int bd = a.getBass() - b.getBass();

        int[] intervals = {7, 12}; // over any octaves
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





