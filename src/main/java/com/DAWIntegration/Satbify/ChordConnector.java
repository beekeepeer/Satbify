package com.DAWIntegration.Satbify;

import com.DAWIntegration.Satbify.module.Alteration;
import com.DAWIntegration.Satbify.module.Chord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;


// Принимать решение о выборе лучшей пары для соединения
// на основании оценки плавности, и средней велечены предыдущего голоса.
// и в дальнейшем придерживаться этой оценки,для того, чтобы не отклоняться от примерной нормальной высоты всего произведения.

public class ChordConnector {

    public ArrayList<Chord> connect(ArrayList<ArrayList<Chord>> in){
        Chord[][] chordArray = in.stream().map(x -> x.stream()
//                .filter(chord -> (chord.absentFinalNotes() || chord.fitsFinalNote()))
                .map(this::adjustOctaves)
                .map(this::setFinalNots)
                .filter(chord -> chord.getAlteration() != Alteration.DD_Flat_IV)
                .toArray(Chord[]::new)).toArray(Chord[][]::new);

        return findBestConnectedChords(chordArray);
    }
    public ArrayList<Chord> findBestConnectedChords(Chord[][] arrays) {    // todo: not tested properly!!!
        int n = arrays.length;
        int[][] dp = new int[n][];
        int[][] backtrack = new int[n][];

        // Initialize dp and backtrack arrays
        for (int i = 0; i < n; i++) {
            int len = arrays[i].length;
            dp[i] = new int[len];
            backtrack[i] = new int[len];
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        // Fill dp array for the first array
        for (int j = 0; j < arrays[0].length; j++) {
            dp[0][j] = 0; // No previous arrays to compare, so initialize with 0
        }

        // Fill dp array for other arrays
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                for (int k = 0; k < arrays[i - 1].length; k++) {
                    if (haveParallels(arrays[i - 1][k], arrays[i][j])) continue;    // todo: not tested properly!!!
//                    if(arrays[i - 1][k].requiresFinalNote()) {
//                        if (arrays[i - 1][k].containsFinalNote()) {
//                            System.out.println("Condition is met!");
//                            arrays[i - 1][k] = setFinalNots(arrays[i - 1][k]);
//                        } else continue;
//                    }
                    int connectionCost = calculateSmoothness(arrays[i - 1][k], arrays[i][j]);
//                    System.out.println("connectionCost: " + connectionCost);
                    if (connectionCost < dp[i][j]) {
                        dp[i][j] = connectionCost;
                        backtrack[i][j] = k;
                    }
                }
            }
        }

        // Find the best last chord in the last array
        int minIndex = 0;
        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < arrays[n - 1].length; j++) {
            if (dp[n - 1][j] < minCost) {
                minCost = dp[n - 1][j];
                minIndex = j;
            }
        }

        // Reconstruct the path
        Chord[] result = new Chord[n];
        int currentIndex = minIndex;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = arrays[i][currentIndex];
            if (i > 0) {
                currentIndex = backtrack[i][currentIndex];
            }
        }

        return new ArrayList<>(Arrays.asList(result));
    }
    private int calculateSmoothness(Chord a, Chord b){
        int sd, ad, td, bd, power = 3, result = 0;
        sd = a.getSoprano() - b.getSoprano();
        ad = a.getAlto() - b.getAlto();
        td = a.getTenor() - b.getTenor();
        bd = a.getBass() - b.getBass();

        if ((sd > 0 && bd > 0)  ||  (sd < 0 && bd < 0))     // decrease probability of parallel s and b
            result += 100;
        if(Math.abs(bd) > 6)                                // smother bass
            result += 20;

        result = result + (int)((Math.pow(Math.abs(sd), power)
                + Math.pow(Math.abs(ad), power)
                + Math.pow(Math.abs(td), power)
//                + Math.pow(Math.abs(bd), power)
        ));

        return result;
    }
    private boolean haveParallels(Chord a, Chord b) {
        //differences:
        int sd = a.getSoprano() - b.getSoprano();
        int ad = a.getAlto() - b.getAlto();
        int td = a.getTenor() - b.getTenor();
        int bd = a.getBass() - b.getBass();

        int[] intervals = {7, 12};
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
        double standard = chord.getStandard();
         // todo: make it non final for harmonizing given notes.
        double averagePitch = ((double) chord.getSoprano()
                + chord.getAlto()
                + chord.getTenor())
                / 3;
        if (averagePitch == standard || Math.abs(averagePitch - standard) < 7.0) {
            return chord;
        }
        return (averagePitch < standard) ?
                adjustOctaves(OctaveUpDown(chord,  true)) : // recurse
                adjustOctaves(OctaveUpDown(chord, false)); // recurse

    }
    // harmonizing existing notes. It shifts
    private Chord setFinalNots(Chord chord) { // todo: test it!!!!
        var difS = 0;
        var difA = 0;
        var difT = 0;
        var difB = 0;
        var counter = 0;
        var difCommon = 0;
//        boolean sb = false, ab = false, tb = false, bb = false;

        if (chord.getFinalSoprano() != 0) {
            difS = chord.getFinalSoprano() - chord.getSoprano();
            counter++;
        }
        if (chord.getFinalAlto() != 0) {
            difA = chord.getFinalAlto() - chord.getAlto();
            counter++;
        }
        if(chord.getFinalTenor() != 0){
            difT = chord.getFinalTenor() - chord.getTenor();
            counter++;
            }
        if(chord.getFinalBass() != 0) {
            difB = chord.getFinalBass() - chord.getBass();
            counter++;
        }

        var sum = difS + difA + difT + difB;


        if (sum != 0) { // if this chord should follow Specified Notes
            difCommon = sum / counter; // get common difference
            if (difCommon % 12 == 0) { // if differences can be used to shift by octave, AND are equal.
                chord.setSoprano(chord.getSoprano() + difCommon);
                chord.setAlto(chord.getAlto() + difCommon);
                chord.setTenor(chord.getTenor() + difCommon);
                chord.setBass(chord.getBass() + difCommon);
                return chord;
            } else { // differences are no octaves - this is the wrong Chord!!!
                chord.setAlteration(Alteration.DD_Flat_IV); // todo: change to a different way of filtering.
                return chord;
            }
        } else { // This Chord should not be touched!!! Return the original.
            return chord;
        }
    }





        private Chord OctaveUpDown(Chord a, boolean b) {
        var o = 12;
        a.setSoprano(a.getSoprano() + (b ? o: -o));
        a.setAlto(a.getAlto() + (b ? o: -o));
        a.setTenor(a.getTenor() + (b ? o: -o));
        a.setBass(a.getBass() + (b ? o: -o));
        return a;
    }


}





