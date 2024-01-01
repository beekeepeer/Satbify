package org.example.module;

import java.util.ArrayList;

/*
The substance of the database of all variations of chords without mentioning its Degree, tonality and scale.
 */
public class ChordRepository {


    // Initialize the ArrayList of chords
    public static final ArrayList<Chord> chordsRepositoryList = new ArrayList<>();

    // Close Spacing:
    static {
        chordsRepositoryList.add(new Chord(
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
        chordsRepositoryList.add(new Chord(
                MelodicPosition.III,
                Spacing.CLOSE,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.I, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.V,
                Spacing.CLOSE,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.V, 5),
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.I, 5),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.V,
                Spacing.CLOSE,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.III, 4),
                new NoteOfScale(Degree.I, 4),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.III,
                Spacing.CLOSE,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.I, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 3)));


        // Open Spacing:
        chordsRepositoryList.add(new Chord(
                MelodicPosition.I,
                Spacing.OPEN,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.I, 6),
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.I,
                Spacing.OPEN,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.I, 6),
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.I,
                Spacing.OPEN,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.I, 6),
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.I,
                Spacing.OPEN,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.I, 6),
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 4)));
        chordsRepositoryList.add(new Chord(
                MelodicPosition.I,
                Spacing.OPEN,
                Inversion.ROOT_POSITION,
                ChordType.TRIAD,
                Alteration.NONE,
                Occurrence.COMMON,
                new NoteOfScale(Degree.I, 6),
                new NoteOfScale(Degree.III, 5),
                new NoteOfScale(Degree.V, 4),
                new NoteOfScale(Degree.I, 4)));

    }
}
