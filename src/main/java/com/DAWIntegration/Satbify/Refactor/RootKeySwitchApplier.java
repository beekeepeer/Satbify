package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import com.DAWIntegration.Satbify.module.Key;
import com.DAWIntegration.Satbify.module.Note;

public class RootKeySwitchApplier implements KeySwitchApplier {

    public static RootKeySwitchApplier getInstance() {
        return new RootKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords) {
        int lastRootNoteIndex = 0;
        for (Note note : notes) {




            
            }
        return chords;
    }

    private Key rootFromNote(int pitch) {
        switch (pitch) {
            case 0:  return Key.C;
            case 1:  return Key.C_Sharp;
            case 2:  return Key.D;
            case 3:  return Key.D_Sharp;
            case 4:  return Key.E;
            case 5:  return Key.F;
            case 6:  return Key.F_Sharp;
            case 7:  return Key.G;
            case 8:  return Key.G_Sharp;
            case 9:  return Key.A;
            case 10: return Key.A_Sharp;
            case 11: return Key.B;
            default: return null; // This line will never be reached due to the check above.
        }
    }
}


/*

chatGpt 
 * public class RootKeySwitchApplier {

    public List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords) {
        Key currentKeyRoot = Key.C; // Default key
        int noteIndex = 0;
        
        for (FatChord chord : chords) {
            // Process all notes whose startTime is <= the chord's startTime
            while (noteIndex < notes.size() && notes.get(noteIndex).startTime() <= chord.startTime) {
                int pitch = notes.get(noteIndex).pitch();
                Key newKey = getKeyFromPitch(pitch);
                if (newKey != null) { // Only update if the pitch is valid
                    currentKeyRoot = newKey;
                }
                noteIndex++;
            }
            chord.setKeyRoot(currentKeyRoot);
        }
        return chords;
    }

    private Key getKeyFromPitch(int pitch) {
        // Ignore invalid key switches
        if (pitch < 0 || pitch > 11) {
            return null;
        }
        switch (pitch) {
            case 0:  return Key.C;
            case 1:  return Key.C_Sharp;
            case 2:  return Key.D;
            case 3:  return Key.D_Sharp;
            case 4:  return Key.E;
            case 5:  return Key.F;
            case 6:  return Key.F_Sharp;
            case 7:  return Key.G;
            case 8:  return Key.G_Sharp;
            case 9:  return Key.A;
            case 10: return Key.A_Sharp;
            case 11: return Key.B;
            default: return null; // This line will never be reached due to the check above.
        }
    }
}

 */