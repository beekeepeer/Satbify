package com.DAWIntegration.Satbify.module;

public record Note(
        int reaperTrack,
        int pitch,
        int velocity,
        double startTime,
        double endTime,
        int startBar,
        int endBar,
        double startBeat,
        double endBeat) {
}

