package org.netcracker.unc.group16.model;

import org.netcracker.unc.group16.annotations.FieldSettings;
import org.netcracker.unc.group16.exceptions.IllegalTimeSetException;

import java.util.Calendar;


public class Appointment extends Task {
    @FieldSettings(displayName = "Окончание", editable = true, orderNumb = 3)
    private Calendar endTime;

    public Appointment() {

    }

    public Appointment(String title, Calendar time, Calendar endTime, String description) {
        super(title, time, description);
        this.endTime = (Calendar) endTime.clone();
    }

    public Appointment(Integer id, String title, Calendar time, Calendar endTime, String description) {
        super(id, title, time, description);
        this.endTime = (Calendar) endTime.clone();
    }

    public Appointment(Appointment appointment) {
        super(appointment.id, appointment.title, appointment.time, appointment.description);
        this.endTime = (Calendar) appointment.endTime.clone();
    }

    @Override
    public void setTime(Calendar time) throws IllegalArgumentException {
        if (endTime != null && (time.after(endTime) || time.equals(endTime))) {
            throw new IllegalTimeSetException();
        }

        super.setTime(time);
    }


    public Calendar getEndTime() {
        return (Calendar) endTime.clone();
    }

    public void setEndTime(Calendar endTime) throws IllegalArgumentException {
        if (time != null && (endTime.before(time) || endTime.equals(time))) {
            throw new IllegalTimeSetException();
        }

        this.endTime = (Calendar) endTime.clone();
    }

    @Override
    public Object clone() {
        Appointment result = (Appointment) super.clone();
        result.endTime = (Calendar) endTime.clone();

        return result;
    }
}
