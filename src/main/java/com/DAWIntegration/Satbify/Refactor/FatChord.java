package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
// mutable
public class FatChord implements Cloneable {
    // TODO id only for debugging. should be deleted on production
    private static int counter;
    private int id;

    private int soprano, alto, tenor, bass;
    private int finalSoprano, finalAlto, finalTenor, finalBass; // for harmonizing given notes
    private double startTime, endTime;
    private int startBar, endBar; // Musical timing
    private double startBeat, endBeat; // Musical timing
    private Key keyRoot = Key.C;
    private Degree chordDegree = Degree.I;
    private Scale keyScale = Scale.MAJOR_NATURAL;
    private MelodicPosition melodicPosition;
    private ChordType chordType = ChordType.TRIAD;
    private Inversion inversion;
    private Spacing spacing;
    private Alteration alteration = Alteration.NONE;
    private Occurrence occurrence;
    private int register = 68;
    private boolean legato = true;
    private int phraseNumber, periodNumber;
    // smoothness should be always maximum, but lines should be "musical" and follow textbook (best practice).
    // public boolean smoothSoprano = false;
    // public boolean smoothBass = false;

    // constructor for ChordRepository only.
    public FatChord(MelodicPosition melodicPosition,
            Spacing spacing,
            Inversion inversion,
            ChordType chordType,
            Alteration alteration,
            Occurrence occurrence,
            int soprano,
            int alto,
            int tenor,
            int bass) {
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

    private FatChord() {}

    public static FatChord getNewInstance() {
        FatChord fatChord = new FatChord();
        counter++;
        fatChord.id = counter;
        return fatChord;
    }

    @Override
    public FatChord clone() {
        try {
            FatChord copy = (FatChord) super.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Should not happen
        }
    }




    @Override
    public String toString() {
    return "FatChord{" +
    " \n id = " + id +
    " \n keyRoot = " + keyRoot +
    " \n keyScale = " + keyScale +
    " \n chordDegree = " + chordDegree +
    " \n melodicPosition = " + melodicPosition +
    " \n spacing = " + spacing +
    " \n inversion = " + inversion +
    " \n chordType = " + chordType +
    " \n alteration = " + alteration +
    " \n occurrence = " + occurrence +
    " \n register = " + register +
    " \n Phrasing = " + periodNumber + " " + phraseNumber + 
    " \n Time = " + startTime + " " + endTime + 
    " \n MusicStart = " + startBar + " / " + startBeat + 
    " \n MusicEnd = " + endBar + " / " + endBeat +
    " \n FinalVoices = " + finalSoprano + " " + finalAlto + " " + finalTenor + " " + finalBass +
    " \n voices = " + soprano + " " + alto + " " + tenor + " " + bass +
    '}' +
    " \n";
    }
}