package org.netcracker.unc.group16.model;

import java.util.Calendar;


public class Appointment extends Task {
    private Calendar endTime;

    public Appointment(Integer id, String title, Calendar time, Calendar endTime, String description) {
        super(id, title, time, description);
        this.endTime = endTime;
    }

    public Calendar getEndTime() {
        return (Calendar) endTime.clone();
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
