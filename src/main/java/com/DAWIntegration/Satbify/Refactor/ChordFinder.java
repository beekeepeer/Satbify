package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

public class ChordFinder {

    public static ChordFinder getInstance() {
        return new ChordFinder();
    }

    public List<FatChord> findMissingChord(List<Note> allKS, List<FatChord> preChords) {
        //TODO Automatic chord finding is not implemented yet.
        return preChords;
    }

}
