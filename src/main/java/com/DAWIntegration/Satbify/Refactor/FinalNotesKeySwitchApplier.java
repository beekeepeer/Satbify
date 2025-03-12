package com.DAWIntegration.Satbify.Refactor;

import java.util.List;
import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.shouldApplyNonLatching;
import com.DAWIntegration.Satbify.module.Note;

public class FinalNotesKeySwitchApplier implements KeySwitchApplier{

    public static FinalNotesKeySwitchApplier getInstance() {
        return new FinalNotesKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (Note note : allKS) {
            for (FatChord chord : preChords) {
               if(shouldApplyNonLatching(note, chord)) {
                applyFinalNote(note, chord);
               }
            }
        }
        return preChords;
    }

    private void applyFinalNote(Note note, FatChord chord) {
        if (note.reaperTrack() > 1 && note.reaperTrack() < 4) {
            chord.setFinalSoprano(note.pitch());
        }
    }

}
