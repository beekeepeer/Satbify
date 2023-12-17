package org.example.mainClasses;

public record Note(
        int pitch,
        int midiChannel,
        int velocity,
        int duration,
        long timePosition
) {

}
