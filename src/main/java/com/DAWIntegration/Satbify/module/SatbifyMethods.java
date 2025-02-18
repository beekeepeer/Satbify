package com.DAWIntegration.Satbify.module;

import com.DAWIntegration.Satbify.Refactor.FatChord;

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

    public static void applyKeySwitch(int pitch, FatChord chord) {
        switch (pitch) {
            case 0: chord.setKeyRoot(Key.C); break;
            case 1: chord.setKeyRoot(Key.C_Sharp);break;
            case 2: chord.setKeyRoot(Key.D);break;
            case 3: chord.setKeyRoot(Key.D_Sharp);break;
            case 4: chord.setKeyRoot(Key.E);break;
            case 5: chord.setKeyRoot(Key.F);break;
            case 6: chord.setKeyRoot(Key.F_Sharp);break;
            case 7: chord.setKeyRoot(Key.G);break;
            case 8: chord.setKeyRoot(Key.G_Sharp);break;
            case 9: chord.setKeyRoot(Key.A);break;
            case 10: chord.setKeyRoot(Key.A_Sharp);break;
            case 11: chord.setKeyRoot(Key.B);break;
            // Degree and scale:
            case 12: chord.setChordDegree(Degree.I);break;
            case 13: chord.setKeyScale(Scale.MAJOR_NATURAL);break;
            case 14: chord.setChordDegree(Degree.II);break;
            case 15: chord.setKeyScale(Scale.MAJOR_HARMONIC);break;
            case 16: chord.setChordDegree(Degree.III);break;
            case 17: chord.setChordDegree(Degree.IV);break;
            case 18: chord.setKeyScale(Scale.MINOR_NATURAL);break;
            case 19: chord.setChordDegree(Degree.V);break;
            case 20: chord.setKeyScale(Scale.MINOR_HARMONIC);break;
            case 21: chord.setChordDegree(Degree.VI);break;
            case 22: chord.setKeyScale(Scale.MINOR_MELODIC);break;
            case 23: chord.setChordDegree(Degree.VII);break;


            // a feature to add
//            case 108: alteration = null; break;
//            case 109: alteration = null; break;
//            case 110: alteration = null; break;
//            case 111: alteration = null; break;


            case 24: chord.setChordType(ChordType.TRIAD);break;
            case 25: chord.setChordType(ChordType.SEVENTH_CHORD);break;
            case 26: chord.setChordType(ChordType.NINTH_CHORD);break;

            case 27: chord.setInversion(Inversion.ROOT_POSITION);break;
            case 28: chord.setInversion(Inversion.FIRST_INVERSION);break;
            case 29: chord.setInversion(Inversion.SECOND_INVERSION);break;
            case 30: chord.setInversion(Inversion.THIRD_INVERSION);break;
            
            case 31: chord.setMelodicPosition(MelodicPosition.I);break;
            case 32: chord.setMelodicPosition(MelodicPosition.III);break;
            case 33: chord.setMelodicPosition(MelodicPosition.V);break;
            case 34: chord.setMelodicPosition(MelodicPosition.VII);break;
            case 35: chord.setMelodicPosition(MelodicPosition.IX);break;

            case 36: chord.setSpacing(Spacing.CLOSE);break;
            case 37: chord.setSpacing(Spacing.OPEN);break;
            case 38: chord.setSpacing(Spacing.MIXED_1);break;
            case 39: chord.setLegato(false);

            // register
        //     case 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78:
        //     case 79: chord.setRegister(pitch);

        //     case 108: chord.setPhraseNumber(chord.phraseNumber);

        }
    }
}
