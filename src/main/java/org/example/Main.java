package org.example;

import org.example.module.Chord;
import static org.example.mainClasses.Chords.*;

import javax.sound.midi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // create a File object from directory
        var file = new File("/Users/homestudio/Music/Chord Track Reaper project/Audio/1_ScaleTonDegree.mid");
        int noteNumber;
        List<Chord> listOfChords = new ArrayList<>();
        listOfChords.add(new Chord());
        int lastChord = 0;
   

        try (var fis = new FileInputStream(file);) {
            Sequence sequence = MidiSystem.getSequence(fis);
            Track[] tracks = sequence.getTracks();
            Track track = tracks[0];


            for (int i = 0; i < track.size(); i++) {


                MidiEvent event = track.get(i);
                int status = event.getMessage().getStatus();  // note on = 144 / off = 128/ CC/ all note off 176, 255


                MidiMessage midiMessage = event.getMessage();
                byte[] bytes = midiMessage.getMessage(); // System.out.println(Arrays.toString(bytes));
                byte b = bytes[1]; // second byte value of i'th midi message = note number.
                int channel = 1 + (midiMessage.getStatus() & 0x0F);
                boolean isDegreeKeySwitch = b == 12 || b == 14 || b == 16 || b == 17 || b == 19 || b == 21 || b == 23;
                boolean isGivenNoteInVoice =  channel < 5; // TODO: add note range from Soprano to Bass. 
                lastChord = listOfChords.size() - 1; // why element of list, why not the object? I do not know... it works.


                if (status == 144) {      // if this is a note On channel 1 message. For all key
                    listOfChords.get(lastChord).setTickStartTime(event.getTick());
                    listOfChords.get(lastChord).applyKeySwitch(b);
                } else if (status == 128 && isDegreeKeySwitch) {      // if this is a note Off message of a Chord degree KeySwitch
                    listOfChords.get(lastChord).setTickEndTime(event.getTick()); // TODO: end time only for the last chord in the sequence

                    //add new Chord to the list:
                    Chord newChord = listOfChords.get(lastChord).clone();
                    listOfChords.add(newChord);
                }






                // TODO: apply provided explicit midi notes to Notes fields of Chord objects:
                if(channel<=4){
                    // if note is out of scale - build logic for changing the alteration field in current Chord object
                }















            }
            listOfChords.remove(lastChord); // TODO: too stupidly delete last added Chord form list at the end, without condition
            
            // System.out.println(listOfChords);


            connectChords(listOfChords).forEach(System.out::println);
            // Chords.toAbsoluteChord(listOfChords.get(0));

        } catch (InvalidMidiDataException | IOException e) {
            throw new RuntimeException(e);
        } catch (ArithmeticException eAr){
            System.out.println(" Arithmetic Exception ");
        }
        
        // System.out.println("listOfChords : " + listOfChords);









        //  TODO: Connect chords in the listOfChords - apply KeySwitches to Notes fields:



















        // TODO: export properly connected list of Chords to midi file in 4 midi channels

    }
}


// print the resolution of the sequence:
//                System.out.println(sequence.getResolution()); // 960
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
      
