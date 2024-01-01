package org.example.module;

// The chord mutable POJO
public class Chord {

    public KeyRoot keyRoot = KeyRoot.C;
    public Scale keyScale = Scale.MAJOR_NATURAL;
    private Degree chordDegree = Degree.I;
    private MelodicPosition melodicPosition = MelodicPosition.I;
    private Spacing spacing = Spacing.CLOSE;
    private Inversion inversion = Inversion.ROOT_POSITION;
    private ChordType chordType = ChordType.TRIAD;
    private Alteration alteration;             // for additional features
    private Occurrence occurrence;


    private long tickStartTime;
    private long tickEndTime;
    private NoteOfScale sopranoNote = new NoteOfScale(Degree.I, 5);
    private NoteOfScale altoNote = new NoteOfScale(Degree.V, 4);
    private NoteOfScale tenorNote = new NoteOfScale(Degree.III, 4);
    private NoteOfScale bassNote = new NoteOfScale(Degree.I, 4);


    // constructor:
    public Chord(MelodicPosition melodicPosition, Spacing spacing, Inversion inversion, ChordType chordType, Alteration alteration, Occurrence occurrence, NoteOfScale bassNote, NoteOfScale tenorNote, NoteOfScale altoNote, NoteOfScale sopranoNote) {

        this.melodicPosition = melodicPosition;
        this.spacing = spacing;
        this.inversion = inversion;
        this.chordType = chordType;
        this.alteration = alteration;
        this.occurrence = occurrence;
        this.sopranoNote = sopranoNote;
        this.altoNote = altoNote;
        this.tenorNote = tenorNote;
        this.bassNote = bassNote;
    }


    // getters and setters


    public KeyRoot getKeyRoot() {
        return keyRoot;
    }

    public void setKeyRoot(KeyRoot keyRoot) {
        // todo set tonality attributes only by static Chords.methods


        this.keyRoot = keyRoot;
    }

    public Scale getKeyScale() {
        return keyScale;
    }

    public void setKeyScale(Scale keyScale) {
        this.keyScale = keyScale;
    }

    public ChordType getChordType() {
        return chordType;
    }

    public void setChordType(ChordType chordType) {
        this.chordType = chordType;
    }

    public Degree getChordDegree() {
        return chordDegree;
    }

    public void setChordDegree(Degree chordDegree) {
        this.chordDegree = chordDegree;
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
        return chordType;
    }

    public void setType(ChordType type) {
        this.chordType = type;
    }

    public Alteration getAlteration() {
        return alteration;
    }

    public void setAlteration(Alteration alteration) {
        this.alteration = alteration;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
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

    public NoteOfScale getSopranoNote() {
        return sopranoNote;
    }

    public void setSopranoNote(NoteOfScale sopranoNote) {
        this.sopranoNote = sopranoNote;
    }

    public NoteOfScale getAltoNote() {
        return altoNote;
    }

    public void setAltoNote(NoteOfScale altoNote) {
        this.altoNote = altoNote;
    }

    public NoteOfScale getTenorNote() {
        return tenorNote;
    }

    public void setTenorNote(NoteOfScale tenorNote) {
        this.tenorNote = tenorNote;
    }

    public NoteOfScale getBassNote() {
        return bassNote;
    }

    public void setBassNote(NoteOfScale bassNote) {
        this.bassNote = bassNote;
    }
}
















