package com.DAWIntegration.Satbify.repository;

import com.DAWIntegration.Satbify.module.*;

import java.util.List;

/*
This is a substance of the database of all variations of chords without mentioning its Degree and scale. For simplicity it is in C major
TODO add comments to lines about practical usage from textbook.
 */
public class ChordRepository {


    // Initialize List of chords
    public static final List<Chord> chordsRepositoryList = List.of(
        // Close Spacing:
         new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON,
         79, 
         76, 
         72, 
         60),
         new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON, 
         76, 
         72, 
         67, 
         60 ),
         new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON,
         72,
         67, 
         64, 
         60 ),

         
        // Open Spacing:
         new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON,
         79, 
         72, 
         64, 
         60 ),
         new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON,
         84, 
         76, 
         67, 
         60 ),
         new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.TRIAD, Alteration.NONE, Occurrence.COMMON,
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
         64 ),

        // Квартсекстаккорды. Скорее всего их будет очень мало. Например проходящие и кадансовые.

        new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.SECOND_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.NOT_COMMON,
                67,
                64,
                60,
                55),
        new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.SECOND_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.NOT_COMMON,
                72,
                67,
                64,
                55),
        new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.SECOND_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.NOT_COMMON,
                76,
                72,
                67,
                55),
        new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.SECOND_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.NOT_COMMON,
                76,
                67,
                60,
                55),
        new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.SECOND_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.NOT_COMMON,
                79,
                72,
                64,
                55),
        new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.SECOND_INVERSION, ChordType.TRIAD, Alteration.NONE, Occurrence.NOT_COMMON,
                84,
                76,
                67,
                55),







    // Seventh Chord


        new Chord(MelodicPosition.VII, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                59,
                55,
                52,
                48),

        new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                64,
                59,
                55,
                48),
        new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                67,
                64,
                59,
                48),
        new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                64,
                60,
                59,
                48),

        new Chord(MelodicPosition.VII, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                71,
                64,
                55,
                48),
        new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                76,
                67,
                59,
                48),
        new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                67,
                59,
                52,
                48),
        new Chord(MelodicPosition.VII, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                71,
                60,
                52,
                48),
        new Chord(MelodicPosition.VII, Spacing.MIXED_1, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                71,
                64,
                60,
                48),

        new Chord(MelodicPosition.I, Spacing.MIXED_1, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                60,
                59,
                52,
                48),
        new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                64,
                59,
                48,
                48),
        new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                72,
                64,
                59,
                48),






        // inversion 1





        new Chord(MelodicPosition.VII, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                71,
                67,
                60,
                52),
        new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                72,
                71,
                67,
                64),
        new Chord(MelodicPosition.V, Spacing.MIXED_1, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                79,
                72,
                71,
                64),
        new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                79,
                71,
                60,
                52),
        new Chord(MelodicPosition.VII, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                71,
                60,
                55,
                52),
        new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.RARE,
                72,
                67,
                59,
                52),



//            second inversion





        new Chord(MelodicPosition.VII, Spacing.CLOSE, Inversion.SECOND_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                71,
                64,
                60,
                55),
        new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.SECOND_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                64,
                60,
                59,
                55),
        new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.SECOND_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                76,
                71,
                60,
                55),
        new Chord(MelodicPosition.VII, Spacing.OPEN, Inversion.SECOND_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                83,
                72,
                64,
                55),
        new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.SECOND_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                72,
                64,
                59,
                55),
        new Chord(MelodicPosition.I, Spacing.MIXED_1, Inversion.SECOND_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                72,
                71,
                64,
                55),




        // Third inversion




        new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.THIRD_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                67,
                64,
                60,
                59),
        new Chord(MelodicPosition.I, Spacing.CLOSE, Inversion.THIRD_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                72,
                67,
                64,
                59),
        new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.THIRD_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                76,
                72,
                67,
                59),

        new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.THIRD_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                76,
                67,
                60,
                59),
        new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.THIRD_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                79,
                72,
                64,
                59),
        new Chord(MelodicPosition.I, Spacing.OPEN, Inversion.FIRST_INVERSION, ChordType.SEVENTH_CHORD, Alteration.NONE, Occurrence.NOT_COMMON,
                84,
                76,
                67,
                59),




        // and NonaChord






        new Chord(MelodicPosition.VII, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.RARE,
                71,
                64,
                62,
                48),
        new Chord(MelodicPosition.IX, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.RARE,
                74,
                71,
                64,
                60),
        new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.RARE,
                76,
                74,
                71,
                60),
        new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.RARE,
                76,
                71,
                62,
                48),
        new Chord(MelodicPosition.VII, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.RARE,
                83,
                74,
                64,
                60),
        new Chord(MelodicPosition.IX, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.RARE,
                86,
                76,
                71,
                60)




        // very rare. можно удваивать квинту только для обращений :

//            new Chord(MelodicPosition.IX, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.UNUSABLE,
//                    74,
//                    67,
//                    64,
//                    60),
//            new Chord(MelodicPosition.III, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.UNUSABLE,
//                    76,
//                    74,
//                    67,
//                    60),
//            new Chord(MelodicPosition.V, Spacing.CLOSE, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.UNUSABLE,
//                    79,
//                    76,
//                    74,
//                    60),
//            new Chord(MelodicPosition.IX, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.UNUSABLE,
//                    86,
//                    76,
//                    67,
//                    60),
//            new Chord(MelodicPosition.V, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.UNUSABLE,
//                    79,
//                    74,
//                    64,
//                    60),
//            new Chord(MelodicPosition.III, Spacing.OPEN, Inversion.ROOT_POSITION, ChordType.NINTH_CHORD, Alteration.NONE, Occurrence.UNUSABLE,
//                    88,
//                    79,
//                    74,
//                    60)




    );
}
