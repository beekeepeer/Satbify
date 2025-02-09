package com.DAWIntegration.Satbify.module;

import lombok.*;



@Data
@AllArgsConstructor
public class Chord{
    private int soprano;
    private int alto;
    private int tenor;
    private int bass;
    private int finalSoprano, finalAlto, finalTenor, finalBass; // for harmonizing given notes
    private double noteStartTime;
    private double noteEndTime;
    private Key keyRoot = Key.C;
    private Degree chordDegree = Degree.I;
    private Scale keyScale = Scale.MAJOR_NATURAL;
    private MelodicPosition melodicPosition;
    private ChordType chordType = ChordType.TRIAD;
    private Inversion inversion;
    private Spacing spacing;
    private Alteration alteration = Alteration.NONE;
    private Occurrence occurrence;
    private int tessitura;
    private int id;


    //constructor for ChordRepository
    public Chord(MelodicPosition melodicPosition, Spacing spacing, Inversion inversion, ChordType chordType, Alteration alteration, Occurrence occurrence, int soprano, int alto, int tenor, int bass) {
        this.melodicPosition = melodicPosition;
        this.spacing = spacing;
        this.inversion = inversion;
        this.chordType = chordType;
        this.alteration = alteration;
        this.occurrence = occurrence;
        this.soprano = soprano;
        this.alto = alto;
        this.tenor = tenor;
        this.bass = bass;
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
                " \n tickStartTime =   " + noteStartTime +
                " \n tickEndTime =     " + noteEndTime +
                " \n soprano =         " + soprano +
                " \n alto =            " + alto +
                " \n tenor =           " + tenor +
                " \n bass =            " + bass +
                '}' +
                " \n";
    }
}