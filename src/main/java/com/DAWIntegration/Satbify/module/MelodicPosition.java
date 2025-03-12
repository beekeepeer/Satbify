package com.DAWIntegration.Satbify.module;

import lombok.Getter;

public enum MelodicPosition {
    I(31), III(32), V(33), VII(34), IX(35);

    @Getter
    private final int keySwitch;
    MelodicPosition(int keyswitch) {
        this.keySwitch = keyswitch;
    }
}
