package com.DAWIntegration.Satbify.module;

import lombok.*;


@AllArgsConstructor
@Data
public class Chord{
    private int id; // delete if not used in ChordConnector.
    private int soprano;
    private int alto;
    private int tenor;
    private int bass;
//    private final int finalSoprano, finalAlto, finalTenor, finalBass;
    private int tickStartTime;
    private int tickEndTime;
    private Key keyRoot = Key.C;
    private Degree chordDegree = Degree.I;
    private Scale keyScale = Scale.MAJOR_NATURAL;
    private MelodicPosition melodicPosition;
    private ChordType chordType = ChordType.TRIAD;
    private Inversion inversion;
    private Spacing spacing;
    private Alteration alteration = Alteration.NONE;
    private Occurrence occurrence;

    //constructor for ChordRepository
    public Chord(MelodicPosition melodicPosition, Spacing spacing, Inversion inversion, ChordType chordType, Alteration alteration, Occurrence occurrence, int i, int i1, int i2, int i3) {
        this.melodicPosition = melodicPosition;
        this.spacing = spacing;
        this.inversion = inversion;
        this.chordType = chordType;
        this.alteration = alteration;
        this.occurrence = occurrence;
        this.soprano = i;
        this.alto = i1;
        this.tenor = i2;
        this.bass = i3;
    }
    public Chord() {}

    @Override
    public String toString() {
        return "Chord{" +
                " \n id =              " + id +
                " \n keyRoot =         " + keyRoot +
                " \n keyScale =        " + keyScale +
                " \n chordDegree =     " + chordDegree +
                " \n melodicPosition = " + melodicPosition +
                " \n spacing =         " + spacing +
                " \n inversion =       " + inversion +
                " \n chordType =       " + chordType +
                " \n alteration =      " + alteration +
                " \n occurrence =      " + occurrence +
                " \n tickStartTime =   " + tickStartTime +
                " \n tickEndTime =     " + tickEndTime +
                " \n soprano =         " + soprano +
                " \n alto =            " + alto +
                " \n tenor =           " + tenor +
                " \n bass =            " + bass +
                '}' +
                " \n" +
                " \n";
    }




}