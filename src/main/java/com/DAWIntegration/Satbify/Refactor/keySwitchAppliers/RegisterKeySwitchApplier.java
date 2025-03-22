package com.DAWIntegration.Satbify.Refactor.keySwitchAppliers;

import java.util.List;

import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.shouldApplyLatching;

import com.DAWIntegration.Satbify.Refactor.FatChord;
import com.DAWIntegration.Satbify.module.Note;
/*
 * This class only moves information about the musical register of chord (how low or high all the notes of the chord is)
 * from the keySwitches to the Chord objects to store.
 */
public class RegisterKeySwitchApplier {

    public static RegisterKeySwitchApplier getInstance() {
        return new RegisterKeySwitchApplier();
    }

    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (FatChord chord : preChords) {
            for (Note note : allKS) {
                if (isRegister(note) && shouldApplyLatching(note, chord)) {
                    applyRegister(chord, note);
                }
            }
        }
        return preChords;
    }

    private boolean isRegister(Note note) {
        return note.reaperTrack() == 0
                && note.pitch() > 50
                && note.pitch() < 80;
    }

    private void applyRegister(FatChord chord, Note note) {
        chord.setRegister(note.pitch());
    }

}
