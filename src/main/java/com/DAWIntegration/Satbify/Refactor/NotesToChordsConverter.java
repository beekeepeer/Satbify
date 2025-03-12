package com.DAWIntegration.Satbify.Refactor;


import java.util.ArrayList;
import java.util.List;
import com.DAWIntegration.Satbify.module.Degree;
import com.DAWIntegration.Satbify.module.Note;

public class NotesToChordsConverter {

    public static NotesToChordsConverter getInstance() {
        return new NotesToChordsConverter();
    }

    public List<FatChord> notesToChords(List<Note> allKS) {
        List<FatChord> chords = new ArrayList<>();
        for (int i = 0; i < allKS.size(); i++) {
            Note note = allKS.get(i);
            if (isDegree(note.pitch())) {
                chords.add(noteToChord(note));
            }
        }
        return chords;
    }

    public static boolean isDegree(int pitch) {
        return pitch == Degree.I.getKeyNumber() ||
                pitch == Degree.II.getKeyNumber() ||
                pitch == Degree.III.getKeyNumber() ||
                pitch == Degree.IV.getKeyNumber() ||
                pitch == Degree.V.getKeyNumber() ||
                pitch == Degree.VI.getKeyNumber() ||
                pitch == Degree.VII.getKeyNumber();
    }

    private FatChord noteToChord(Note note) {
        FatChord chord = FatChord.getNewInstance();
        chord.setStartTime(note.startTime());
        chord.setEndTime(note.endTime());
        chord.setStartBar(note.startBar());
        chord.setEndBar(note.endBar());
        chord.setStartBeat(note.startBeat());
        chord.setEndBeat(note.endBeat());
        // apply degree only
        applyDegree(note, chord);

        return chord;
    }

    private void applyDegree(Note note, FatChord c) {
        switch (note.pitch()) {
            case 12: c.setChordDegree(Degree.I);break;
            case 14: c.setChordDegree(Degree.II);break;
            case 16: c.setChordDegree(Degree.III);break;
            case 17: c.setChordDegree(Degree.IV);break;
            case 19: c.setChordDegree(Degree.V);break;
            case 21: c.setChordDegree(Degree.VI);break;
            case 23: c.setChordDegree(Degree.VII);break;
        }
    }
}
