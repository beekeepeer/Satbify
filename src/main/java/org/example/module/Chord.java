package org.example.module;


public class Chord {

    public static int keyRoot = 0;
    public static int keyScale = 13;
    private int chordDegree = 12;
    private int melodicPosition = 113; // delete and provide by midi note to "sopranoNote" variable???
    private int spacing = 124;
    private int inversion = 120;
    private int type = 117;
    private int alteration;             // for additional features
    private long tickStartTime = 0;
    private long tickEndTime = 0;

    private int sopranoNote = 60;
    private int altoNote = 55;
    private int tenorNote = 52;
    private int bassNote = 48;

    // getters and setters


    public int getChordDegree() {
        return chordDegree;
    }

    public void setChordDegree(int chordDegree) {
        this.chordDegree = chordDegree;
    }

    public int getMelodicPosition() {
        return melodicPosition;
    }

    public void setMelodicPosition(int melodicPosition) {
        this.melodicPosition = melodicPosition;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getInversion() {
        return inversion;
    }

    public void setInversion(int inversion) {
        this.inversion = inversion;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAlteration() {
        return alteration;
    }

    public void setAlteration(int alteration) {
        this.alteration = alteration;
    }

    public long getTickStartTime() {
        return tickStartTime;
    }

    public void setTickStartTime(long tickStartTime) {
        this.tickStartTime = tickStartTime;
    }

    public long getTickEndTime() {
        return tickEndTime;
    }

    public void setTickEndTime(long tickEndTime) {
        this.tickEndTime = tickEndTime;
    }

    public int getSopranoNote() {
        return sopranoNote;
    }

    public void setSopranoNote(int sopranoNote) {
        this.sopranoNote = sopranoNote;
    }

    public int getAltoNote() {
        return altoNote;
    }

    public void setAltoNote(int altoNote) {
        this.altoNote = altoNote;
    }

    public int getTenorNote() {
        return tenorNote;
    }

    public void setTenorNote(int tenorNote) {
        this.tenorNote = tenorNote;
    }

    public int getBassNote() {
        return bassNote;
    }

    public void setBassNote(int bassNote) {
        this.bassNote = bassNote;
    }
}
















