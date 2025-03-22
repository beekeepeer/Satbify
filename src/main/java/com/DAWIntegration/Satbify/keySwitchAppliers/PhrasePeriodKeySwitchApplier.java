package com.DAWIntegration.Satbify.keySwitchAppliers;

import static com.DAWIntegration.Satbify.service.SatbifyMethods.shouldApplyNonLatching;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;

/**
 * The {@code PhrasePeriodKeySwitchApplier} class is an implementation of the {@code KeySwitchApplier} interface
 * that applies musical phrase and period number assignments to a list of {@code FatChord}
 * objects based on events of a single key switch with pitch 108. The main logic relies on the time gap between
 * the repetitive keySwitches.
 */

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