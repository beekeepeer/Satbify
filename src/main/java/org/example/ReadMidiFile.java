package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.midi.*;

// todo: covert int from keyswitch to Degree enam
public class ReadMidiFile {
    public static void main(String[] args) {

        // create a File object from directory
        var file =  new File("/Users/homestudio/Music/Chord Track Reaper project/Audio/1_ScaleTonDegree.mid");

        try (var fis = new FileInputStream(file);){
            Sequence sequence = MidiSystem.getSequence(fis);
            Track[] tracks = sequence.getTracks();
            Track track = tracks[0];
            for (int i = 0; i < track.size(); i++) {

                MidiEvent event = track.get(i);
                var x = event.getMessage().getMessage()[0];
                if(x == -112)
                    System.out.println("Byte " + x + " - " + event.getTick() / 960); // print through all events
//                System.out.println("Status " + event.getMessage().getStatus());

//                System.out.println(event.getTick()); // print event position in time:
                // print the resolution of the sequence:
//                System.out.println(sequence.getResolution()); // 960
                // print note offsets
                byte[] bytes = event.getMessage().getMessage();



            }
        } catch (FileNotFoundException | InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


/*

The MIDI channel of an event in javax.sound.midi can be defined by retrieving the status byte of the MIDI message and extracting the channel number. The status byte is the first byte of the MIDI message and is used to identify the type of message and the channel to which it belongs.

To retrieve the status byte, you can use the getStatus() method of the MidiMessage class. For example, the following code snippet retrieves the status byte of a MIDI event:

Java
MidiMessage message = event.getMessage();
int statusByte = message.getStatus();
Use code with caution. Learn more
Once you have the status byte, you can extract the channel number by masking the byte with the value 127. The channel number is represented by the bits 0-5 of the status byte. For example, the following code snippet extracts the channel number from a status byte:

Java
int channelNumber = statusByte & 0x7F;
Use code with caution. Learn more
Here is an example of how to define the MIDI channel of an event:

Java
ShortMessage noteOn = new ShortMessage();
noteOn.setMessage(ShortMessage.NOTE_ON, channelNumber, noteNumber, velocity);
Use code with caution. Learn more
In this example, the noteOn message is created with the specified channel number. The channel number is passed as an argument to the setMessage() method.
 */