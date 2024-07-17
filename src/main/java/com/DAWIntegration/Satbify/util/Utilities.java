package com.DAWIntegration.Satbify.util;

public class Utilities {
    public static void main(String[] args) {


        int n = 0;

//        while (n < 128) {
//            for (int i = 1; i < 8; i++) {
//                System.out.println("case " + (n + Scale.MAJOR_NATURAL.getIntervals()[i]) + ": return ");
//            }
//            n += 12;
//        }

//        while (n < 128) {
//            System.out.println("case " + n + " result =  ");
//            n += 12;
//        }

//        while (n < 128) {
//            System.out.println(n);
//            n += 12;
//        }
        while (n < 128) {
            if (n % 12 == 9)
                System.out.println(n);
            n++;
        }

    }
}
