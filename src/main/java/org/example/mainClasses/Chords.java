package org.example.mainClasses;


import org.example.module.*;

import static org.example.module.ChordRepository.chordsRepositoryList;
import static org.example.mainClasses.Experiments.toAbsoluteNote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This is a helper class with static methods on Chord objects
// connectChords() should change octaves of bass and chords

public class Chords {
    public static ArrayList<Chord> connectChords(List<Chord> inList) {

        ArrayList<List<Chord>> toConnect = new ArrayList<>(inList.size());
        
        for (int i = 0; i < inList.size(); i++) {
            var chord = inList.get(i);
            Stream<Chord> temp = chordsRepositoryList.stream().parallel();

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
                    
            toConnect.add(temp.
            map(Chord::clone).
            map(a -> {a.setTickStartTime(chord.getTickStartTime()); return a;}).
            map(a -> {a.setTickEndTime(chord.getTickEndTime()); return a;}).
            map(a -> {a.setKeyScale(chord.getKeyScale()); return a;}).
            map(a -> {a.setChordDegree(chord.getChordDegree()); return a;}).
            map(a -> {a.setKeyRoot(chord.getKeyRoot()); return a;}).
            map(a -> {toAbsoluteChord(a); return a;}).
            peek(System.out::print).                   // <-- print each
            collect(Collectors.toList()));
            
        }
/* 
        for (int i = 1; i < toConnect.size(); i++) {
            for (int x = 0; x < toConnect.get(i-1).size(); x++) {
                for (int y = 0; y < toConnect.get(i).size(); y++) {

                    left = toConnect.get(i-1).get(x);
                    right = toConnect.get(i).get(y);

                    // toAbsoluteChord(left);
                    // toAbsoluteChord(right);

                    if (testAllRules(left, right)){
                        if (result.size() == 0) result.add(left);
                        result.add(i, right);
                        break; // if i need only one version.
                    }
                }
            }
        } */
        return new ArrayList<>();
    }

    private static boolean testAllRules(Chord a, Chord b) {
        return true;
    }

    public static void toAbsoluteChord(Chord chord) {
        var key = chord.getKeyRoot();       // is Ok
        var cd = chord.getChordDegree();    // is Ok
        var scale = chord.getKeyScale();    // is Ok
        int s = chord.getSoprano();         // is Ok
        int a = chord.getAlto();            // is Ok
        int t = chord.getTenor();           // is Ok
        int b = chord.getBass();            // is Ok


        // ok:
        Degree sd = Degree.values()[toDegreeIndex(s)];
        Degree ad = Degree.values()[toDegreeIndex(a)];
        Degree td = Degree.values()[toDegreeIndex(t)];
        Degree bd = Degree.values()[toDegreeIndex(b)];


        
        // in C any in scale:
        // BAGs solved:
        s = toAbsoluteNote(scale, cd, s, sd);
        a = toAbsoluteNote(scale, cd, a, ad);
        t = toAbsoluteNote(scale, cd, t, td);
        b = toAbsoluteNote(scale, cd, b, bd);

        // ok:
        s += + key.getKeyNumber();
        a += + key.getKeyNumber();
        t += + key.getKeyNumber();
        b += + key.getKeyNumber();

        // ok:
        chord.setSoprano(s);
        chord.setAlto(a);
        chord.setTenor(t);
        chord.setBass(b);
    }

    private static int toDegreeIndex(int a){ // makes sense only in C major
        

        int index = switch(a % 12){
            default-> -1;
            case 0 -> 0;
            case 2 -> 1;
            case 4 -> 2;
            case 5 -> 3;
            case 7 -> 4;
            case 9 -> 5;
            case 11 -> 6;
        };
        return index;
    }
}
