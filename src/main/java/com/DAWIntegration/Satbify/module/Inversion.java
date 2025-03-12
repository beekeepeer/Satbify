package com.DAWIntegration.Satbify.module;

import lombok.Getter;

public enum Inversion {
    ROOT_POSITION(27),
    FIRST_INVERSION(28),
    SECOND_INVERSION(29),
    THIRD_INVERSION(30);


    @Getter
    private final int keySwitch;

    Inversion(int keySwitch){
        this.keySwitch = keySwitch;
    }
}