package com.DAWIntegration.Satbify.Refactor.keySwitchAppliers;

import java.util.List;

import com.DAWIntegration.Satbify.Refactor.FatChord;
import com.DAWIntegration.Satbify.module.Inversion;
import com.DAWIntegration.Satbify.module.Note;
import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.*;

/*
 * This class only moves information about the musical chord inversion
 * from the keySwitches to the Chord objects to store.
 */

public class InversionKeySwitchApplier implements KeySwitchApplier{

    public static InversionKeySwitchApplier getInstance() {
        return new InversionKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (FatChord chord : preChords) {
            for (Note note : allKS) {
                if (isInversion(note.pitch()) && shouldApplyNonLatching(note, chord)) {
                    applyInversion(chord, note);
                }
            }
        }
        return preChords;
    }

    private boolean isInversion(int pitch) {
        return pitch == Inversion.ROOT_POSITION.getKeySwitch() ||
            pitch == Inversion.FIRST_INVERSION.getKeySwitch() ||
            pitch == Inversion.SECOND_INVERSION.getKeySwitch() ||
            pitch == Inversion.THIRD_INVERSION.getKeySwitch();
    }

    private void applyInversion(FatChord chord, Note note) {
        switch (note.pitch()) {
            case 27: chord.setInversion(Inversion.ROOT_POSITION);break;
            case 28: chord.setInversion(Inversion.FIRST_INVERSION);break;
            case 29: chord.setInversion(Inversion.SECOND_INVERSION);break;
            case 30: chord.setInversion(Inversion.THIRD_INVERSION);break;
        }
    }


}
