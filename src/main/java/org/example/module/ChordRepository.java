package org.example.module;

import java.util.List;

/*
This is a substance of the database of all variations of chords without mentioning its Degree and scale. For simplicity it is in C major
TODO add comments to lines about practical usage from textbook.
 */
public class ChordRepository {


    // Initialize immutable List of chords
    public static final List<Chord> chordsRepositoryList = List.of(
        // Close Spacing:
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         79, 
         76, 
         72, 
         60),
         new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         76, 
         72, 
         67, 
         60 ),
         new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         72,
         67, 
         64, 
         60 ),

         
        // Open Spacing:
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         79, 
         72, 
         64, 
         60 ),
         new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         84, 
         76, 
         67, 
         60 ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         88, 
         79, 
         72, 
         60 ),
        // add specific spacing and doubling


        // First inversion:
        // Doubled I, MelodicPosition.I
         new Chord(MelodicPosition.I, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         84, 
         72, 
         67, 
         64 ),
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         84, 
         79, 
         72, 
         64 ),
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         84, 
         84, 
         79, 
         76 ),
        // Doubled I, MelodicPosition.V
         new Chord(MelodicPosition.V, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         79, 
         72, 
         72, 
         64 ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON,
        91, 
        84, 
        72, 
        64 ),

        // Doubled V, MelodicPosition.I
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         84, 
         79, 
         67, 
         64 ),
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         84, 
         79, 
         79, 
         76 ),

        // Doubled V, MelodicPosition.V
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         79, 
         72, 
         67, 
         64 ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         79, 
         79, 
         72, 
         64 ),
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         91, 
         79, 
         72, 
         64 )

        // Квартсекстаккорды. Скорее всего их будет очень мало. Например проходящие и кадансовые.

        // Seventh Chord and NonaChord
    );
}
