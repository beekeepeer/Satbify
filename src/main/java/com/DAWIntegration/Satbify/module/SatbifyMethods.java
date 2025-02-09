package com.DAWIntegration.Satbify.module;

public class SatbifyMethods {

public static boolean isDegree(int pitch) {
        return pitch == Degree.I.getKeyNumber() ||
                pitch == Degree.II.getKeyNumber() ||
                pitch == Degree.III.getKeyNumber() ||
                pitch == Degree.IV.getKeyNumber() ||
                pitch == Degree.V.getKeyNumber() ||
                pitch == Degree.VI.getKeyNumber() ||
                pitch == Degree.VII.getKeyNumber();
    }
}
