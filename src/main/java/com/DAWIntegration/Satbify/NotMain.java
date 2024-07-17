package com.DAWIntegration.Satbify;

import static com.DAWIntegration.Satbify.Chords.harmonise;

public class NotMain {
    public static void notMain(String[] args) {



//        args = new String[]{"""
//        0, 0, 0, 1
//        0, 113, 1440, 14400
//        0, 12, 2880, 3360
//        0, 13, 2880, 3360
//        0, 0, 2880, 5760
//        0, 16, 3360, 3840
//        0, 12, 3840, 4320
//        0, 16, 4320, 4800
//        0, 12, 4800, 5280
//        0, 17, 5280, 5760
//        0, 19, 5760, 6240
//        0, 12, 6240, 6720
//        0, 16, 6720, 7200
//        0, 12, 7200, 7680
//        0, 16, 7680, 8160
//        0, 12, 8160, 8640
//        0, 17, 8640, 9120
//        0, 19, 9120, 9600
//        0, 12, 9600, 11520"""};




        if (args.length != 0 && args[0].length() > 11){
            String str = args[0];
            harmonise(str);
            }
        else
            throw new ArrayIndexOutOfBoundsException("\n\n---Reaper did not send any note---\n");
    }
}
