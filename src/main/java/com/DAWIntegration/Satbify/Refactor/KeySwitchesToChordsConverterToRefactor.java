package com.DAWIntegration.Satbify.Refactor;

import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.applyKeySwitch;
import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.isDegree;

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

public class KeySwitchesToChordsConverterToRefactor {
    private int phraseNumber, periodNumber = 1;
    private Note previousKS;
    
        public static KeySwitchesToChordsConverterToRefactor getInstance() {
            return new KeySwitchesToChordsConverterToRefactor();
        }
    
        public List<FatChord> notesToChords(List<Note> notes) {
            System.out.println(notes);
            final List<FatChord> preChords = new ArrayList<>();
            final List<Note> activeKeySwitches = new ArrayList<>();
            double currentTime = 0.0;
            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                if (!isDegree(note.pitch())) {
                    activeKeySwitches.add(note);
                } else {
                    System.out.println(i);
                    updateActiveKeySwitches(note, activeKeySwitches, currentTime);
                    currentTime = note.endTime();
                    addChord(preChords, note, activeKeySwitches);
                }
            }
            System.out.println(preChords);
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
            if (note.pitch() != 108 || this.previousKS == note) {
                return;
            }
            if (!Objects.isNull(this.previousKS) && this.previousKS.endTime() < (note.startTime())) {
                this.periodNumber++;
            }
            this.phraseNumber++;
            this.previousKS = note;
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
            return note.endTime() <= currentTime; // TODO round
        }
    
        private void addChord(List<FatChord> preChords, Note ChordDegreeNote, List<Note> activeKeySwitches) {
            var chord = FatChord.getNewInstance();
            applyKeySwitch(ChordDegreeNote.pitch(), chord);
            chord.setPeriodNumber(this.periodNumber);
            chord.setStartTime(ChordDegreeNote.startTime());
            chord.setEndTime(ChordDegreeNote.endTime());
        for (Note keySwitch : activeKeySwitches) {
            applyKeySwitch(keySwitch.pitch(), chord);
        }
        
        preChords.add(chord);
    }

}
