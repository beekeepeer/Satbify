package com.DAWIntegration.Satbify;


import com.DAWIntegration.Satbify.module.*;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.DAWIntegration.Satbify.repository.ChordRepository.chordsRepositoryList;

public final class Chords {

    private static int id;
    public static String harmonise(String input) {
        int standard = 68; // todo: set it in applyKeySwitch()
        boolean legato = false; //todo: if true, noteEnd += 20, and join repeated notes.
        Chord c = new Chord();
        int pitch;
        List<Note> notes = parsArgs(input);
        ArrayList<ArrayList<Chord>> finalChords = new ArrayList<>();

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            c.setTickStartTime(note.start());
            c.setTickEndTime(note.end());
            pitch = note.pitch();
            int trackNumber = note.reaperTrack();
            c = applyKeySwitch(pitch, c);
            if (notes.size() != i && !isDegree(pitch)) {        // if this is a Degree, it should go further to add a new Chord.
                continue;
            } else if(trackNumber > 0) {
//                directlyPitchToVoices(trackNumber, pitch, c); // todo: set notes to harmonize and stay forever.
            }
            finalChords.add(populateFromRepo(c));

        }

        return returnToReaper (connectChords (finalChords, standard));
    }

    private static ArrayList<Note> parsArgs(String args) {
        //todo: apply all keySwitches before applying them!!!
        ToIntFunction<Note> function = new ToIntFunction<Note>() {
            @Override
            public int applyAsInt(Note note) {
                if (isDegree(note.pitch())){
                    return 1;
                } else return -1;
            }
        };
        return Arrays.stream(args.split("\n")).
                filter(s -> s.length() > 10).
                map(Chords::StringToNote).
                sorted(Comparator
                        .comparingInt(Note::start)
                        .thenComparingInt(function)).
                collect(Collectors.toCollection(ArrayList::new));
    }
    private static Chord applyKeySwitch(int pitch, Chord c) {
        switch (pitch) {
            case 0: c.setKeyRoot(Key.C); break;
            case 1: c.setKeyRoot(Key.C_Sharp);break;
            case 2: c.setKeyRoot(Key.D);break;
            case 3: c.setKeyRoot(Key.D_Sharp);break;
            case 4: c.setKeyRoot(Key.E);break;
            case 5: c.setKeyRoot(Key.F);break;
            case 6: c.setKeyRoot(Key.F_Sharp);break;
            case 7: c.setKeyRoot(Key.G);break;
            case 8: c.setKeyRoot(Key.G_Sharp);break;
            case 9: c.setKeyRoot(Key.A);break;
            case 10: c.setKeyRoot(Key.A_Sharp);break;
            case 11: c.setKeyRoot(Key.B);break;
            // Degree and scale:
            case 12: c.setChordDegree(Degree.I);break;
            case 13: c.setKeyScale(Scale.MAJOR_NATURAL);break;
            case 14: c.setChordDegree(Degree.II);break;
            case 15: c.setKeyScale(Scale.MAJOR_HARMONIC);break;
            case 16: c.setChordDegree(Degree.III);break;
            case 17: c.setChordDegree(Degree.IV);break;
            case 18: c.setKeyScale(Scale.MINOR_NATURAL);break;
            case 19: c.setChordDegree(Degree.V);break;
            case 20: c.setKeyScale(Scale.MINOR_HARMONIC);break;
            case 21: c.setChordDegree(Degree.VI);break;
            case 22: c.setKeyScale(Scale.MINOR_MELODIC);break;
            case 23: c.setChordDegree(Degree.VII);break;

//            case 30: legato = true; // todo: the keySwitch makes a strange side effect on result. Investigate harmonise().

            // a feature to add
//            case 108: alteration = null; break;
//            case 109: alteration = null; break;
//            case 110: alteration = null; break;
//            case 111: alteration = null; break;
            case 112: c.setMelodicPosition(MelodicPosition.I);break;
            case 113: c.setMelodicPosition(MelodicPosition.III);break;
            case 114: c.setMelodicPosition(MelodicPosition.V);break;
            case 115: c.setMelodicPosition(MelodicPosition.VII);break;
            case 116: c.setMelodicPosition(MelodicPosition.IX);break;

            case 117: c.setChordType(ChordType.TRIAD);break;
            case 118: c.setChordType(ChordType.SEVENTH_CHORD);break;
            case 119: c.setChordType(ChordType.NINTH_CHORD);break;

            case 120: c.setInversion(Inversion.ROOT_POSITION);break;
            case 121: c.setInversion(Inversion.FIRST_INVERSION);break;
            case 122: c.setInversion(Inversion.SECOND_INVERSION);break;
            case 123: c.setInversion(Inversion.THIRD_INVERSION);break;

            case 124: c.setSpacing(Spacing.CLOSE);break;
            case 125: c.setSpacing(Spacing.OPEN);break;
            case 126: c.setSpacing(Spacing.MIXED_1);break;
            case 127: c.setSpacing(Spacing.MIXED_2);break;
        }
        return c;
    }
    private static void directlyPitchToVoices(int trackNumber, int pitch, Chord c) {
        // create new Chord in special constructor inside which set the final value.
        switch (trackNumber) {
            case 1 -> c.setSoprano(pitch);
            case 2 -> c.setAlto(pitch);
            case 3 -> c.setTenor(pitch);
            case 4 -> c.setBass(pitch);
            default -> throw new IllegalStateException("Unexpected value of trackNumber: " + trackNumber);
        }
    }
    private static ArrayList<Chord> populateFromRepo(Chord c) {
        ArrayList<List<Chord>> clonedFromRepo = new ArrayList<>();
        return chordsRepositoryList
//                        .stream()
                .parallelStream()
                .filter(x -> c.getMelodicPosition() == null || x.getMelodicPosition() == c.getMelodicPosition())
                .filter(x -> c.getChordType() == null || x.getChordType() == c.getChordType())
                .filter(x -> c.getInversion() == null || x.getInversion() == c.getInversion())
                .filter(x -> c.getSpacing() == null || x.getSpacing() == c.getSpacing())
                .filter(x -> c.getAlteration() == null || x.getAlteration() == c.getAlteration())
                .filter(x -> c.getOccurrence() == null || x.getOccurrence() == c.getOccurrence())
                .map(x -> {
                    x = duplicate(x);
                    x.setKeyRoot(c.getKeyRoot());
                    x.setTickStartTime(c.getTickStartTime());
                    x.setTickEndTime(c.getTickEndTime());
                    x.setChordDegree(c.getChordDegree());
                    x.setKeyScale(c.getKeyScale());
                    x = applyRootDegreeScale(x);
                    return x;
                })
//                .peek(System.out::println)
                .flatMap(Chords::addBassVariant)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    private static ArrayList<Chord> connectChords(ArrayList<ArrayList<Chord>> clonedFromRepo, int standard) {
        ArrayList<Chord> finalChords;
        Random random = new Random();
//        finalChords = clonedFromRepo.stream()
////                .flatMap(List::stream)
//                .map(x -> x.get(0))
//                .toList();
        ChordConnector chordConnector = new ChordConnector();
        finalChords = chordConnector.connect(clonedFromRepo, standard);
        return finalChords;
    }
    private static boolean isDegree(int pitch) {
        return pitch == Degree.I.getKeyNumber() ||
                pitch == Degree.II.getKeyNumber() ||
                pitch == Degree.III.getKeyNumber() ||
                pitch == Degree.IV.getKeyNumber() ||
                pitch == Degree.V.getKeyNumber() ||
                pitch == Degree.VI.getKeyNumber() ||
                pitch == Degree.VII.getKeyNumber();
    }
    private static Chord duplicate(Chord original) {
        return new Chord(++id,
                original.getSoprano(),
                original.getAlto(),
                original.getTenor(),
                original.getBass(),
                original.getTickStartTime(),
                original.getTickEndTime(),
                original.getKeyRoot(),
                original.getChordDegree(),
                original.getKeyScale(),
                original.getMelodicPosition(),
                original.getChordType(),
                original.getInversion(),
                original.getSpacing(),
                original.getAlteration(),
                original.getOccurrence());
    }
    private static Stream<Chord> addBassVariant(Chord original){
        int t = original.getTenor();
        int b = original.getBass();
        if(t-b < 7){
            Chord variant = duplicate(original);
            variant.setBass(original.getBass() - 12);
            return Stream.of(original, variant);

        } else return Stream.of(original);
    }
    private static Chord applyRootDegreeScale(Chord x) {

        int s = raiseToDegree(x.getSoprano(), x.getChordDegree());
        s = applyScale(x.getKeyScale(), s);
        s += x.getKeyRoot().getKeyNumber();


        int a = raiseToDegree(x.getAlto(), x.getChordDegree());
        a = applyScale(x.getKeyScale(), a);
        a += x.getKeyRoot().getKeyNumber();                 // ok


        int t = raiseToDegree(x.getTenor(), x.getChordDegree());
        t = applyScale(x.getKeyScale(), t);
        t += x.getKeyRoot().getKeyNumber();

        int b = raiseToDegree(x.getBass(), x.getChordDegree());
        b = applyScale(x.getKeyScale(), b);
        b += x.getKeyRoot().getKeyNumber();




//        System.out.println(x.getSoprano());
//        System.out.println(raiseToDegree(x.getSoprano(), x.getChordDegree()));
//        System.out.println(raiseToDegree(x.getAlto(), x.getChordDegree()));
//        System.out.println(raiseToDegree(x.getTenor(), x.getChordDegree()));
//        System.out.println(raiseToDegree(x.getBass(), x.getChordDegree()));
//        System.out.println();

        x.setSoprano(s);
        x.setAlto(a);
        x.setTenor(t);
        x.setBass(b);

        return x;
    }
    private static int raiseToDegree(int noteNumber, Degree degree) {
        var ordinal = degree.ordinal();
        // todo: make a notes list for pentatonic and hexatonic. Add a "Scale" method argument
        List<Integer> notes = List.of(0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57, 59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101, 103, 105, 107, 108, 110, 112, 113, 115, 117, 119, 120, 122, 124, 125);
        var a = notes.indexOf(noteNumber);
        return notes.get(a + ordinal);
    }
    private static int applyScale(Scale scale, int n) {
        int bypass = n;
        switch (scale){ // I'm proud of this humble solution:
            case MAJOR_HARMONIC: if (n % 12 == 9) n--; break;
            case MINOR_NATURAL : if (n % 12 == 9 || n % 12 == 4 || n % 12 == 11) n--; break;
            case MINOR_HARMONIC: if (n % 12 == 9 || n % 12 == 4) n--; break;
            case MINOR_MELODIC : if (n % 12 == 4) n--; break;

        }
//        return bypass;
        return  n;
    }
    private static Note StringToNote(String s) {
        var ar = s.split(", ");

        int reaperTrack = Integer.parseInt(ar[0]);
        int pitch = Integer.parseInt(ar[1]);
        int start = Integer.parseInt(ar[2]);
        int end = Integer.parseInt(ar[3]);

        return new Note(reaperTrack, pitch, start, end);
    }
    private static String returnToReaper(ArrayList<Chord> finalChords) {
        StringBuilder out = new StringBuilder();
        try {
            for (int i = 0; i < finalChords.size(); i++) {
                String start = ", " +
                        finalChords.get(i).getTickStartTime(),
                        end = ", " + (finalChords.get(i).getTickEndTime()) + "\n";
                out = out.
                        append("1, ").append(finalChords.get(i).getSoprano()).append(start).append(end).
                        append("2, ").append(finalChords.get(i).getAlto()).append(start).append(end).
                        append("3, ").append(finalChords.get(i).getTenor()).append(start).append(end).
                        append("4, ").append(finalChords.get(i).getBass()).append(start).append(end);
            }
        } catch (Exception e) {
            throw new RuntimeException("   ! \n! ! ! \n! ! ! ! \n! ! ! !The output of This program is EMPTY ! ! ! \n! ! \n ! !\n ! ! ! ");
        }
        return out.toString();
    }
}
