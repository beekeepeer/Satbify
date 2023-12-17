package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.midi.*;

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
                // print event position in time:
//                System.out.println(event.getTick());
                // print the resolution of the sequence:
//                System.out.println(sequence.getResolution()); // 960
                // print note offsets



            }
        } catch (FileNotFoundException | InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
