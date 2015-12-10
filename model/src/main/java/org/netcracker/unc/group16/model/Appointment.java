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
        this(0, title, time, endTime, description);
    }

    public Appointment(Integer id, String title, Calendar time, Calendar endTime, String description) {
        super(id, title, time, description);
        setEndTime(endTime);
    }

    public Appointment(Appointment appointment) {
        this(appointment.id, appointment.title, appointment.time, appointment.endTime, appointment.description);
    }

    @Override
    public void setTime(Calendar time) throws IllegalArgumentException {
        if (time == null) {
            this.time = null;
            return;
        }

        Calendar tmpCalendar = (Calendar) time.clone();
        tmpCalendar.set(Calendar.SECOND, 0);
        tmpCalendar.set(Calendar.MILLISECOND, 0);

        if (endTime != null && (time.after(endTime) || time.equals(endTime))) {
            throw new IllegalTimeSetException();
        }

        this.time = tmpCalendar;
    }


    public Calendar getEndTime() {
        return (Calendar) endTime.clone();
    }

    public void setEndTime(Calendar endTime) throws IllegalArgumentException {
        if (endTime == null) {
            this.endTime = null;
            return;
        }
        Calendar tmpCalendar = (Calendar) endTime.clone();
        tmpCalendar.set(Calendar.SECOND, 0);
        tmpCalendar.set(Calendar.MILLISECOND, 0);

        if (time != null && (tmpCalendar.before(time) || tmpCalendar.equals(time))) {
            throw new IllegalTimeSetException();
        }

        this.endTime = tmpCalendar;
    }

    @Override
    public Object clone() {
        Appointment result = (Appointment) super.clone();
        result.endTime = (Calendar) endTime.clone();

        return result;
    }
}
