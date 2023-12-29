package org.example.module.enums;

// @todo: Set int values from REAPER keySwitches
public enum Alteration {
    RAISED_I(0),  // probably should be deleted
    RAISED_II(0),
    RAISED_III(0),
    RAISED_IV(0), // Группа аккордов двойной доминанты
    RAISED_V(0),
    RAISED_VI(0),
    RAISED_VII(0),   // probably should be deleted потому что и в мажоре и в минору это норма

    LOWERED_I(0),
    LOWERED_II(0),
    LOWERED_III(0),
    LOWERED_IV(0),
    LOWERED_V(0),
    LOWERED_VI(0),
    LOWERED_VII(0);

    private final int value;

    Alteration(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
