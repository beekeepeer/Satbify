package com.DAWIntegration.Satbify.module;
// todo: in this class add logic for choosing if is latching or not.
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class KeySwitchContext {
    private List<Note> kws = new ArrayList<>();

    public void addKeySwitch(Note note) {
        kws.add(note);
    }
    // i is the index number of the chord. I should not use timing because of not perfect quantization.
    public void removeExpiredKeySwitches(int i) {
        kws.removeIf(note -> note.end() < i);
    }

    public List<Note> getKws() {
        return kws;
    }

    // Optional: Find specific types of key switches, like scales or harmonic functions
    public Optional<Note> findKeySwitchByType(int type) {
        return kws.stream().filter(ks -> ks.pitch() == type).findFirst();
        // Maybe latching kw should be handled differently.
    }
}