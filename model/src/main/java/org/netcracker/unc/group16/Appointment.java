package org.netcracker.unc.group16;

import java.util.Date;

/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public class Appointment extends Task {
    private Date duration;

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
