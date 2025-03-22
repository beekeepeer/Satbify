package com.DAWIntegration.Satbify;

import com.DAWIntegration.Satbify.module.FatChord;
import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.service.NotesToChordsConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotesToChordsConverterTest {
    private static List<Note> notes;
    private NotesToChordsConverter converter;

    private static List<Note> loadNotesFromJson(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(new ClassPathResource(path).getFile());
        JsonNode notesArray = root.get("notes");
        
        List<Note> notes = new ArrayList<>();
        for (JsonNode node : notesArray) {
            Note note = new Note(
                node.get("track").asInt(),
                node.get("note").asInt(),
                node.get("velocity").asInt(),
                node.get("start").asDouble(),
                node.get("end").asDouble(),
                node.get("startBar").asInt(),
                node.get("endBar").asInt(),
                node.get("startBeat").asDouble(),
                node.get("endBeat").asDouble()
            );
            notes.add(note);
        }
        return notes;
    }
    @BeforeAll
    static void loadTestData() throws IOException {
        notes = loadNotesFromJson("data/incomingDataExample.json");
    }

    @BeforeEach
    void setUp() {
        converter = NotesToChordsConverter.getInstance();
    }

    @Test
    void testNotesToChords() {
        List<FatChord> chords = converter.notesToChords(notes);
        
        assertNotNull(chords);
        assertFalse(chords.isEmpty());
        
        // Check that only valid degrees are converted
        for (FatChord chord : chords) {
            System.out.println(chord);
            assertNotNull(chord.getChordDegree());
        }
    }

    
}