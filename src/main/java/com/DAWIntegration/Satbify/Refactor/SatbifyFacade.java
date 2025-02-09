package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Note;

import java.util.ArrayList;
import java.util.List;

public class SatbifyFacade {
    
    private final KeySwitchSorter keySwitchSorter = KeySwitchSorter.getInstance();
    private final KeySwitchesToChordsConverter keySwitchApplier = KeySwitchesToChordsConverter.getInstance();
    List<Period> periods = new ArrayList<>();
    List<Phrase> phrases = new ArrayList<>();
    List<FatChord> preChords= new ArrayList<>(); // temporary, for storing keySwitches.

    // facade method
    public String processRequest(List<Note> notes) {
        notes = this.keySwitchSorter.sortKeySwitches(notes);
        preChords = keySwitchApplier.notesToChords(notes);
        return null;
    }
}