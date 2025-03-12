package com.DAWIntegration.Satbify.module;

import lombok.Getter;

public enum MelodicPosition {
    I(112), III(113), V(114), VII(115), IX(116);

    @Getter
    private final int keySwitch;
    MelodicPosition(int keyswitch) {
        this.keySwitch = keyswitch;
    }
}
