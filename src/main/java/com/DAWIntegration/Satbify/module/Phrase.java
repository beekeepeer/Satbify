package com.DAWIntegration.Satbify.module;

import java.util.List;

public record Phrase (List<Chord> chords){
    public void addChord(Chord chord){
        chords.add(chord);
    }
}
