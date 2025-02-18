package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Note;
import com.DAWIntegration.Satbify.module.RequestDeserialized;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonComponent
public class ReaperDeserializer extends JsonDeserializer<RequestDeserialized> {

    private RequestDeserialized requestDeserialized;

    @Override
    public RequestDeserialized deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser); // Read the entire JSON tree
        int customerId = node.get("customerId").asInt();
        String token = node.get("token").asText();

        JsonNode notesNode = node.get("notes");
        List<Note> notes = new ArrayList<>();

        notesNode.forEach(noteNode -> {
            int reaperTrack = noteNode.get("track").asInt();
            int pitch = noteNode.get("note").asInt();
            int velocity = noteNode.get("velocity").asInt();
            double start = noteNode.get("start").asDouble();
            double end = noteNode.get("end").asDouble();

            notes.add(new Note(reaperTrack, pitch, velocity, start, end, 0, 0, 0, 0));
        });

        this.requestDeserialized = new RequestDeserialized(customerId, token, notes); // Store the deserialized object
        return this.requestDeserialized;
    }

    public int getCustomerId() {
        return this.requestDeserialized.customerId();
    }

    public String getToken() {
        return this.requestDeserialized.token();
    }

    public List<Note> getNotes() {
        return this.requestDeserialized.notes();
    }
}
