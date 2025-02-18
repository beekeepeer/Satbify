package com.DAWIntegration.Satbify;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.DAWIntegration.Satbify.Refactor.FatChord;
import com.DAWIntegration.Satbify.Refactor.MusicalTiming;
import com.DAWIntegration.Satbify.module.Alteration;
import com.DAWIntegration.Satbify.module.ChordType;
import com.DAWIntegration.Satbify.module.Degree;
import com.DAWIntegration.Satbify.module.Key;
import com.DAWIntegration.Satbify.module.Scale;

public class FatChordCloneTest {

    @Test
    void testClone_distinctObjects() {
        // Arrange
        MusicalTiming mt = new MusicalTiming(1, 2); // Example MusicalTiming
        FatChord originalChord = FatChord.getNewInstance();

        FatChord clonedChord = originalChord.clone();

        assertNotSame(originalChord, clonedChord);
    }



    // Test for null startMusical
    @Test
    void testClone_nullStartMusical() {
        FatChord originalChord = FatChord.getNewInstance();
        FatChord clonedChord = originalChord.clone();
    }

    // Test for null endMusical
    @Test
    void testClone_nullEndMusical() {
        FatChord originalChord = FatChord.getNewInstance();
        FatChord clonedChord = originalChord.clone();
    }


}