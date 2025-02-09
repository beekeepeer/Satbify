package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Chord;

import java.util.List;

// Предложение
public record Phrase(PhrasePosition position,       // beginning of a period / middle / end
                     int phraseNumberInPeriod,
                     int phraseLength,              // number of bars in a phrase
                     List<Chord> chords) {          // each chord contains musical timing
}
