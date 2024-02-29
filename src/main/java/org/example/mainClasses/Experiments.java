package org.example.mainClasses;


import org.example.module.Degree;
import org.example.module.Scale;

public class Experiments {              // ok
    public static int toAbsoluteNote(
        final Scale scale, 
        final Degree chordDegree, 
         int note,                 // ok
         Degree noteDegree) {

            // System.out.println("note: " + note);

        // first to key C
        int[] I = new int[] { 24, 36, 48, 60, 72, 84, 96 };
        int[] I_Sharp = new int[] { 25, 37, 49, 61, 73, 85, 97 };
        int[] II = new int[] { 26, 38, 50, 62, 74, 86, 98 };
        int[] II_Sharp = new int[] { 27, 39, 51, 63, 75, 87, 99 };
        int[] III = new int[] { 28, 40, 52, 64, 76, 88, 100 };
        int[] IV = new int[] { 29, 41, 53, 65, 77, 89, 101 };
        int[] IV_Sharp = new int[] { 30, 42, 54, 66, 78, 90, 102 };
        int[] V = new int[] { 31, 43, 55, 67, 79, 91, 103 };
        int[] V_Sharp = new int[] { 32, 44, 56, 68, 80, 92, 104 };
        int[] VI = new int[] { 33, 45, 57, 69, 81, 93, 105 };
        int[] VI_Sharp = new int[] { 34, 46, 58, 70, 82, 94, 106 };
        int[] VII = new int[] { 35, 47, 59, 71, 83, 95, 107 };

        int[] outputNotesArray = new int[7];

        
        int index = (noteDegree.ordinal() + chordDegree.ordinal()) % 7;
        noteDegree = Degree.values()[index];

        System.out.println("index ============== " + index);

        System.out.println("chordDegree: " + chordDegree + chordDegree.ordinal());
        System.out.println("noteDegree: " + noteDegree + noteDegree.ordinal());

            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> III;
                case IV -> IV;
                case V -> V;
                case VI -> VI;
                case VII -> VII;
            };
        
        if (scale == Scale.MAJOR_HARMONIC) {
            outputNotesArray = V_Sharp;
        } else if (scale == Scale.MINOR_NATURAL) {
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> II_Sharp;
                case IV -> IV;
                case V -> V;
                case VI -> V_Sharp;
                case VII -> VI_Sharp;
            };
        } else if (scale == Scale.MINOR_HARMONIC) {
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> II_Sharp;
                case IV -> IV;
                case V -> V;
                case VI -> V_Sharp;
                case VII -> VII;
            };
        } else if (scale == Scale.MINOR_MELODIC) { // ascending minor melodic. descending should be natural.
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> II_Sharp;
                case IV -> IV;
                case V -> V;
                case VI -> VI;
                case VII -> VII;
            };
        }

        System.out.println(" - - - - note  - - - -" + note);

        // returns the specific note number
        for (int i = 0; i < outputNotesArray.length; i++) {
            if(outputNotesArray[i] == note) {
                System.out.println("FIRST if !!!!");
                return outputNotesArray[i];
            };

            System.out.println("Math.abs(outputNotesArray[i] - note) ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; ; " + Math.abs(outputNotesArray[i] - note));
            if(Math.abs(outputNotesArray[i] - note) <= 7) { // TODO: no idea how this number works!
                System.out.println("SECOND if !!!! " + i);
                return outputNotesArray[i-1]; // -1 for 1 octave lower all notes.
            };
        }
    
    return 0;
      }

}



