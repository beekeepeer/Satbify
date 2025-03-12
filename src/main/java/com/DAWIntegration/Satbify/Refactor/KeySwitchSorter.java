package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Note;

import static com.DAWIntegration.Satbify.Refactor.NotesToChordsConverter.isDegree;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

// creates a list of Phrases with 2D lists of Chords
public class KeySwitchSorter {

    public static KeySwitchSorter getInstance() {
        return new KeySwitchSorter();
    }

    public List<Note> sortKeySwitches(List<Note> notes){
        ToIntFunction<Note> degreeLastSort = new ToIntFunction<Note>() {
            @Override
            public int applyAsInt(Note note) {
                if (isDegree(note.pitch())){
                    return 1;
                } else return -1;
            }
        };
        notes.sort(Comparator.comparingDouble(Note::startTime)
                .thenComparingInt(degreeLastSort));
        return notes;
    }

    

}
