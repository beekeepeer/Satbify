package org.example.module;

import java.util.ArrayList;

/*
The substance of the database of all variations of chords without mentioning its tonality and scale.
 */
public class ChordRepository {


    // Initialize the ArrayList of chords
    public static final ArrayList<Chord> chordsRepositoryList = new ArrayList<>();

    static{
        chordsRepositoryList.add(new Chord(
                Degree.I,
                MelodicPosition.I,
                Spacing.CLOSE,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.I, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.III, 4),
                new NoteOfScale(Degree.I, 4)));
    }
}
