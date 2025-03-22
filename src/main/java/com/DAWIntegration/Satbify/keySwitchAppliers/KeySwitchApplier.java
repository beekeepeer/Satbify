package com.DAWIntegration.Satbify.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;

public interface KeySwitchApplier {
    List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords);
}
