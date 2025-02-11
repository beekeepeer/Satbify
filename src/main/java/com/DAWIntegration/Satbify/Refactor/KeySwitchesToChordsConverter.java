package com.DAWIntegration.Satbify.Refactor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
    private int phraseNumber, periodNumber = 1;
    private Note x;
    
        public static KeySwitchesToChordsConverter getInstance() {
            return new KeySwitchesToChordsConverter();
        }
    
        public List<FatChord> notesToChords(List<Note> notes) {
            final List<FatChord> preChords = new ArrayList<>();
            final List<Note> activeKeySwitches = new ArrayList<>();
            double currentTime = 0.0;
            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                if (!isDegree(note.pitch())) {
                    activeKeySwitches.add(note);
                } else {
                    updateActiveKeySwitches(note, activeKeySwitches, currentTime);
                    currentTime = note.end();
                    addChord(preChords, note, activeKeySwitches);
                }
            }
            return preChords;
        }
    
        private void updateActiveKeySwitches(Note newKeySwitch, List<Note> activeKeySwitches, double currentTime) {
            Iterator<Note> iterator = activeKeySwitches.iterator();
            while (iterator.hasNext()) {
                Note note = iterator.next();
                    incrementPhrasePeriod(note);
                if (isNotLatchingOutdated(note, currentTime) || bothLatching(note, newKeySwitch)) {
                    iterator.remove();
                }
            }
        }
    
        private void incrementPhrasePeriod(Note note) {
            if (note.pitch() != 108 || this.x == note) {
                return;
            }
            if (!Objects.isNull(this.x) && this.x.end() < (note.start())) {
                this.periodNumber++;
            }
            this.phraseNumber++;
            this.x = note;
        }

    
    
        private boolean isNotLatchingOutdated(Note note, double currentTime) {
            return !isLatching(note) && isOutdated(note, currentTime);
        }
    
        private boolean bothLatching(Note note, Note newKeySwitch) {
            return isLatching(note) && isLatching(newKeySwitch);
        }
    
        // TODO come up with several groups of latching + switchable keySwitches: roots, registers, functions, 
        private boolean isLatching(Note note) {
            // RootKeys
            return note.pitch() <= 11;
        }
    
        private boolean isOutdated(Note note, double currentTime) {
            return note.end() <= currentTime; // TODO round
        }
    
        private void addChord(List<FatChord> preChords, Note ChordDegreeNote, List<Note> activeKeySwitches) {
            var chord = FatChord.getInstance();
            applyKeySwitch(ChordDegreeNote.pitch(), chord);
            chord.setPeriodNumber(this.periodNumber);
        for (Note keySwitch : activeKeySwitches) {
            applyKeySwitch(keySwitch.pitch(), chord);
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
            case 39: chord.setLegato(false);

            // register
            case 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78:
            case 79: chord.setRegister(pitch);

            case 108: chord.setPhraseNumber(this.phraseNumber);

        }
    }

}
