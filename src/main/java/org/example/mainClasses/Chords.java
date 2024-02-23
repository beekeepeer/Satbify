package org.example.mainClasses;

import org.example.module.Alteration;
import org.example.module.Chord;
import org.example.module.ChordType;
import org.example.module.Degree;
import org.example.module.Inversion;
import org.example.module.MelodicPosition;
import org.example.module.Occurrence;

import static org.example.module.ChordRepository.chordsRepositoryList;
import org.example.module.Scale;
import org.example.module.Spacing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This is a helper class with static methods on Chord objects

public class Chords {
    public static ArrayList<ArrayList<Chord>> connectChords(List<Chord> inList) {

        ArrayList<ArrayList<Chord>> ToConnect = new ArrayList<>(inList.size());
        
        // iterate through inList
        // TODO  use Optional for dealing with null values.
        for (int i = 0; i<inList.size(); i++) {
            var chord = inList.get(i);

            Stream<Chord> temp = chordsRepositoryList.stream();

            if (chord.getMelodicPosition() != null)
                temp = temp.filter(x -> x.getMelodicPosition() == chord.getMelodicPosition());
            if (chord.getSpacing() != null)
                temp = temp.filter(x -> x.getSpacing() == chord.getSpacing());
            if (chord.getInversion() != null)
                temp = temp.filter(x -> x.getInversion() == chord.getInversion());
            if (chord.getType() != null)
                temp = temp.filter(x -> x.getType() == chord.getType());
            if (chord.getAlteration() != null)
                temp = temp.filter(x -> x.getAlteration() == chord.getAlteration());
            if (chord.getOccurrence() != null)
                temp = temp.filter(x -> x.getOccurrence() == chord.getOccurrence());
                    
            temp = temp.map(a -> {a.setTickStartTime(chord.getTickStartTime()); return a;}).
            map(a -> {a.setTickEndTime(chord.getTickEndTime()); return a;}).
            map(a -> {a.setKeyScale(chord.getKeyScale()); return a;}).
            map(a -> {a.setChordDegree(chord.getChordDegree()); return a;}).
            map(a -> {a.setKeyRoot(chord.getKeyRoot()); return a;});

            ToConnect.add(i, temp.collect(Collectors.toCollection(ArrayList::new)));    
        } 
        // ToConnect.forEach(System.out::println);
        
        // each list in this collection is a connected sequence following the rules.
        // size is not known ahead of time.
        ArrayList<ArrayList<Chord>> result = new ArrayList<>();

        //  the loop connects two chords if they do not contain forbidden moves.
        // use logic of break the loop for "depth first" instead of recursion
        int TimePositionIndex = 0, iLeft = 0, iRight = 1;
        int l = ToConnect.size();
        result.get(0).add(ToConnect.get(0).get(0));
        
        outer: while (TimePositionIndex < l) {
            innerLeft: while (iLeft < ToConnect.get(TimePositionIndex).size()) {
                if (testAllRules(ToConnect.get(TimePositionIndex).get(iLeft),
                                 ToConnect.get(TimePositionIndex + 1).get(iRight))) {
                                    if (TimePositionIndex == 0) {//, add this first chord and second
                                        result.add(new ArrayList<Chord>(l));
                                        result.get(0).add(0, ToConnect.get(TimePositionIndex).get(iLeft));
                                        result.get(0).add(1, ToConnect.get(TimePositionIndex + 1).get(iRight));
                                    }
                                    // else, add only the Chord with index (TimePositionIndex+1)
                                    TimePositionIndex++;
                                    
                    } else {
                        iLeft++;
                        iRight++;
                    }

            }
        }

        return result;
    }

    private static boolean testAllRules(Chord a, Chord b) {
        if (a.equals(b))
            return true;

        return false;
    }

    /*
     * logic:
     * check for parallels and other forbidden moves.
     * last operation - transpose chosen chords in C to the "keyRoot"
     */

    // add methods to cast int to enums using switch statement

    /*
     * TODO:- Отложить гармонизацию голосов на потом. Сейчас просто соеденять
     * указанные аккорды.
     * - Все указанные в учебнике ВИДЫ СОЕДЕНЕНИЙ долджны быть
     * организованы в виде методов в этом вспомогательном классе,
     * а не в виде enum как в других случаях.
     * - Также должно быть учтено всё что связано с понятием "Приготовление"
     * - Additional methods for each alteration. Do not take an altered chord.
     * - inside a connection method, add an option to lower bass one octave lower.
     * it will reduce the repository twice. If between tenor and bass is exact
     * octave -
     * raise bass octave higher in the repository.
     * - add an operation to change octave of the whole chord if the previous chord
     * is
     * significantly far from current or next by majority of voices.
     * 
     */

    /*
     * here is how to cast int to enum:
     * 
     * public enum MyEnum {
     * EnumValue1,
     * EnumValue2;
     * 
     * public static MyEnum fromInteger(int x) {
     * switch(x) {
     * case 0:
     * return EnumValue1;
     * case 1:
     * return EnumValue2;
     * }
     * return null;
     * }
     * }
     */
    public static int relativizeNote(Scale scale, int chordDegree, int octave) {

        int[] I = new int[] { 24, 36, 48, 60, 72, 84, 96 };
        int[] II = new int[] { 26, 38, 50, 62, 74, 86, 98 };
        int[] IIIb = new int[] { 27, 39, 51, 63, 75, 87, 99 };
        int[] III = new int[] { 28, 40, 52, 64, 76, 88, 100 };
        int[] IV = new int[] { 29, 41, 53, 65, 77, 89, 101 };
        int[] V = new int[] { 31, 43, 55, 67, 79, 91, 103 };
        int[] VIb = new int[] { 32, 44, 56, 68, 80, 92, 104 };
        int[] VI = new int[] { 33, 45, 57, 69, 81, 93, 105 };
        int[] VIIb = new int[] { 34, 46, 58, 70, 82, 94, 106 };
        int[] VII = new int[] { 35, 47, 59, 71, 83, 95, 107 };

        int[] outputNotesArray = new int[7];

        if (scale.ordinal() == 13) { // major natural
            outputNotesArray = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> III;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VI;
                case 23 -> VII;
            };
        } else if (scale.ordinal() == 15) { // major harmonic
            outputNotesArray = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> III;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VIb;
                case 23 -> VII;
            };
        } else if (scale.ordinal() == 18) { // minor natural
            outputNotesArray = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> IIIb;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VIb;
                case 23 -> VIIb;
            };
        } else if (scale.ordinal() == 20) { // minor harmonic
            outputNotesArray = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> IIIb;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VIb;
                case 23 -> VII;
            };
        } else if (scale.ordinal() == 22) { // ascending minor melodic. descending should be natural.
            outputNotesArray = switch (chordDegree) {
                default -> I;
                case 14 -> II;
                case 16 -> IIIb;
                case 17 -> IV;
                case 19 -> V;
                case 21 -> VI;
                case 23 -> VII;
            };
        }

        // returns the specific note on MIDI message as int from string argument
        return outputNotesArray[octave] + scale.ordinal();
    }
}
