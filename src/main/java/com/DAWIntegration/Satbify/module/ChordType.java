package com.DAWIntegration.Satbify.module;

import lombok.Getter;

public enum ChordType {
    TRIAD(24), SEVENTH_CHORD(25), NINTH_CHORD(26) ;

    @Getter
    private final int keySwitch;

    ChordType(int keySwitch) {
        this.keySwitch = keySwitch;
    }
}
