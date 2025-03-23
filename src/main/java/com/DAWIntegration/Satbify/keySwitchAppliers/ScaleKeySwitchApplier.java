package com.DAWIntegration.Satbify.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.module.Scale;

/*
 * This class only moves information about the musical scale context
 * from the keySwitches to the Chord objects to store.
 */

public class ScaleKeySwitchApplier implements KeySwitchApplier{
    public static ScaleKeySwitchApplier getInstance() {
        return new ScaleKeySwitchApplier();
    }


    @Override
    public List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> preChords) {
        for (FatChord chord : preChords) {
            for (Note note : notes) {
                if (isScale(note.pitch()) && chord.getStartTime() >= note.startTime()) {
                    chord.setKeyScale(applyScale(note));
                    }
            }
        }
        return preChords;
    }
    

    public boolean isScale(int pitch) {
        return pitch == Scale.MAJOR_NATURAL.getKeySwitch() ||
                pitch == Scale.MAJOR_HARMONIC.getKeySwitch() ||
                pitch == Scale.MINOR_NATURAL.getKeySwitch() ||
                pitch == Scale.MINOR_HARMONIC.getKeySwitch() ||
                pitch == Scale.MINOR_MELODIC.getKeySwitch();
    }

    private Scale applyScale(Note note) {
        return switch (note.pitch()) {
            case 15 -> Scale.MAJOR_HARMONIC;
            case 18 -> Scale.MINOR_NATURAL;
            case 20 -> Scale.MINOR_HARMONIC;
            case 22 -> Scale.MINOR_MELODIC;
            default -> Scale.MAJOR_NATURAL;
        };
    }
}
