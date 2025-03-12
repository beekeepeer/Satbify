package com.DAWIntegration.Satbify.module;

import lombok.Getter;

public enum Spacing {
    CLOSE(36),
    OPEN(37),
    MIXED_1(38),
    MIXED_2(127);

    @Getter
    public final int KeySwith;
    Spacing(int keySwith) {
        this.KeySwith = keySwith;
    }
}
