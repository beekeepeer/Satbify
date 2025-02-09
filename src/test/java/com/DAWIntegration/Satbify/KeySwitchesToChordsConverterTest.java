package com.DAWIntegration.Satbify;

import com.DAWIntegration.Satbify.Refactor.KeySwitchSorter;
import com.DAWIntegration.Satbify.Refactor.KeySwitchesToChordsConverter;
import com.DAWIntegration.Satbify.module.ChordType;
import com.DAWIntegration.Satbify.module.Note;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class KeySwitchesToChordsConverterTest {

    private List<Note> notes() {
        String requestJson = "{\"customerId\":\"12345\",\"token\":\"a7f5c3b7d98ef43e6a5a7c8b9d3e1f6d\",\"notes\":[{\"track\":\"0\",\"note\":\"25\",\"velocity\":\"96\",\"start\":\"235.289071016113180\",\"end\":\"237.994953369054740\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"235.289071016113180\",\"end\":\"235.965541604348570\"},{\"track\":\"0\",\"note\":\"31\",\"velocity\":\"96\",\"start\":\"235.289071016113180\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"235.965541604348570\",\"end\":\"236.642012192583960\"},{\"track\":\"0\",\"note\":\"29\",\"velocity\":\"96\",\"start\":\"235.965541604348570\",\"end\":\"237.994953369054740\"},{\"track\":\"0\",\"note\":\"21\",\"velocity\":\"96\",\"start\":\"236.642012192583960\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"2\",\"velocity\":\"96\",\"start\":\"236.642012192583960\",\"end\":\"237.318482780819350\"},{\"track\":\"0\",\"note\":\"17\",\"velocity\":\"96\",\"start\":\"237.318482780819350\",\"end\":\"237.994953369054740\"},{\"track\":\"0\",\"note\":\"33\",\"velocity\":\"96\",\"start\":\"237.318482780819350\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"28\",\"velocity\":\"96\",\"start\":\"237.994953369054740\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"237.994953369054740\",\"end\":\"238.671423957290130\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"238.671423957290130\",\"end\":\"239.347894545525550\"},{\"track\":\"0\",\"note\":\"26\",\"velocity\":\"96\",\"start\":\"239.347894545525550\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"239.347894545525550\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"9\",\"velocity\":\"96\",\"start\":\"239.347894545525550\",\"end\":\"240.024365133760940\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"36\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"240.700835721996330\"},{\"track\":\"0\",\"note\":\"27\",\"velocity\":\"96\",\"start\":\"240.024365133760940\",\"end\":\"242.053776898467110\"},{\"track\":\"0\",\"note\":\"14\",\"velocity\":\"96\",\"start\":\"240.700835721996330\",\"end\":\"241.377306310231720\"},{\"track\":\"0\",\"note\":\"19\",\"velocity\":\"96\",\"start\":\"241.377306310231720\",\"end\":\"242.053776898467110\"},{\"track\":\"0\",\"note\":\"25\",\"velocity\":\"96\",\"start\":\"241.377306310231720\",\"end\":\"242.053776898467110\"},{\"track\":\"0\",\"note\":\"38\",\"velocity\":\"96\",\"start\":\"241.377306310231720\",\"end\":\"242.730247486702520\"},{\"track\":\"0\",\"note\":\"12\",\"velocity\":\"96\",\"start\":\"242.053776898467110\",\"end\":\"242.730247486702520\"}]}";
        var sorter = new KeySwitchSorter();
        List<Note> notes = new ArrayList<>();

        String notesJson = requestJson.substring(requestJson.indexOf("\"notes\":[") + 9, requestJson.lastIndexOf("]}"));
        String[] noteStrings = notesJson.split("\\},\\{");

        for (String noteString : noteStrings) {
            noteString = noteString.replace("{", "").replace("}", "");
            String[] parts = noteString.split(",");

            int track = Integer.parseInt(parts[0].split(":")[1].replace("\"", ""));
            int note = Integer.parseInt(parts[1].split(":")[1].replace("\"", ""));
            int velocity = Integer.parseInt(parts[2].split(":")[1].replace("\"", ""));
            double start = Double.parseDouble(parts[3].split(":")[1].replace("\"", ""));
            double end = Double.parseDouble(parts[4].split(":")[1].replace("\"", ""));

            notes.add(new Note(track, note, velocity, start, end));
        }
        return sorter.sortKeySwitches(notes);
    }

    @Test
    public void notesToChords_numberTest() throws Exception {
        var converter = new KeySwitchesToChordsConverter();
        var chords = converter.notesToChords(notes());
                assertNotNull(chords);
        assertEquals(11, chords.size());
    }

        @Test
    public void notesToChords_chordsFields() throws Exception {
        var converter = new KeySwitchesToChordsConverter();
        var chords = converter.notesToChords(notes());
        assertNotNull(chords);
        assertEquals(ChordType.SEVENTH_CHORD, chords.get(0).getChordType());
        assertEquals(ChordType.SEVENTH_CHORD, chords.get(1).getChordType());
        assertEquals(ChordType.SEVENTH_CHORD, chords.get(2).getChordType());
        assertEquals(ChordType.SEVENTH_CHORD, chords.get(3).getChordType());
        assertEquals(ChordType.TRIAD, chords.get(4).getChordType());
        assertEquals(ChordType.TRIAD, chords.get(5).getChordType());
        assertEquals(ChordType.NINTH_CHORD, chords.get(6).getChordType());
        assertEquals(ChordType.NINTH_CHORD, chords.get(7).getChordType());
        // assertEquals(ChordType.TRIAD, chords.get(8).getChordType());
        assertEquals(ChordType.SEVENTH_CHORD, chords.get(9).getChordType());
        assertEquals(ChordType.SEVENTH_CHORD, chords.get(10).getChordType());
    }
}
