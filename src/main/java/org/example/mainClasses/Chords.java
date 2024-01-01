package org.example.mainClasses;

import org.example.module.Chord;
import org.example.module.ChordRepository;

import java.util.ArrayList;
import java.util.List;

// This is a helper class with static methods on Chord objects

public class Chords {

    /* logic:
    filter chordRepository by chord "type"
    filter chordRepository by "chordDegree"
    filter chordRepository by "inversion"
    filter chordRepository by "alteration"
    if specified - filter chordRepository by "melodicPosition"
    if specified - filter chordRepository by "spacing"
    if specified - filter chordRepository by "inversion"


    check for parallels and other forbidden moves.
    last operation - transpose chosen chords in C to the "keyRoot"
    */

    // filter chords by keySwitches:
    public static ArrayList<Chord> filterByKeyswithch(ArrayList<Chord> allChords) {
        var filteredChords = new ArrayList<Chord>();
        allChords.stream().filter(a-> true);

        return filteredChords;
    }

    // add methods to cast int to enums using switch statement

    /* todo:
            - Все указанные в учебнике ВИДЫ СОЕДЕНЕНИЙ долджны быть
        организованы в виде методов в этом вспомогательном классе,
        а не в виде enum как в других случаях.
            - Также должно быть учтено всё что связано с понятием "Приготовление"
            - Additional methods for each alteration. Do not take a altered chord.

     */

    /*
    here is how to cast int to enum:

    public enum MyEnum {
    EnumValue1,
    EnumValue2;

    public static MyEnum fromInteger(int x) {
        switch(x) {
        case 0:
            return EnumValue1;
        case 1:
            return EnumValue2;
        }
        return null;
    }
}
     */

}
