package com.DAWIntegration.Satbify.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;

public class AlterationKeySwitchApplier implements KeySwitchApplier{

    public static AlterationKeySwitchApplier getInstance() {
        return new AlterationKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        return preChords;
    }

}
