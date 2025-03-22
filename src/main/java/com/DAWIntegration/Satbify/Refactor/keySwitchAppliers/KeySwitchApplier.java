package com.DAWIntegration.Satbify.Refactor.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.Refactor.FatChord;
import com.DAWIntegration.Satbify.module.Note;

public interface KeySwitchApplier {
    List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords);
}
