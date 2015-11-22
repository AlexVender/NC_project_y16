package org.netcracker.unc.group16.model;

import java.util.Calendar;

/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public class Appointment extends Task {
    private Calendar duration;

    public Calendar getDuration() {
        return duration;
    }

    public void setDuration(Calendar duration) {
        this.duration = duration;
    }
}
