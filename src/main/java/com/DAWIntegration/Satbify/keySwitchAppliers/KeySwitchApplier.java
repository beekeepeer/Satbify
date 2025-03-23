package com.DAWIntegration.Satbify.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;

// Functional interface exists only for readability and possible future use a lambdas or method references.
public interface KeySwitchApplier {
    List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords);
}
