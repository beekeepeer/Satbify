package com.DAWIntegration.Satbify;

import com.DAWIntegration.Satbify.module.Alteration;
import com.DAWIntegration.Satbify.module.Chord;
import static com.DAWIntegration.Satbify.Chords.smoothBass;

import java.util.ArrayList;
import java.util.Arrays;


public class ChordConnector {
    static long counter = 0;

    public ArrayList<Chord> connect(ArrayList<ArrayList<Chord>> in){
        Chord[][] chordArray = in.stream().map(x -> x.stream()
//                .filter(chord -> (chord.absentFinalNotes() || chord.fitsFinalNote()))
                .map(this::adjustOctaves)
                .map(this::setFinalNots)
                .filter(chord -> chord.getAlteration() != Alteration.DD_Flat_IV)
                .toArray(Chord[]::new)).toArray(Chord[][]::new);

        return new ArrayList<>(Arrays.asList(findBestConnected(chordArray)));
    }


public Chord[] findBestConnected(Chord[][] arr) {
    int l = arr.length;
    int[] outIndexes = new int[l];
    Chord[] out = new Chord[l];
    // DP arrays to store the minimum smoothness and the path to reconstruct
    int[][] dp = new int[l][];
    int[][] path = new int[l][];
    if (l == 1) return new Chord [] {arr [0][0]}; // todo: if array length is 1 - return one random chord. The problem is not here.
    // Initialize dp and path arrays
    for (int i = 0; i < l; i++) {
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
        currentIndex = path[i][currentIndex]; // Move to the previous index
    }
    // Build the result based on the outIndexes
    for (int i = 0; i < l; i++) {
        out[i] = arr[i][outIndexes[i]];
    }
    return out;
}








    private int calculateSmoothness(Chord a, Chord b){
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
    private boolean haveParallels(Chord a, Chord b) {
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
    private Chord adjustOctaves(Chord chord){

        double highestSoprano = 84, lowestBass = 35;
        double tessitura = chord.getTessitura();
        double averagePitch = ((double) chord.getSoprano()
                + chord.getAlto()
                + chord.getTenor())
                / 3;
        if (averagePitch == tessitura || Math.abs(averagePitch - tessitura) < 7.0) {
            return chord;
        }
        return (averagePitch < tessitura) ?
                adjustOctaves(OctaveUpDown(chord,  true)) : // recurse
                adjustOctaves(OctaveUpDown(chord, false)); // recurse

    }

    // harmonizing existing notes. It shifts tessitura for only one chord.
    private Chord setFinalNots(Chord chord) {
        var difCommon = getDifCommon(chord);
        if (difCommon != 0) { // if this chord should harmonize Specified Notes
            if (difCommon % 12 == 0) { // if differences can be used to shift by octave, AND are equal.
                chord.setSoprano(chord.getSoprano() + difCommon);
                chord.setAlto(chord.getAlto() + difCommon);
                chord.setTenor(chord.getTenor() + difCommon);
                chord.setBass(chord.getBass() + difCommon);
                return chord;
            } else { // differences are no octaves - this is the wrong Chord!!!
                chord.setAlteration(Alteration.DD_Flat_IV); // todo: it is temporary! change to a different way of filtering.
                return chord;
            }
        } else { // This Chord should not be touched!!! Return the original.
            return chord;
        }
    }

    private static int getDifCommon(Chord chord) {
        var difS = 0;
        var difA = 0;
        var difT = 0;
        var difB = 0;
        if (chord.getFinalSoprano() != 0) {
            difS = chord.getFinalSoprano() - chord.getSoprano();
        }
        if (chord.getFinalAlto() != 0) {
            difA = chord.getFinalAlto() - chord.getAlto();
        }
        if(chord.getFinalTenor() != 0){
            difT = chord.getFinalTenor() - chord.getTenor();
            }
        if(chord.getFinalBass() != 0) {
            difB = chord.getFinalBass() - chord.getBass();
        }
        return suitableChord(difS, difA, difT, difB);
    }

    public static int suitableChord(int difS, int difA, int difT, int difB) {
        // Count non-zero values
        int[] values = {difS, difA, difT, difB};
        int nonZeroCount = 0;
        int nonZeroValue = 0;
        boolean allEqual = true;

        for (int value : values) {
            if (value != 0) {
                nonZeroCount++;
                if (nonZeroValue == 0) {
                    nonZeroValue = value;
                } else if (value != nonZeroValue) {
                    allEqual = false;
                }
            }
        }

        // not clever, but clear:
        if (nonZeroCount == 0) {
            return 0; // All are zero
        } else if (nonZeroCount == 1) {
            return nonZeroValue; // Only one is non-zero
        } else if (allEqual) {
            return nonZeroValue; // All non-zero values are equal
        } else {
            return 13; // This chord doesn't fit
        }
    }

    private Chord OctaveUpDown(Chord a, boolean b) {
        var o = 12;
        a.setSoprano(a.getSoprano() + (b ? o : -o));
        a.setAlto(a.getAlto() + (b ? o : -o));
        a.setTenor(a.getTenor() + (b ? o : -o));
        a.setBass(a.getBass() + (b ? o : -o));
        return a;
    }
}





