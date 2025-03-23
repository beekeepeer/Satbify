package com.DAWIntegration.Satbify.keySwitchAppliers;

import static com.DAWIntegration.Satbify.service.SatbifyMethods.shouldApplyNonLatching;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;

/*
 * This class only moves information about the notes which should be harmonized
 * from the keySwitches to the Chord objects to store.
 */

public class FinalNotesKeySwitchApplier implements KeySwitchApplier{

    public static FinalNotesKeySwitchApplier getInstance() {
        return new FinalNotesKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (Note note : allKS) {
            for (FatChord chord : preChords) {
                if (shouldApplyNonLatching(note, chord)) {
                   switch (note.reaperTrack()) {
                    case 1: chord.setFinalSoprano(note.pitch()); break;
                    case 2: chord.setFinalAlto(note.pitch()); break;
                    case 3: chord.setFinalTenor(note.pitch()); break;
                    case 4: chord.setFinalBass(note.pitch()); break;
                   }
                }
            }
        }
        return preChords;
    }
}
