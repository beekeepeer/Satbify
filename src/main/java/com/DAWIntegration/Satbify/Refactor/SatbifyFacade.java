package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.ChordTypeKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.FinalNotesKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.InversionKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.MelodicPositionKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.PhrasePeriodKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.RegisterKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.RootKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.ScaleKeySwitchApplier;
import com.DAWIntegration.Satbify.Refactor.keySwitchAppliers.SpacingKeySwitchApplier;
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
        var preChords = NotesToChordsConverter.getInstance().notesToChords(this.allKS);
        preChords = PhrasePeriodKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = ChordFinder.getInstance().findMissingChord(this.allKS, preChords);
        preChords = FinalNotesKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = RootKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords); 
        preChords = ScaleKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = MelodicPositionKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = ChordTypeKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = InversionKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = SpacingKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        // preChords = AlterationKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = RegisterKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        List<List<FatChord>> versions = ChordVersionCreator.getInstance().filterChordVersions(this.allKS, preChords);
        List<FatChord> connectedChords = ChordVersionsConnector.getInstance().connectChords(versions, this.allKS);
        String out = ChordsToReaperSerializer.getInstance().ChordsToJson(connectedChords);
        System.out.println(out);
        // 
        return out;
    }
}