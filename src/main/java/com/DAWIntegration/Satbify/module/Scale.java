package com.DAWIntegration.Satbify.module;

import lombok.Getter;

public enum Scale {
    MAJOR_NATURAL(13, new int[] {0, 2, 4, 5, 7, 9, 11, 12}),
    MAJOR_HARMONIC(15, new int[]{0, 2, 4, 5, 7, 8, 11, 12}),
    MINOR_NATURAL(18, new int[] {0, 2, 3, 5, 7, 8, 10, 12}),
    MINOR_HARMONIC(20, new int[]{0, 2, 3, 5, 7, 8, 11, 12}),
    MINOR_MELODIC(22, new int[] {0, 2, 3, 5, 7, 9, 11, 12});

    @Getter
    private final int keySwitch;
    @Getter
    private final int[] intervals;


    Scale(int keySwitch, int[] intervals) {
        this.keySwitch = keySwitch;
        this.intervals = intervals;
    }

}