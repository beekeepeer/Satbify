package com.DAWIntegration.Satbify.Refactor.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.Refactor.FatChord;
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
        switch (note.pitch()) {
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
            default: return Key.C;
        }
    }
}