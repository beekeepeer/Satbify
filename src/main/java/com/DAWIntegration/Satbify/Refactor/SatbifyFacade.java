package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Note;

import java.util.List;

public class SatbifyFacade {
    
    private final KeySwitchSorter keySwitchSorter = KeySwitchSorter.getInstance();
    
    public final List<Note> allKS;

    private SatbifyFacade(List<Note> notes) {
        this.allKS = this.keySwitchSorter.sortKeySwitches(notes);
    }

    public static SatbifyFacade getInstance(List<Note> notes) {
        return new SatbifyFacade(notes);
    }

    // facade method
    public String processRequest() {
        System.out.println("Facade");
        //set Root key, 
        var preChords = NotesToChordsConverter.getInstance().notesToChords(this.allKS);
        preChords = ChordFinder.getInstance().findMissingChord(this.allKS, preChords);
        preChords = FinalNotesKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = RootKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords); 
        preChords = ScaleKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = MelodicPositionKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = ChordTypeKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = InversionKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = SpacingKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = AlterationKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = RegisterKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = LegatoKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = PhrasePeriodKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = ChordConnector.getInstance().connectChords(this.allKS, preChords);

        return "";
    }
}