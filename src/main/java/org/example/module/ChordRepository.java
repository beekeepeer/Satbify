package org.example.module;

import java.util.List;

/*
This is a substance of the database of all variations of chords without mentioning its Degree, tonality and scale.
add comments to lines about practical usage.
 */
public class ChordRepository {


    // Initialize immutable List of chords
    public static final List<Chord> chordsRepositoryList = List.of(
        // Close Spacing:
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4), new NoteOfScale(Degree.I, 4) ),
         new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.I, 4) ),
         new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.I, 5) ),
        // Open Spacing:
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.I, 4) ),
         new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.III, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.I, 5) ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.III, 5), new NoteOfScale(Degree.I, 5) ),
        // add specific spacing and doubling


        // First inversion:
        // Doubled I, MelodicPosition.I
         new Chord(MelodicPosition.I, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4) ),
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4) ),
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.III, 3) ),
        // Doubled I, MelodicPosition.V
         new Chord(MelodicPosition.V, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4) ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4) ),
        // Doubled V, MelodicPosition.I
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4) ),
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.I, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.III, 5) ),
        // Doubled V, MelodicPosition.V
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.V, 4), new NoteOfScale(Degree.III, 4) ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4) ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, new NoteOfScale(Degree.V, 6), new NoteOfScale(Degree.V, 5), new NoteOfScale(Degree.I, 5), new NoteOfScale(Degree.III, 4) )

        // Квартсекстаккорды. Скорее всего их будет очень мало. Например проходящие и кадансовые.

        // Seventh Chord and NonaChord
    );
}
