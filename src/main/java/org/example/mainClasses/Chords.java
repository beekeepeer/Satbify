package org.example.mainClasses;

import org.example.module.*;

import static org.example.module.ChordRepository.chordsRepositoryList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// This is a helper class with static methods on Chord objects
// connectChords() should change octaves of bass and chords
// toAbsoluteNote() should only apply key and scale.

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
            peek(System.out::print).                   // <-- print them
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
        var key = chord.getKeyRoot();
        var chordDegree = chord.getChordDegree();
        var scale = chord.getKeyScale();
        var sopranoDegree = chord.getSopranoNote().getDegree();
        var sopranoOctave = chord.getSopranoNote().getOctave();
        var altoDegree = chord.getAltoNote().getDegree();
        var altoOctave = chord.getAltoNote().getOctave();
        var tenorDegree = chord.getTenorNote().getDegree();
        var tenorOctave = chord.getTenorNote().getOctave();
        var bassDegree = chord.getBassNote().getDegree();
        var bassOctave = chord.getBassNote().getOctave();


        if (chordDegree != Degree.I){
        sopranoDegree = toNoteDegreeOfScale(chordDegree, sopranoDegree);
        altoDegree = toNoteDegreeOfScale(chordDegree, altoDegree);
        tenorDegree = toNoteDegreeOfScale(chordDegree, tenorDegree);
        bassDegree = toNoteDegreeOfScale(chordDegree, bassDegree);
        }
        System.out.println(sopranoDegree + " "+ altoDegree + " "+ tenorDegree + " "+ bassDegree);

        int s = sopranoDegree.getKeyNumber() + key.getKeyNumber() + scale.getSteps()[sopranoDegree.ordinal()] + (sopranoOctave * 12) - 12;
        int a = altoDegree.getKeyNumber() + key.getKeyNumber() + scale.getSteps()[altoDegree.ordinal()] + (altoOctave * 12) - 12;
        int t = tenorDegree.getKeyNumber() + key.getKeyNumber() + scale.getSteps()[tenorDegree.ordinal()] + (tenorOctave * 12) - 12;
        int b = bassDegree.getKeyNumber() + key.getKeyNumber() + scale.getSteps()[bassDegree.ordinal()] + (bassOctave * 12) - 12;

        chord.setSoprano(s);
        chord.setAlto(a);
        chord.setTenor(t);
        chord.setBass(b);
    }
    
    private static Degree toNoteDegreeOfScale(Degree chordDegree, Degree noteDegree) { // TODO delete this method.
        int i = noteDegree.ordinal();
        
        return switch (chordDegree) {
            case I -> Degree.values()[i % 7];
            case II -> Degree.values()[(i + 1) % 7]; 
            case III -> Degree.values()[(i + 2) % 7];
            case IV -> Degree.values()[(i + 3) % 7];
            case V -> Degree.values()[(i + 4) % 7];
            case VI -> Degree.values()[(i + 5) % 7];
            case VII -> Degree.values()[(i + 6) % 7];
        };
        
    }
}
