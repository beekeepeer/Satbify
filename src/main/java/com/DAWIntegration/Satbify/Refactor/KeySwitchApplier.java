package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

public interface KeySwitchApplier {
    List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> chords);

}
