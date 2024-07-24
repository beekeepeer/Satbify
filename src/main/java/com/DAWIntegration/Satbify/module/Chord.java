package com.DAWIntegration.Satbify.module;

import lombok.*;


@AllArgsConstructor
@Data
public class Chord{
    private int soprano;
    private int alto;
    private int tenor;
    private int bass;
    private int finalSoprano, finalAlto, finalTenor, finalBass; // for harmonizing given notes
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
    private int standard;
    private int id;


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
                " \n";
    }


    public boolean absentFinalNotes() {
        return ! (this.finalSoprano != 0 ||
                this.finalAlto != 0 ||
                this.finalTenor != 0 ||
                this.finalBass != 0);
    }
    public boolean fitsFinalNote() {
//        System.out.println("this.finalBass" + this.finalBass);
        return (this.finalSoprano != 0 && this.finalSoprano % 12 == this.getSoprano() % 12)
                || (this.finalAlto != 0 && this.finalAlto % 12 == this.getAlto() % 12)
                || (this.finalTenor != 0  && this.finalTenor % 12 == this.getTenor() % 12)
                || (this.finalBass != 0 && this.finalBass % 12 == this.getBass() % 12);
    }
}