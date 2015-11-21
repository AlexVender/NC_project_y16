package org.netcracker.unc.group16;

import java.util.Calendar;
import java.util.Date;

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
