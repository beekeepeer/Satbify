package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import com.DAWIntegration.Satbify.module.MelodicPosition;
import com.DAWIntegration.Satbify.module.Note;

public class MelodicPositionKeySwitchApplier implements KeySwitchApplier {

    public static MelodicPositionKeySwitchApplier getInstance() {
        return new MelodicPositionKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (FatChord chord : preChords) {
            for (Note note : allKS) {
                if (isMelodicPosition(note.pitch())
                        && chord.getStartTime() >= note.startTime()
                        && chord.getStartTime() < note.endTime()
                        ) {
                    applyMelodicPosition(chord, note);
                }
            }
        }
        return preChords;
    }


    public boolean isMelodicPosition(int pitch) {
        return pitch == MelodicPosition.I.getKeySwitch() ||
                pitch == MelodicPosition.III.getKeySwitch() ||
                pitch == MelodicPosition.V.getKeySwitch() ||
                pitch == MelodicPosition.VII.getKeySwitch() ||
                pitch == MelodicPosition.IX.getKeySwitch();
    }

   private void applyMelodicPosition(FatChord chord, Note note) {
    switch (note.pitch()) {
            case 31: chord.setMelodicPosition(MelodicPosition.I);break;
            case 32: chord.setMelodicPosition(MelodicPosition.III);break;
            case 33: chord.setMelodicPosition(MelodicPosition.V);break;
            case 34: chord.setMelodicPosition(MelodicPosition.VII);break;
            case 35: chord.setMelodicPosition(MelodicPosition.IX);break;
    }}

}
