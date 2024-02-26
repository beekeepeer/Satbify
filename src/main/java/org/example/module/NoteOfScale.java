package org.example.module;


/*
the node consists of a degree (I,V...) and an octave (-3, -2, -1, 0, 1, 2, 3) the Middle C (note number 60)
it should work with Scale class and be used to build the ChordRepository.
 */
public class NoteOfScale implements Cloneable {
    private Degree degree;
    private int octave;

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public String toString(){
        return "Degree is " + degree + ", Octave is " + octave + " ";
    }

    public NoteOfScale(Degree degree, int octave) {
        this.degree = degree;
        this.octave = octave;
    }

    @Override
    public NoteOfScale clone() throws CloneNotSupportedException {
        NoteOfScale clonedNote = (NoteOfScale) super.clone();
        clonedNote.degree = this.degree;
        clonedNote.octave = this.octave;

        return clonedNote;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteOfScale) {
            NoteOfScale note = (NoteOfScale) obj;
            return this.degree == note.degree && this.octave == note.octave;
        }
        return false;
    }

}


