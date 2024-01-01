package org.example.module;


/*
the node consists of a degree (I,V...) and an octave (-3, -2, -1, 0, 1, 2, 3) the Middle C (note number 60)
it should work with Scale class and be used to build the ChordRepository.
 */
public class NoteOfScale {
    Degree degree;
    int octave;

    public NoteOfScale(Degree degree, int octave) {
        this.degree = degree;
        this.octave = octave;
    }
}


