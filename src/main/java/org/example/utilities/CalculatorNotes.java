package org.example.utilities;

import org.example.module.NoteOfScale;

public class CalculatorNotes {
//    public static void main(String[] args) {
//
//        int current_number = 32;
//        System.out.print(current_number + ", ");
//        while (current_number <= 108) {
//            current_number += 12;
//            System.out.print(current_number + ", ");
//        }
//    }


//    calculates note of the scale
    public static void main(String[] args) {

        int[] voices = {60, 64, 67, 72};

        for (int i = 3; i >= 0; i--) {

            if (voices[i] == 24 ||
                    voices[i] == 36 ||
                    voices[i] == 48 ||
                    voices[i] == 60 ||
                    voices[i] == 72 ||
                    voices[i] == 84 ||
                    voices[i] == 96) {
                System.out.print("I ");
            } else if (voices[i] == 28 ||
                    voices[i] == 40 ||
                    voices[i] == 52 ||
                    voices[i] == 64 ||
                    voices[i] == 76 ||
                    voices[i] == 88 ||
                    voices[i] == 100) {
                System.out.print("III ");
            } else if (voices[i] == 31 ||
                    voices[i] == 43 ||
                    voices[i] == 55 ||
                    voices[i] == 67 ||
                    voices[i] == 79 ||
                    voices[i] == 91 ||
                    voices[i] == 103) {
                System.out.print("V ");
            } else if (voices[i] == 24 ||
                    voices[i] == 36 ||
                    voices[i] == 48 ||
                    voices[i] == 60 ||
                    voices[i] == 72 ||
                    voices[i] == 84 ||
                    voices[i] == 96) {
                System.out.print("VII ");
            } else if (voices[i] == 35||
                    voices[i] == 47 ||
                    voices[i] == 59 ||
                    voices[i] == 71 ||
                    voices[i] == 83 ||
                    voices[i] == 95 ||
                    voices[i] == 107) {
                System.out.print("IX ");
            }

            // if for lowest octave
            if (voices[i] > 23 && voices[i]<36){
                System.out.println(1);
            } else if (voices[i] > 35 && voices[i]<48) {
                System.out.println(2);
            } else if (voices[i] > 47 && voices[i]<60) {
                System.out.println(3);
            } else if (voices[i] > 59 && voices[i]<72) {
                System.out.println(4);
            } else if (voices[i] > 71 && voices[i]<84){
                System.out.println(5);
            } else if (voices[i] > 83 && voices[i]<96){
                System.out.println(6);
            } else if (voices[i] > 95 && voices[i]<108){
                System.out.println(7);
            }


        }
    }





}