package org.example.module;


public class Chord {

    // replace int values with enums

    KeyRoot keyRoot;
    ChordRoot chordRoot;
    Mode mode;
    MelodicPosition melodicPosition;
    Spacing spacing;
    Inversion inversion;
    ChordType type;
    Alteration alteration;
    long timePosition;

    ChordNote sopranoNote;
    ChordNote altoNote;
    ChordNote tenorNote;
    ChordNote bassNote;

    public Chord(KeyRoot keyRoot, ChordRoot chordRoot, Mode mode,
                 MelodicPosition melodicPosition, Spacing spacing,
                 Inversion inversion, ChordType type, Alteration alteration) {
        this.keyRoot = keyRoot;
        this.chordRoot = chordRoot;
        this.mode = mode;
        this.melodicPosition = melodicPosition;
        this.spacing = spacing;
        this.inversion = inversion;
        this.type = type;
        this.alteration = alteration;
    }
}

// add int values to enum values:


enum KeyRoot {
    I, II, III, IV, V, VI, VII
}

enum ChordRoot {
    I, II, III, IV, V, VI, VII
}

enum Mode {
    MAJOR_NATURAL,
    MAJOR_HARMONIC,
    MINOR_NATURAL,
    MINOR_HARMONIC
}

enum MelodicPosition {
    I, III, V, VII, IX
}

enum Spacing {
    NARROW, WIDE
}

enum Inversion {
    I, III, V, VII, IX
}

enum ChordType {
    TRIAD,
    SEVENTH,
    NINTH
}

enum alteration {

}

enum ChordNote {
    I, III, V, VII, IX
}

enum Alteration {
    RAISED_I,  // probably should be deleted
    RAISED_II,
    RAISED_III,
    RAISED_IV, // Группа аккордов двойной доминанты
    RAISED_V,
    RAISED_VI,
    RAISED_VII,   // probably should be deleted потому что и в мажоре и в минору это норма

    LOWERED_I,
    LOWERED_II,
    LOWERED_III,
    LOWERED_IV,
    LOWERED_V,
    LOWERED_VI,
    LOWERED_VII
}












