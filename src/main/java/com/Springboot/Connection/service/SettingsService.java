package com.Springboot.Connection.service;

import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private long autoCancelMinutes = 2; //Default value Minutes
    private int autoDeleteMonths = 3; //Default Value Months

    // Get the current auto-cancel time
    public long getAutoCancelMinutes() {
        return autoCancelMinutes;
    }

    // Update the auto-cancel time
    public void setAutoCancelMinutes(long minutes) {
        this.autoCancelMinutes = minutes;
    }

    public void setAutoDeleteMonths(int months) {
        this.autoDeleteMonths = months;
    }

    public int getAutoDeleteMonths() {
        return autoDeleteMonths;
    }
}
