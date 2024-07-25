package com.DAWIntegration.Satbify;


import com.DAWIntegration.Satbify.module.*;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.DAWIntegration.Satbify.repository.ChordRepository.chordsRepositoryList;

public abstract class Chords {
    private static int id;
    public static boolean smoothBass = false;
    public static String harmonise(String input) {
        int standard = 68;
        boolean legato = true;
        Chord c = new Chord();
        c.setTessitura(standard);
        int pitch;
        List<Note> notes = parsArgs(input);
        ArrayList<ArrayList<Chord>> clonedFromRepo = new ArrayList<>();

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            c.setTickStartTime(note.start());
            c.setTickEndTime(note.end());
            pitch = note.pitch();
            int trackNumber = note.reaperTrack();
            c = applyKeySwitch(pitch, c);
            if(trackNumber == 0 && pitch == 39) legato = false;
            if(trackNumber == 0 && pitch == 40) smoothBass = true;
            if(trackNumber == 0 && pitch > 50 && pitch < 80){
                c.setTessitura(pitch); // apply standard
            }
            if(trackNumber > 0 && trackNumber < 5) {
                c = directlyPitchToVoices(trackNumber, pitch, c);
            }
            if (notes.size() != i && !isDegree(pitch)) {        // if this is a Degree, it should go further to add a new Chord.
                continue;
            }
            clonedFromRepo.add(populateFromRepo(c));
            c = resetFinalNotes(c);
        }
        var connected = new ChordConnector().connect(clonedFromRepo);
        // reset inherited from past operations.
        c = new Chord();
        smoothBass = false;
        return returnToReaper (connected, legato);
    }

    private static Chord resetFinalNotes(Chord c) {
        c.setFinalSoprano(0);
        c.setFinalAlto(0);
        c.setFinalTenor(0);
        c.setFinalBass(0);
        Key keyRoot = Key.C;
        c.setMelodicPosition(null);
        c.setChordType(ChordType.TRIAD);
        c.setInversion(null);
        c.setSpacing(null);
        c.setAlteration(Alteration.NONE);
        c.setOccurrence(null);
        return c;
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

//            case 30: legato = true;

            // a feature to add
//            case 108: alteration = null; break;
//            case 109: alteration = null; break;
//            case 110: alteration = null; break;
//            case 111: alteration = null; break;


            case 24: c.setChordType(ChordType.TRIAD);break;
            case 25: c.setChordType(ChordType.SEVENTH_CHORD);break;
            case 26: c.setChordType(ChordType.NINTH_CHORD);break;

            case 27: c.setInversion(Inversion.ROOT_POSITION);break;
            case 28: c.setInversion(Inversion.FIRST_INVERSION);break;
            case 29: c.setInversion(Inversion.SECOND_INVERSION);break;
            case 30: c.setInversion(Inversion.THIRD_INVERSION);break;

            case 31: c.setMelodicPosition(MelodicPosition.I);break;
            case 32: c.setMelodicPosition(MelodicPosition.III);break;
            case 33: c.setMelodicPosition(MelodicPosition.V);break;
            case 34: c.setMelodicPosition(MelodicPosition.VII);break;
            case 35: c.setMelodicPosition(MelodicPosition.IX);break;

            case 36: c.setSpacing(Spacing.CLOSE);break;
            case 37: c.setSpacing(Spacing.OPEN);break;
            case 38: c.setSpacing(Spacing.MIXED_1);break;
//            case 127: c.setSpacing(Spacing.MIXED_2);break;
        }
        return c;
    }
    private static Chord directlyPitchToVoices(int trackNumber, int pitch, Chord c) {
        // create new Chord in special constructor inside which set the final value.
        switch (trackNumber) {
            case 1 -> c.setFinalSoprano(pitch);
            case 2 -> c.setFinalAlto(pitch);
            case 3 -> c.setFinalTenor(pitch);
            case 4 -> c.setFinalBass(pitch);
            default -> throw new IllegalStateException("Chords.directlyPitchToVoices: Unexpected value of trackNumber: " + trackNumber);

        }
        return c;
    }
    private static ArrayList<Chord> populateFromRepo(Chord c) {
        return chordsRepositoryList
//                        .stream()
                .parallelStream()
                .filter(x -> c.getMelodicPosition() == null || x.getMelodicPosition() == c.getMelodicPosition())
                .filter(x -> c.getChordType() == null || x.getChordType() == c.getChordType())
                .filter(x -> (x.getInversion() != Inversion.SECOND_INVERSION
                        && c.getInversion() == null
                )
                        || (x.getInversion() == c.getInversion()))
                .filter(x -> c.getSpacing() == null || x.getSpacing() == c.getSpacing())
                .filter(x -> c.getAlteration() == null || x.getAlteration() == c.getAlteration())
                .filter(x -> c.getOccurrence() == null || x.getOccurrence() == c.getOccurrence())
                .map(x -> {
                    x = duplicate(x, c);
                    x = applyRootDegreeScale(x);
                    return x;
                })

                .flatMap(Chords::addBassVariant) // todo:  here is the problem!!!!
//                .peek(chord -> System.out.println(chord.getFinalTenor()))
                .collect(Collectors.toCollection(ArrayList::new));
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
    private static Chord duplicate(Chord fromRepo, Chord c) {
        var duplicate =  new Chord(
                fromRepo.getSoprano(),
                fromRepo.getAlto(),
                fromRepo.getTenor(),
                fromRepo.getBass(),
                c.getFinalSoprano(),
                c.getFinalAlto(),
                c.getFinalTenor(),
                c.getFinalBass(),
                c.getTickStartTime(),
                c.getTickEndTime(),
                c.getKeyRoot(),
                c.getChordDegree(),
                c.getKeyScale(),
                fromRepo.getMelodicPosition(),
                fromRepo.getChordType(),
                fromRepo.getInversion(),
                fromRepo.getSpacing(),
                fromRepo.getAlteration(),
                fromRepo.getOccurrence(),
                c.getTessitura(),
                ++id);

        if (fromRepo != c) { // if this is not the same object. For addBassVariant() only.
            fromRepo.setFinalSoprano(0);
            fromRepo.setFinalAlto(0);
            fromRepo.setFinalTenor(0);
            fromRepo.setFinalBass(0);
        }
        return duplicate;

    }
    private static Stream<Chord> addBassVariant(Chord original){
        int t = original.getTenor();
        int b = original.getBass();
        if(t-b < 7){
            Chord variant = duplicate(original, original);
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
    private static String returnToReaper(ArrayList<Chord> finalChords, boolean legato) {
        StringBuilder out = new StringBuilder();
        int sP = 0, aP = 0, tP = 0, bP = 0;
        int sopranoStartTime = finalChords.get(0).getTickStartTime();
        int sopranoEndTime = finalChords.get(0).getTickEndTime();
        int altoStartTime = sopranoStartTime;
        int altoEndTime = sopranoEndTime;
        int tenorStartTime = sopranoStartTime;
        int tenorEndTime = sopranoEndTime;
        int bassStartTime = sopranoStartTime;
        int bassEndTime = sopranoEndTime;
        boolean mustAddS = true;
        boolean mustAddA = true;
        boolean mustAddT = true;
        boolean mustAddB = true;


        try {
            for (int i = 0; i < finalChords.size(); i++) {

                if (legato) {
                    if(i == 0){
                        sP = finalChords.get(i).getSoprano();
                        aP = finalChords.get(i).getAlto();
                        tP = finalChords.get(i).getTenor();
                        bP = finalChords.get(i).getBass();
                        continue;
                    }
                    // soprano block:
                    if (sP == finalChords.get(i).getSoprano()) {
                        // do not add, but save to variables
                        sopranoEndTime = finalChords.get(i).getTickEndTime();
                        mustAddS = true;

                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 1, sP, sopranoStartTime, sopranoEndTime);
                        }
                    } else {
                        if (mustAddS) {
                            // add previous note, if was not added:
                            appendNote(out, 1, sP, sopranoStartTime, sopranoEndTime + 5);
                            mustAddS = false;
                        }
                        // apply note at i:
                        sopranoStartTime = finalChords.get(i).getTickStartTime();
                        sopranoEndTime = finalChords.get(i).getTickEndTime();
                        // save previous to the variable:
                        sP = finalChords.get(i).getSoprano();


                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && sP != finalChords.get(i + 1).getSoprano())) {
                            // append:
                            appendNote(out, 1, sP, sopranoStartTime, sopranoEndTime + 5);
                        }
                    }

                    // alto block:
                    if (aP == finalChords.get(i).getAlto()) {
                        // do not add, but save to variables
                        altoEndTime = finalChords.get(i).getTickEndTime();
                        mustAddA = true;

                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 2, aP, altoStartTime, altoEndTime);
                        }
                    } else {

                        if (mustAddA) {
                            // add previous note, if was not added:
                            appendNote(out, 2, aP, altoStartTime, altoEndTime + 5);
                            mustAddA = false;
                        }
                        // apply note at i:
                        altoStartTime = finalChords.get(i).getTickStartTime();
                        altoEndTime = finalChords.get(i).getTickEndTime();
                        // save previous to the variable:
                        aP = finalChords.get(i).getAlto();
                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && aP != finalChords.get(i + 1).getAlto())) {
                            // append:
                            appendNote(out, 2, aP, altoStartTime, altoEndTime + 5);
                        }
                    }

                    // tenor block:
                    if (tP == finalChords.get(i).getTenor()) {
                        // do not add, but save to variables
                        tenorEndTime = finalChords.get(i).getTickEndTime();
                        mustAddT = true;
                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 3, tP, tenorStartTime, tenorEndTime);
                        }
                    } else {

                        if (mustAddT) {
                            // add previous note, if was not added:
                            appendNote(out, 3, tP, tenorStartTime, tenorEndTime + 5);
                            mustAddT = false;
                        }
                        // apply note at i:
                        tenorStartTime = finalChords.get(i).getTickStartTime();
                        tenorEndTime = finalChords.get(i).getTickEndTime();
                        // save previous to the variable:
                        tP = finalChords.get(i).getTenor();
                        // append:
                        // if last chord                or  next check will not fail              next note is not the same
                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && tP != finalChords.get(i + 1).getTenor())) {
                            // append:
                            appendNote(out, 3, tP, tenorStartTime, tenorEndTime + 5);
                        }
                    }

                    // bass block:
                    if (bP == finalChords.get(i).getBass()) {
                        // do not add, but save to variables
                        bassEndTime = finalChords.get(i).getTickEndTime();
                        mustAddB = true;
                        // if this is the last chord:
                        if (i == finalChords.size() - 1) {
                            // add previous note
                            appendNote(out, 4, bP, bassStartTime, bassEndTime);
                        }
                    } else {
                        if (mustAddB) {
                            // add previous note, if was not added:
                            appendNote(out, 4, bP, bassStartTime, bassEndTime + 5);
                            mustAddB = false;
                        }
                        // apply note at i:
                        bassStartTime = finalChords.get(i).getTickStartTime();
                        bassEndTime = finalChords.get(i).getTickEndTime();
                        // save previous to the variable:
                        bP = finalChords.get(i).getBass();
                        // append:
                        if (i == finalChords.size() - 1 || (finalChords.size() > i + 1 && bP != finalChords.get(i + 1).getBass())) {
                            // append:
                            appendNote(out, 4, bP, bassStartTime, bassEndTime + 5);
                        }
                    }
                }
                else {
                    var start = finalChords.get(i).getTickStartTime();
                    var end = finalChords.get(i).getTickEndTime();
                    appendNote(out, 1, finalChords.get(i).getSoprano(), start, end);
                    appendNote(out, 2, finalChords.get(i).getAlto(), start, end);
                    appendNote(out, 3, finalChords.get(i).getTenor(), start, end);
                    appendNote(out, 4, finalChords.get(i).getBass(), start, end);
                }
            }
//            System.out.println(out);
        } catch (Exception e) {
            throw new RuntimeException("   ! \n! ! ! \n! ! ! ! \n! ! ! !The output of This program is EMPTY ! ! ! \n! ! \n ! !\n ! ! ! ");
        }
        return out.toString();
    }
    private static void appendNote(StringBuilder sb, int track, int note, int start, int end) {
        sb.append(track).append(", ")
                .append(note).append(", ")
                .append(start).append(", ")
                .append(end).append("\n");
    }
}
