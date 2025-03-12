package com.DAWIntegration.Satbify.Refactor;

import java.util.List;
import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.isMelodicPosition;
import com.DAWIntegration.Satbify.module.Note;

public class MelodicPositionKeySwitchApplier implements KeySwitchApplier {

    public static MelodicPositionKeySwitchApplier getInstance() {
        return new MelodicPositionKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (FatChord fatChord : preChords) {
           for (Note note : allKS) {
            if (isMelodicPosition(note.pitch()
            && ) {

            }
           }
        }
        return preChords;
    }

}
