package org.example.module;


import org.example.module.enums.*;

public class Chord {

    /*TODO:
       add int values to enum values.
       set default values.
    */

    private static KeyRoot keyRoot;
    private static KeyMode keyMode;
    private ChordRoot chordRoot;
    private MelodicPosition melodicPosition; // delete and provide by midi note to "sopranoNote" variable
    private Spacing spacing;
    private Inversion inversion;
    private ChordType type;
    private Alteration alteration;
    private long tickNoteOn;
    private long tickNoteOff;

    private ChordNote sopranoNote;
    private ChordNote altoNote;
    private ChordNote tenorNote;
    private ChordNote bassNote;

    public Chord(ChordRoot chordRoot, MelodicPosition melodicPosition, Spacing spacing,
                 Inversion inversion, ChordType type, Alteration alteration) {
        this.chordRoot = chordRoot;
        this.melodicPosition = melodicPosition;
        this.spacing = spacing;
        this.inversion = inversion;
        this.type = type;
        this.alteration = alteration;
    }


    // getters and setters:


    public KeyRoot getKeyRoot() {
        return keyRoot;
    }

    public void setKeyRoot(KeyRoot keyRoot) {
        this.keyRoot = keyRoot;
    }

    public ChordRoot getChordRoot() {
        return chordRoot;
    }

    public void setChordRoot(ChordRoot chordRoot) {
        this.chordRoot = chordRoot;
    }

    public KeyMode getMode() {
        return keyMode;
    }

    public void setMode(KeyMode keyMode) {
        this.keyMode = keyMode;
    }

    public MelodicPosition getMelodicPosition() {
        return melodicPosition;
    }

    public void setMelodicPosition(MelodicPosition melodicPosition) {
        this.melodicPosition = melodicPosition;
    }

    public Spacing getSpacing() {
        return spacing;
    }

    public void setSpacing(Spacing spacing) {
        this.spacing = spacing;
    }

    public Inversion getInversion() {
        return inversion;
    }

    public void setInversion(Inversion inversion) {
        this.inversion = inversion;
    }

    public ChordType getType() {
        return type;
    }

    public void setType(ChordType type) {
        this.type = type;
    }

    public Alteration getAlteration() {
        return alteration;
    }

    public void setAlteration(Alteration alteration) {
        this.alteration = alteration;
    }

    public long getTickNoteOn() {
        return tickNoteOn;
    }

    public void setTickNoteOn(long tickNoteOn) {
        this.tickNoteOn = tickNoteOn;
    }

    public long getTickNoteOff() {
        return tickNoteOff;
    }

    public void setTickNoteOff(long tickNoteOff) {
        this.tickNoteOff = tickNoteOff;
    }

    public ChordNote getSopranoNote() {
        return sopranoNote;
    }

    public void setSopranoNote(ChordNote sopranoNote) {
        this.sopranoNote = sopranoNote;
    }

    public ChordNote getAltoNote() {
        return altoNote;
    }

    public void setAltoNote(ChordNote altoNote) {
        this.altoNote = altoNote;
    }

    public ChordNote getTenorNote() {
        return tenorNote;
    }

    public void setTenorNote(ChordNote tenorNote) {
        this.tenorNote = tenorNote;
    }

    public ChordNote getBassNote() {
        return bassNote;
    }

    public void setBassNote(ChordNote bassNote) {
        this.bassNote = bassNote;
    }
}














