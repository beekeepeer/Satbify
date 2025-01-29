package com.DAWIntegration.Satbify.module;

import java.util.List;
public record Period (List<Phrase> phrases) {

    public void addPhrase(Phrase phrase){
        phrases.add(phrase);
    }
}
