package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

public class AlterationKeySwitchApplier {

    public static AlterationKeySwitchApplier getInstance() {
        return new AlterationKeySwitchApplier();
    }

    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        return preChords;
    }

}
