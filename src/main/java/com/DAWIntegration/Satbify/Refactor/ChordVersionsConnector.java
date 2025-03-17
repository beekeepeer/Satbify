package com.DAWIntegration.Satbify.Refactor;

import java.util.ArrayList;
import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

public class ChordVersionsConnector {

    public static ChordVersionsConnector getInstance() {
        return new ChordVersionsConnector();
    }

    public List<FatChord> connectChords(List<List<FatChord>> versions, List<Note> allKS) {
        return new FatChordConnector().connect(versions);
    }

}
