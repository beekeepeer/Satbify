package org.example;


import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.spi.MidiFileWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ServiceLoader;

public class SingleNoteMIDI {

    public static void main(String[] args) {
        File file = new File("myMidiFile.mid");
        try (FileOutputStream fos = new FileOutputStream(file);){
            Sequence sequence = new Sequence(Sequence.PPQ, 480, 1);
            Track[] tracks = sequence.getTracks();
            Track track  = tracks[0];

            // Create a note-on event for the MIDI note C1
            ShortMessage noteOn = new ShortMessage();
            noteOn.setMessage(ShortMessage.NOTE_ON, 0, 60, 100);
            track.add(new MidiEvent(noteOn, 603));

            // Create a note-off event for the MIDI note C1
            ShortMessage noteOff = new ShortMessage();
            noteOff.setMessage(ShortMessage.NOTE_OFF, 0, 60, 127);
            track.add(new MidiEvent(noteOff, 703));



            // save a MIDI file
            var serviceLoader = ServiceLoader.load(MidiFileWriter.class).findFirst().get();
            serviceLoader.write(sequence, 1, file);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

