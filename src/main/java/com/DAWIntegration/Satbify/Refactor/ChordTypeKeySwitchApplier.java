package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.shouldApplyNonLatching;
import com.DAWIntegration.Satbify.module.ChordType;
import com.DAWIntegration.Satbify.module.Note;

/*
 * This class only moves information about the musical scale context
 * from the keySwitches to the Chord objects to store.
 */

public class ChordTypeKeySwitchApplier implements KeySwitchApplier{

    public static ChordTypeKeySwitchApplier getInstance() {
        return new ChordTypeKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (FatChord chord : preChords) {
            for (Note note : allKS) {
                if (isChodType(note.pitch()) && shouldApplyNonLatching(note, chord)) {
                    applyChodType(chord, note);
                }
            }
        }
        return preChords;
    }


    public boolean isChodType(int pitch) {
        return pitch == ChordType.TRIAD.getKeySwitch() ||
        pitch == ChordType.SEVENTH_CHORD.getKeySwitch()||
        pitch == ChordType.NINTH_CHORD.getKeySwitch();
    }

    private void applyChodType(FatChord chord, Note note) {
        switch (note.pitch()) {
            case 24: chord.setChordType(ChordType.TRIAD); break;
            case 25: chord.setChordType(ChordType.SEVENTH_CHORD); break;
            case 26: chord.setChordType(ChordType.NINTH_CHORD); break;
        }
    }
}
