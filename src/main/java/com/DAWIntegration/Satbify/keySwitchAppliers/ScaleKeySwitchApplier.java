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
                    applyScale(chord, note);
                    }
                    // if (chord.getStartTime() < note.startTime() + backlash) {
                    //     // it works if notes list is sorded by startTime.
                    //     break;
                // }
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

    private void applyScale(FatChord chord, Note note) {
        switch (note.pitch()) {
            case 13: chord.setKeyScale(Scale.MAJOR_NATURAL);break;
            case 15: chord.setKeyScale(Scale.MAJOR_HARMONIC);break;
            case 18: chord.setKeyScale(Scale.MINOR_NATURAL);break;
            case 20: chord.setKeyScale(Scale.MINOR_HARMONIC);break;
            case 22: chord.setKeyScale(Scale.MINOR_MELODIC);break;
        }
    }
}
