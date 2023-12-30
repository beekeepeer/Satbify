package org.example.mainClasses;

import static org.example.module.Chord.keyRoot;
import static org.example.module.Chord.keyScale;

public class Scale {

    public static int relativizeNote(int chordDegree, int octave) {

        int[] I = new int[]{24, 36, 48, 60, 72, 84, 96};
        int[] II = new int[]{26, 38, 50, 62, 74, 86, 98};
        int[] IIIb = new int[]{27, 39, 51, 63, 75, 87, 99};
        int[] III = new int[]{28, 40, 52, 64, 76, 88, 100};
        int[] IV = new int[]{29, 41, 53, 65, 77, 89, 101};
        int[] V = new int[]{31, 43, 55, 67, 79, 91, 103};
        int[] VIb = new int[]{32, 44, 56, 68, 80, 92, 104};
        int[] VI = new int[]{33, 45, 57, 69, 81, 93, 105};
        int[] VIIb = new int[]{34, 46, 58, 70, 82, 94, 106};
        int[] VII = new int[]{35, 47, 59, 71, 83, 95, 107};

        int[] theNote = new int[7];

        if (keyScale == 13) {                      // major natural
            theNote = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> III;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VI;
                case 23 -> VII;
            };
        } else if (keyScale == 15) {                      // major harmonic
            theNote = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> III;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VIb;
                case 23 -> VII;
            };
        } else if (keyScale == 18) {                      // minor natural
            theNote = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> IIIb;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VIb;
                case 23 -> VIIb;
            };
        } else if (keyScale == 20) {                      // minor harmonic
            theNote = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> IIIb;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VIb;
                case 23 -> VII;
            };
        } else if (keyScale == 22) {                      // ascending minor melodic. descending should be natural.
            theNote = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> IIIb;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VI;
                case 23 -> VII;
            };
        }




        return theNote[octave] + keyRoot;
    }
}
