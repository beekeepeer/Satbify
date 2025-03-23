package com.DAWIntegration.Satbify.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Key;
import com.DAWIntegration.Satbify.module.Note;

/*
 * This class only moves information about the root of the tonality 
 * from the keySwitches to the Chords objects to store only.
 */
public class RootKeySwitchApplier implements KeySwitchApplier {
// exception - if two root keySwitches start at the same time.
    public static RootKeySwitchApplier getInstance() {
        return new RootKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords) {
        for (Note note : notes) {
            if (note.pitch() >= 0 && note.pitch() <= 11) {
                for (FatChord chord : chords) {
                    if(note.startTime() <= chord.getStartTime()){
                        chord.setKeyRoot(rootFromNote(note));
                    }
                }
            }
        }
        return chords;
    }

    private Key rootFromNote(Note note) {
        return switch (note.pitch()) {
            case 1 -> Key.C_Sharp;
            case 2 -> Key.D;
            case 3 -> Key.D_Sharp;
            case 4 -> Key.E;
            case 5 -> Key.F;
            case 6 -> Key.F_Sharp;
            case 7 -> Key.G;
            case 8 -> Key.G_Sharp;
            case 9 -> Key.A;
            case 10 -> Key.A_Sharp;
            case 11 -> Key.B;
            default -> Key.C;
        };
    }
}