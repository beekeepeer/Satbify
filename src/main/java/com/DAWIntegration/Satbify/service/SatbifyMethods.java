package com.DAWIntegration.Satbify.service;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;

/**
 * The {@code SatbifyMethods} class contains static utility methods used repeatedly in various classes of the Satbify application.
 */

public class SatbifyMethods {

    public static boolean shouldApplyNonLatching(Note note, FatChord chord) {
        // int backlash = 0; // TODO set aproximation here later
        return (chord.getStartTime() >= note.startTime() && chord.getStartTime() < note.endTime());
    }

    // unfinished
    public static boolean shouldApplyLatching(Note note, FatChord chord) {
        return chord.getStartTime() >= note.startTime();
    }
}