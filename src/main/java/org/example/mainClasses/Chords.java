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
            collect(Collectors.toList()));
        }
        
        System.out.println(toConnect.size());
        System.out.println(toConnect.get(0).get(0).getSoprano());
        System.out.println(toConnect.get(0).get(0).getAlto());
        System.out.println(toConnect.get(0).get(0).getTenor());
        System.out.println(toConnect.get(0).get(0).getBass());
        
    

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
       
        // if (false)
        //     return false;

        return true;
    }

    public static void toAbsoluteChord(Chord chord) {

        int s = toAbsoluteNote(chord.getKeyScale(), chord.getChordDegree(), chord.getSopranoNote());
        int a = toAbsoluteNote(chord.getKeyScale(), chord.getChordDegree(), chord.getAltoNote());
        int t = toAbsoluteNote(chord.getKeyScale(), chord.getChordDegree(), chord.getTenorNote());
        int b = toAbsoluteNote(chord.getKeyScale(), chord.getChordDegree(), chord.getBassNote());

         // TODO: check it... I do not like this logic. It is not clear for me, and it is not predictable, I ges.
        if(b > t) t += 12;
        if(t > a) a += 12;
        if(a > s) s += 12;
        if(b <= s) s += 12;

        switch (chord.keyRoot) { // apply key to chord
            case C -> {}
            case C_Sharp -> {s+=1; a+=1; t+=1; b+=1;}
            case D -> {s+=2; a+=2; t+=2; b+=2;}
            case D_Sharp -> {s+=3; a+=3; t+=3; b+=3;}
            case E -> {s+=4; a+=4; t+=4; b+=4;}
            case F -> {s+=5; a+=5; t+=5; b+=5;}
            case F_Sharp -> {s+=6; a+=6; t+=6; b+=6;}
            case G -> {s+=7; a+=7; t+=7; b+=7;}
            case G_Sharp -> {s+=8; a+=8; t+=8; b+=8;}
            case A -> {s+=9; a+=9; t+=9; b+=9;}
            case A_Sharp -> {s+=10; a+=10; t+=10; b+=10;}
            case B -> {s-=1; a-=1; t-=1; b-=1;}         // this is the only case too high for human voices
        }

        try {
            chord.setSoprano(s);
            chord.setAlto(a);
            chord.setTenor(t);
            chord.setBass(b);
        } catch (NoteOutOfRangeException e) {
            e.printStackTrace();
        }
        // System.out.println(chord);
    }

    private static int toAbsoluteNote(Scale scale, Degree chordDegree, NoteOfScale note) {
        
        Degree noteDegree = note.getDegree();
        noteDegree = toNoteDegreeOfScale(chordDegree, noteDegree);
        int octave = note.getOctave();
        // if(chordDegree != Degree.I && note.getDegree() == Degree.I) octave++;
        // System.out.println("note degree is " + noteDegree);

        // first to key C
        int[] I = new int[] { 24, 36, 48, 60, 72, 84, 96 };
        int[] IIb = new int[] { 25, 37, 49, 61, 73, 85, 97 };
        int[] II = new int[] { 26, 38, 50, 62, 74, 86, 98 };
        int[] IIIb = new int[] { 27, 39, 51, 63, 75, 87, 99 };
        int[] III = new int[] { 28, 40, 52, 64, 76, 88, 100 };
        int[] IV = new int[] { 29, 41, 53, 65, 77, 89, 101 };
        int[] Vb = new int[] { 30, 42, 54, 66, 78, 90, 102 };
        int[] V = new int[] { 31, 43, 55, 67, 79, 91, 103 };
        int[] VIb = new int[] { 32, 44, 56, 68, 80, 92, 104 };
        int[] VI = new int[] { 33, 45, 57, 69, 81, 93, 105 };
        int[] VIIb = new int[] { 34, 46, 58, 70, 82, 94, 106 };
        int[] VII = new int[] { 35, 47, 59, 71, 83, 95, 107 };

        int[] outputNotesArray = new int[7];

        if (scale == Scale.MAJOR_NATURAL) {
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> III;
                case IV -> IV;
                case V -> V;
                case VI -> VI;
                case VII -> VII;
            };
        } else if (scale == Scale.MAJOR_HARMONIC) {
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> III;
                case IV -> IV;
                case V -> V;
                case VI -> VIb;
                case VII -> VII;
            };
        } else if (scale == Scale.MINOR_NATURAL) {
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> IIIb;
                case IV -> IV;
                case V -> V;
                case VI -> VIb;
                case VII -> VIIb;
            };
        } else if (scale == Scale.MINOR_HARMONIC) {
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> IIIb;
                case IV -> IV;
                case V -> V;
                case VI -> VIb;
                case VII -> VII;
            };
        } else if (scale == Scale.MINOR_MELODIC) { // ascending minor melodic. descending should be natural.
            outputNotesArray = switch (noteDegree) {
                case I -> I;
                case II -> II;
                case III -> IIIb;
                case IV -> IV;
                case V -> V;
                case VI -> VI;
                case VII -> VII;
            };
        }

        // returns the specific note on MIDI message as int
        return outputNotesArray[octave-3];
    }

    
    private static Degree toNoteDegreeOfScale(Degree chordDegree, Degree noteDegree) {
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
