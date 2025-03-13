package com.DAWIntegration.Satbify.Refactor;

import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.shouldApplyNonLatching;

import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

public class PhrasePeriodKeySwitchApplier implements KeySwitchApplier{

    public static PhrasePeriodKeySwitchApplier getInstance() {
        return new PhrasePeriodKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        double previousPhraseEnd = 0.0;
        int phraseNumber = 0;
        int periodNumber = 0;
        for (Note note : allKS) {
            if (note.pitch() == 108) {
                phraseNumber++;
                if (previousPhraseEnd != note.startTime())
                    periodNumber++;
                for (FatChord chord : preChords) {
                    if (shouldApplyNonLatching(note, chord)) {
                        chord.setPhraseNumber(phraseNumber);
                        chord.setPeriodNumber(periodNumber);
                    }
                }
                previousPhraseEnd = note.endTime();
            }
        }
        return preChords;
    }

}
