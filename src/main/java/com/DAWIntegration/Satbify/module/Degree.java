package com.DAWIntegration.Satbify.module;

public enum Degree {
    I(12),
    II(14),
    III(16),
    IV(17),
    V(19),
    VI(21),
    VII(23);

    private final int keyNumber;
    public int getKeyNumber() {
        return keyNumber;
    }
    Degree(int keyNumber) {
        this.keyNumber = keyNumber;
    }
    public static Degree whichDegree(int keyNumber) {

        // Ensure the input note is within the C major scale for testing repository only.
        int baseNote = keyNumber % 12;
        if (baseNote != 0 && baseNote != 2 && baseNote != 4 && baseNote != 5 &&
                baseNote != 7 && baseNote != 9 && baseNote != 11) {
            throw new IllegalArgumentException("Note number is not in the C major scale.");
        }

        for (Degree degree : Degree.values()) {
            if (degree.getKeyNumber() % 12 == keyNumber % 12) {
                return degree;
            }
        }
        throw new IllegalArgumentException("No enum constant with keyNumber " + keyNumber);
    }
}