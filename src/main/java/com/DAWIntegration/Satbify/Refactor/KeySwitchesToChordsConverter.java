package com.DAWIntegration.Satbify.Refactor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.DAWIntegration.Satbify.module.ChordType;
import com.DAWIntegration.Satbify.module.Degree;
import com.DAWIntegration.Satbify.module.Inversion;
import com.DAWIntegration.Satbify.module.Key;
import com.DAWIntegration.Satbify.module.MelodicPosition;
import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.module.Scale;
import com.DAWIntegration.Satbify.module.Spacing;

import static com.DAWIntegration.Satbify.module.SatbifyMethods.isDegree;
public class KeySwitchesToChordsConverter {

    public static KeySwitchesToChordsConverter getInstance() {
        return new KeySwitchesToChordsConverter();
    }

    public List<FatChord> notesToChords(List<Note> notes) {
        final List<FatChord> preChords = new ArrayList<>();
        final List<Note> ActiveKeySwitches = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (!isDegree(note.pitch())) {
                updateActiveKeySwitches(note, ActiveKeySwitches, note.start());
            } else {
                addChord(preChords, note, ActiveKeySwitches);
            }
        }
        return preChords;
    }

    private void updateActiveKeySwitches(Note newKeySwitch, List<Note> activeKeySwitches, double currentTime) {
        Iterator<Note> iterator = activeKeySwitches.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (isNotLatchingOutdated(note, currentTime) || areLatching(note, newKeySwitch)) {
                iterator.remove();
            }
        }
        activeKeySwitches.add(newKeySwitch);
    }

    private boolean isNotLatchingOutdated(Note note, double currentTime) {
        return !isLatching(note) && isOutdated(note, currentTime);
    }

    private boolean areLatching(Note note, Note newKeySwitch) {
        return isLatching(note) && isLatching(newKeySwitch);
    }

    private boolean isLatching(Note note) {
        return note.pitch() <= 11;
    }

    private boolean isOutdated(Note note, double currentTime) {
        return note.end() <= currentTime;
    }

    private void addChord(List<FatChord> preChords, Note note, List<Note> activeKeySwitches) {
        var chord = FatChord.getInstance();
        for (Note keySwitch : activeKeySwitches) {
            applyKeySwitch(keySwitch.pitch(), chord);
            System.out.println(chord.getChordDegree());
        }
        preChords.add(chord);
    }

    private void applyKeySwitch(int pitch, FatChord chord) {
        switch (pitch) {
            case 0: chord.setKeyRoot(Key.C); break;
            case 1: chord.setKeyRoot(Key.C_Sharp);break;
            case 2: chord.setKeyRoot(Key.D);break;
            case 3: chord.setKeyRoot(Key.D_Sharp);break;
            case 4: chord.setKeyRoot(Key.E);break;
            case 5: chord.setKeyRoot(Key.F);break;
            case 6: chord.setKeyRoot(Key.F_Sharp);break;
            case 7: chord.setKeyRoot(Key.G);break;
            case 8: chord.setKeyRoot(Key.G_Sharp);break;
            case 9: chord.setKeyRoot(Key.A);break;
            case 10: chord.setKeyRoot(Key.A_Sharp);break;
            case 11: chord.setKeyRoot(Key.B);break;
            // Degree and scale:
            case 12: chord.setChordDegree(Degree.I);break;
            case 13: chord.setKeyScale(Scale.MAJOR_NATURAL);break;
            case 14: chord.setChordDegree(Degree.II);break;
            case 15: chord.setKeyScale(Scale.MAJOR_HARMONIC);break;
            case 16: chord.setChordDegree(Degree.III);break;
            case 17: chord.setChordDegree(Degree.IV);break;
            case 18: chord.setKeyScale(Scale.MINOR_NATURAL);break;
            case 19: chord.setChordDegree(Degree.V);break;
            case 20: chord.setKeyScale(Scale.MINOR_HARMONIC);break;
            case 21: chord.setChordDegree(Degree.VI);break;
            case 22: chord.setKeyScale(Scale.MINOR_MELODIC);break;
            case 23: chord.setChordDegree(Degree.VII);break;

//            case 30: legato = true;

            // a feature to add
//            case 108: alteration = null; break;
//            case 109: alteration = null; break;
//            case 110: alteration = null; break;
//            case 111: alteration = null; break;


            case 24: chord.setChordType(ChordType.TRIAD);break;
            case 25: chord.setChordType(ChordType.SEVENTH_CHORD);break;
            case 26: chord.setChordType(ChordType.NINTH_CHORD);break;

            case 27: chord.setInversion(Inversion.ROOT_POSITION);break;
            case 28: chord.setInversion(Inversion.FIRST_INVERSION);break;
            case 29: chord.setInversion(Inversion.SECOND_INVERSION);break;
            case 30: chord.setInversion(Inversion.THIRD_INVERSION);break;
            
            case 31: chord.setMelodicPosition(MelodicPosition.I);break;
            case 32: chord.setMelodicPosition(MelodicPosition.III);break;
            case 33: chord.setMelodicPosition(MelodicPosition.V);break;
            case 34: chord.setMelodicPosition(MelodicPosition.VII);break;
            case 35: chord.setMelodicPosition(MelodicPosition.IX);break;

            case 36: chord.setSpacing(Spacing.CLOSE);break;
            case 37: chord.setSpacing(Spacing.OPEN);break;
            case 38: chord.setSpacing(Spacing.MIXED_1);break;
//            case 127: c.setSpacing(Spacing.MIXED_2);break;
        }
    }

}
