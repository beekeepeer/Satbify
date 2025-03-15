package com.DAWIntegration.Satbify.Refactor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.DAWIntegration.Satbify.module.Degree;
import com.DAWIntegration.Satbify.module.Inversion;
import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.module.Scale;

public class ChordVersionsFilter {

    public static ChordVersionsFilter getInstance() {
        return new ChordVersionsFilter();
    }

    public List<List<FatChord>> filterChordVersions(List<Note> allKS, List<FatChord> preChords) {

        return preChords.parallelStream().map(c -> populateFromRepo(c)).toList();

    }

    
    private static List<FatChord> populateFromRepo(FatChord c) {
        List<FatChord> chordsRepositoryList = FatChordRepository.INSTANCE.getChordsRepository();
        return chordsRepositoryList
                       .stream()
                // .parallelStream()
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
                    x = c.clone();
                    x = applyRootDegreeScale(x);
                    return x;
                })
                .flatMap(x -> addBassVariant(x))
//                .peek(chord -> System.out.println(chord.getFinalTenor()))
                .collect(Collectors.toCollection(ArrayList::new));
    }




    private static FatChord applyRootDegreeScale(FatChord x) {
        //todo: optimize for C Major
        int s = raiseToDegree(x.getSoprano(), x.getChordDegree());
        s = applyScale(x.getKeyScale(), s);
        s += x.getKeyRoot().getKeyNumber();

        int a = raiseToDegree(x.getAlto(), x.getChordDegree());
        a = applyScale(x.getKeyScale(), a);
        a += x.getKeyRoot().getKeyNumber();

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
        return notes.get(ordinal + a);
    }


    
    private static int applyScale(Scale scale, int n) {
        switch (scale){ // I'm proud of this humble solution:
            case MAJOR_HARMONIC: if (n % 12 == 9) n--; break;
            case MINOR_NATURAL : if (n % 12 == 9 || n % 12 == 4 || n % 12 == 11) n--; break;
            case MINOR_HARMONIC: if (n % 12 == 9 || n % 12 == 4) n--; break;
            case MINOR_MELODIC : if (n % 12 == 4) n--; break;

        }
        return  n;
    }


    private static Stream<FatChord> addBassVariant(FatChord original){
        int t = original.getTenor();
        int b = original.getBass();
        if(t-b < 7){
            FatChord variant = original.clone();
            variant.setBass(original.getBass() - 12);
            return Stream.of(original, variant);
        } else return Stream.of(original);
    }
}
