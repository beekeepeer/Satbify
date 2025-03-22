package com.DAWIntegration.Satbify.service;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;

public class ChordsToReaperSerializer {

    public static ChordsToReaperSerializer getInstance() {
        return new ChordsToReaperSerializer();
    }

    public String ChordsToJson(List<FatChord> connectedChords) {
        
        return returnToReaper(connectedChords, true);
    }


    private static String returnToReaper(List<FatChord> finalChords, boolean legato) {
        if (finalChords.size() == 1) legato = false;
        StringBuilder out = new StringBuilder();
        int sP = 0, aP = 0, tP = 0, bP = 0;
        double sopranoStartTime = finalChords.get(0).getStartTime();
        double sopranoEndTime = finalChords.get(0).getEndTime();
        double altoStartTime = sopranoStartTime;
        double altoEndTime = sopranoEndTime;
        double tenorStartTime = sopranoStartTime;
        double tenorEndTime = sopranoEndTime;
        double bassStartTime = sopranoStartTime;
        double bassEndTime = sopranoEndTime;
        boolean mustAddS = true;
        boolean mustAddA = true;
        boolean mustAddT = true;
        boolean mustAddB = true;

        try {
            for (int i = 0; i < finalChords.size(); i++) {

                if (legato) {
                    if(i == 0){
                        sP = finalChords.get(i).getSoprano();
                        aP = finalChords.get(i).getAlto();
                        tP = finalChords.get(i).getTenor();
                        bP = finalChords.get(i).getBass();
                        continue;
                    }
                    // soprano block:
                    if (sP == finalChords.get(i).getSoprano()) {
                        // do not add, but save to variables
                        sopranoEndTime = finalChords.get(i).getEndTime();
                        mustAddS = true;

                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 1, sP, sopranoStartTime, sopranoEndTime);
                        }
                    } else {
                        if (mustAddS) {
                            // add previous note, if was not added:
                            appendNote(out, 1, sP, sopranoStartTime, sopranoEndTime + 0.005);
                            mustAddS = false;
                        }
                        // apply note at i:
                        sopranoStartTime = finalChords.get(i).getStartTime();
                        sopranoEndTime = finalChords.get(i).getEndTime();
                        // save previous to the variable:
                        sP = finalChords.get(i).getSoprano();


                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && sP != finalChords.get(i + 1).getSoprano())) {
                            // append:
                            appendNote(out, 1, sP, sopranoStartTime, sopranoEndTime + 0.005);
                        }
                    }

                    // alto block:
                    if (aP == finalChords.get(i).getAlto()) {
                        // do not add, but save to variables
                        altoEndTime = finalChords.get(i).getEndTime();
                        mustAddA = true;

                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 2, aP, altoStartTime, altoEndTime);
                        }
                    } else {

                        if (mustAddA) {
                            // add previous note, if was not added:
                            appendNote(out, 2, aP, altoStartTime, altoEndTime + 0.005);
                            mustAddA = false;
                        }
                        // apply note at i:
                        altoStartTime = finalChords.get(i).getStartTime();
                        altoEndTime = finalChords.get(i).getEndTime();
                        // save previous to the variable:
                        aP = finalChords.get(i).getAlto();
                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && aP != finalChords.get(i + 1).getAlto())) {
                            // append:
                            appendNote(out, 2, aP, altoStartTime, altoEndTime + 0.005);
                        }
                    }

                    // tenor block:
                    if (tP == finalChords.get(i).getTenor()) {
                        // do not add, but save to variables
                        tenorEndTime = finalChords.get(i).getEndTime();
                        mustAddT = true;
                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 3, tP, tenorStartTime, tenorEndTime);
                        }
                    } else {

                        if (mustAddT) {
                            // add previous note, if was not added:
                            appendNote(out, 3, tP, tenorStartTime, tenorEndTime + 0.005);
                            mustAddT = false;
                        }
                        // apply note at i:
                        tenorStartTime = finalChords.get(i).getStartTime();
                        tenorEndTime = finalChords.get(i).getEndTime();
                        // save previous to the variable:
                        tP = finalChords.get(i).getTenor();
                        // append:
                        // if last chord                or  next check will not fail              next note is not the same
                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && tP != finalChords.get(i + 1).getTenor())) {
                            // append:
                            appendNote(out, 3, tP, tenorStartTime, tenorEndTime + 0.005);
                        }
                    }

                    // bass block:
                    if (bP == finalChords.get(i).getBass()) {
                        // do not add, but save to variables
                        bassEndTime = finalChords.get(i).getEndTime();
                        mustAddB = true;
                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 4, bP, bassStartTime, bassEndTime);
                        }
                    } else {
                        if (mustAddB) {
                            // add previous note, if was not added:
                            appendNote(out, 4, bP, bassStartTime, bassEndTime + 0.005);
                            mustAddB = false;
                        }
                        // apply note at i:
                        bassStartTime = finalChords.get(i).getStartTime();
                        bassEndTime = finalChords.get(i).getEndTime();
                        // save previous to the variable:
                        bP = finalChords.get(i).getBass();
                        // append:
                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && bP != finalChords.get(i + 1).getBass())) {
                            // append:
                            appendNote(out, 4, bP, bassStartTime, bassEndTime + 0.005);
                        }
                    }
                }
                else {
                    var start = finalChords.get(i).getStartTime();
                    var end = finalChords.get(i).getEndTime();
                    appendNote(out, 1, finalChords.get(i).getSoprano(), start, end - 0.005);
                    appendNote(out, 2, finalChords.get(i).getAlto(), start, end - 0.005);
                    appendNote(out, 3, finalChords.get(i).getTenor(), start, end - 0.005);
                    appendNote(out, 4, finalChords.get(i).getBass(), start, end - 0.005);
                }
            }
//            System.out.println(out);
        } catch (Exception e) {
            System.out.println("The output of This program is EMPTY.");
        }
        return out.toString();
    }
    private static void appendNote(StringBuilder sb, int track, int note, double start, double end) {
        sb.append(track).append(", ")
                .append(note).append(", ")
                .append(start).append(", ")
                .append(end).append("\n");
    }

}
