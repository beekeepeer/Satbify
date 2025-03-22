package com.DAWIntegration.Satbify.Refactor;

import java.util.List;

import static com.DAWIntegration.Satbify.Refactor.SatbifyMethods.*;
import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.module.Spacing;
/*
 * This class only moves information about the musical chord spacing
 * from the keySwitches to the Chord objects to store.
 */
public class SpacingKeySwitchApplier implements KeySwitchApplier{

    public static SpacingKeySwitchApplier getInstance() {
        return new SpacingKeySwitchApplier();
    }

    @Override
    public List<FatChord> applyKeySwitch(List<Note> allKS, List<FatChord> preChords) {
        for (FatChord chord : preChords) {
            for (Note note : allKS) {
                if (isSpacing(note.pitch()) && shouldApplyNonLatching(note, chord)) {
                    applySpacing(chord, note);
                }
            }
        }
        return preChords;
    }

    private boolean isSpacing(int pitch) {
        return pitch == Spacing.CLOSE.getKeySwith() ||
                pitch == Spacing.OPEN.getKeySwith() ||
                pitch == Spacing.MIXED_1.getKeySwith();
    }

    private void applySpacing(FatChord chord, Note note) {
        switch (note.pitch()) {
                case 36: chord.setSpacing(Spacing.CLOSE);break;
                case 37: chord.setSpacing(Spacing.OPEN);break;
                case 38: chord.setSpacing(Spacing.MIXED_1);break;
            }
    }

}
