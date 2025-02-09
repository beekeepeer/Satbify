package com.DAWIntegration.Satbify.Refactor;

public record MusicalTiming(int barNumber, int beatNumber) implements Cloneable {
    @Override
    public MusicalTiming clone() {
        return new MusicalTiming(barNumber, beatNumber);
    }
}
