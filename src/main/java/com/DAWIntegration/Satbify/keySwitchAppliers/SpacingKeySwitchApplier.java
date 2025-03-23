package com.DAWIntegration.Satbify.keySwitchAppliers;

import static com.DAWIntegration.Satbify.service.SatbifyMethods.*;

import java.util.List;

import com.DAWIntegration.Satbify.module.FatChord;
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
                    chord.setSpacing(applySpacing(note));
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

    private Spacing applySpacing(Note note) {
        return switch (note.pitch()) {
            case 36 -> Spacing.CLOSE;
            case 37 -> Spacing.OPEN;
            case 38 -> Spacing.MIXED_1;
            default -> Spacing.MIXED_2; // unusable
        };
    }

}
