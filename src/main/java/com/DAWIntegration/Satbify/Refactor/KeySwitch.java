package com.DAWIntegration.Satbify.Refactor;

import com.DAWIntegration.Satbify.module.Note;

public interface KeySwitch {
    public void setKyeSwitchNote(Note note);
    public double getStartTime();
    public double getEndTime();
    public boolean isActive(double time);
    

}
