package org.example.module;

public enum Scale {
    MAJOR_NATURAL(13, new int[] {0,0,0,0,0,0,0}),
    MAJOR_HARMONIC(15, new int[] {0,0,0,0,0,-1,0}),
    MINOR_NATURAL(18, new int[] {0,0,-1,0,0,-1,-1}),
    MINOR_HARMONIC(20, new int[] {0,0,-1,0,0,-1,0}),
    MINOR_MELODIC(22, new int[] {0,0,-1,0,0,0,0});

    private final int keySwitch;
    private final int[] steps;
    

    Scale(int keySwitch, int[] steps) {
        this.keySwitch = keySwitch;
        this.steps = steps;
    }

    public int[] getSteps(){
        return steps;
    }

    public int getKeySwitch() {
        return keySwitch;
    }
}
