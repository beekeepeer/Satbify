package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Note;

import java.util.ArrayList;
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
        preChords = ScaleKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords);
        preChords = RootKeySwitchApplier.getInstance().applyKeySwitch(this.allKS, preChords); 

        return "";
    }
}