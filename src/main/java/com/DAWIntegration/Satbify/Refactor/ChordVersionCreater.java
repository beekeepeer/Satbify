package com.DAWIntegration.Satbify.Refactor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.DAWIntegration.Satbify.module.Degree;
import com.DAWIntegration.Satbify.module.Inversion;
import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.module.Occurrence;
import com.DAWIntegration.Satbify.module.Scale;

public class ChordVersionCreater {

    public static ChordVersionCreater getInstance() {
        return new ChordVersionCreater();
    }

    public List<List<FatChord>> filterChordVersions(List<Note> allKS, List<FatChord> preChords) {

        return preChords.parallelStream().map(c -> populateFromRepo(c)).toList();

    }

    
    private List<FatChord> populateFromRepo(FatChord c) {
        return FatChordRepository.INSTANCE.getChordsRepository().stream()
                .filter(x -> isSuitableVersion(x, c))
                .map(x -> setFinalNots(
                        adjustOctaves(
                                applyRootDegreeScale(
                                        duplicate(x, c)))))
                .filter(x -> x.getOccurrence() != Occurrence.UNUSABLE)
                .flatMap(x -> addBassVariant(x))
                .collect(Collectors.toList());
    }

    private boolean isSuitableVersion(FatChord x, FatChord c) {
        return ((c.getMelodicPosition() == null
                        || x.getMelodicPosition() == c.getMelodicPosition())
                        && (c.getChordType() == null
                                || x.getChordType() == c.getChordType())
                        && ((x.getInversion() != Inversion.SECOND_INVERSION && c.getInversion() == null)
                                || (x.getInversion() == c.getInversion()))
                        && (c.getSpacing() == null
                                || x.getSpacing() == c.getSpacing()));
    }

    private FatChord duplicate(FatChord fromRepo, FatChord original) {
        var duplicate = fromRepo.clone();
        duplicate.setFinalSoprano(original.getFinalSoprano());
        duplicate.setFinalAlto(original.getFinalAlto());
        duplicate.setFinalTenor(original.getFinalTenor());
        duplicate.setFinalBass(original.getFinalBass());
        duplicate.setStartTime(original.getStartTime());
        duplicate.setEndTime(original.getEndTime());
        duplicate.setStartBar(original.getStartBar());
        duplicate.setEndBar(original.getEndBar());
        duplicate.setStartBeat(original.getStartBeat());
        duplicate.setEndBeat(original.getEndBeat());
        duplicate.setKeyRoot(original.getKeyRoot());
        duplicate.setChordDegree(original.getChordDegree());
        duplicate.setKeyScale(original.getKeyScale());
        duplicate.setRegister(original.getRegister());
        duplicate.setLegato(original.isLegato());
        duplicate.setPhraseNumber(original.getPhraseNumber());
        duplicate.setPeriodNumber(original.getPeriodNumber());

        return duplicate;

    }

    private FatChord applyRootDegreeScale(FatChord x) {
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


    private int raiseToDegree(int noteNumber, Degree degree) {
        var ordinal = degree.ordinal();
        // todo: make a notes list for pentatonic and hexatonic. Add a "Scale" method argument
        List<Integer> notes = List.of(0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57, 59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101, 103, 105, 107, 108, 110, 112, 113, 115, 117, 119, 120, 122, 124, 125);
        var a = notes.indexOf(noteNumber);
        return notes.get(ordinal + a);
    }


    
    private int applyScale(Scale scale, int n) {
        switch (scale){ // I'm proud of this humble solution:
            case MAJOR_HARMONIC: if (n % 12 == 9) n--; break;
            case MINOR_NATURAL : if (n % 12 == 9 || n % 12 == 4 || n % 12 == 11) n--; break;
            case MINOR_HARMONIC: if (n % 12 == 9 || n % 12 == 4) n--; break;
            case MINOR_MELODIC : if (n % 12 == 4) n--; break;

        }
        return  n;
    }


    private FatChord adjustOctaves(FatChord chord) {
        double highestSoprano = 84, lowestBass = 35;
        double tessitura = chord.getRegister();
        double averagePitch = ((double) chord.getSoprano()
                + chord.getAlto()
                + chord.getTenor())
                / 3;
        if (averagePitch == tessitura || Math.abs(averagePitch - tessitura) < 7.0) {
            return chord;
        }
        return (averagePitch < tessitura) 
                ? adjustOctaves(shiftOctave(chord, 12))
                : adjustOctaves(shiftOctave(chord, -12));

    }


    private FatChord setFinalNots(FatChord chord) {
        int difCommon = getDifCommon(chord);
        if (difCommon != 0) { // if this chord should harmonize Specified Notes
            if (difCommon % 12 == 0) { // if differences can be used to shift by octave, AND are equal.
                chord.setSoprano(chord.getSoprano() + difCommon);
                chord.setAlto(chord.getAlto() + difCommon);
                chord.setTenor(chord.getTenor() + difCommon);
                chord.setBass(chord.getBass() + difCommon);
                return chord;
            } else { // differences are no octaves - this is the wrong Chord!!!
                chord.setOccurrence(Occurrence.UNUSABLE);
                return chord;
            }
        } else { // This Chord should not be touched!!! Return the original.
            return chord;
        }
    }


    private int getDifCommon(FatChord chord) {
        var difS = 0;
        var difA = 0;
        var difT = 0;
        var difB = 0;
        if (chord.getFinalSoprano() != 0) {
            difS = chord.getFinalSoprano() - chord.getSoprano();
        }
        if (chord.getFinalAlto() != 0) {
            difA = chord.getFinalAlto() - chord.getAlto();
        }
        if(chord.getFinalTenor() != 0){
            difT = chord.getFinalTenor() - chord.getTenor();
            }
        if(chord.getFinalBass() != 0) {
            difB = chord.getFinalBass() - chord.getBass();
        }
        return suitableChord(difS, difA, difT, difB);
    }

    public int suitableChord(int difS, int difA, int difT, int difB) {
        // Count non-zero values
        int[] values = {difS, difA, difT, difB};
        int nonZeroCount = 0;
        int nonZeroValue = 0;
        boolean allEqual = true;

        for (int value : values) {
            if (value != 0) {
                nonZeroCount++;
                if (nonZeroValue == 0) {
                    nonZeroValue = value;
                } else if (value != nonZeroValue) {
                    allEqual = false;
                }
            }
        }
        // not clever, but clear:
        if (nonZeroCount == 0) {
            return 0; // All are zero
        } else if (nonZeroCount == 1) {
            return nonZeroValue; // Only one is non-zero
        } else if (allEqual) {
            return nonZeroValue; // All non-zero values are equal
        } else {
            return 13; // This chord doesn't fit
        }
    }

    private FatChord shiftOctave(FatChord a, int shift) {
        a.setSoprano(a.getSoprano() + shift);
        a.setAlto(a.getAlto() + shift);
        a.setTenor(a.getTenor() + shift);
        a.setBass(a.getBass() + shift);
        return a;
    }

    private static Stream<FatChord> addBassVariant(FatChord original) {
        int t = original.getTenor();
        int b = original.getBass();
        if(t-b < 7){
            FatChord variant = original.clone();
            variant.setBass(original.getBass() - 12);
            return Stream.of(original, variant);
        } else return Stream.of(original);
    }
}
