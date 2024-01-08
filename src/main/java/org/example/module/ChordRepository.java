package org.example.module;

import java.util.ArrayList;

/*
This is a substance of the database of all variations of chords without mentioning its Degree, tonality and scale.
add comments to lines about practical usage.
 */
public class ChordRepository {


    // Initialize the ArrayList of chords
    public static final ArrayList<Chord> chordsRepositoryList = new ArrayList<>();


    static {
        // Close Spacing:
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4), new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.I, 5)));
        // Open Spacing:
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.III, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.I, 5)));
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.I, 5)));
        // add specific spacing and doubling


        // First inversion:
        // Doubled I, MelodicPosition.I
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.III, 3)));
        // Doubled I, MelodicPosition.V
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4)));
        // Doubled V, MelodicPosition.I
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.III, 5)));
        // Doubled V, MelodicPosition.V
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4)));
        chordsRepositoryList.add(new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4)));

        // Квартсекстаккорды. Скорее всего их будет очень мало. Например проходящие и кадансовые.

        // Seventh Chord and NonaChord
    }
}
