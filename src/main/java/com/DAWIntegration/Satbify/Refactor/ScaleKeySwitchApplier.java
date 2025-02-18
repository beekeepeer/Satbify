package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import com.DAWIntegration.Satbify.module.Note;

public class ScaleKeySwitchApplier implements KeySwitchApplier{

    public static ScaleKeySwitchApplier getInstance() {
        return new ScaleKeySwitchApplier();
    }


    @Override
    public List<FatChord> applyKeySwitch(List<Note> notes, List<FatChord> preChords) {
        var  backlash = 0.1;
        for (FatChord chord : preChords) {
           for (Note note : notes) {
                if(chord.getStartTime() + backlash > note.startTime()
                && chord.getStartTime() < note.endTime() - backlash
                && !isLatching(note.pitch())) {
                        
                    
                }
           } 
        }
        return preChords;
    }


    private boolean isLatching(int pitch) {
        return false;
    }

}
