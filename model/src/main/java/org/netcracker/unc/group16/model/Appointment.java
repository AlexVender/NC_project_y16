package org.netcracker.unc.group16.model;

import org.netcracker.unc.group16.annotations.FieldSettings;
import org.netcracker.unc.group16.exceptions.IllegalTimeSetException;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;
import java.util.Objects;


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
    public Appointment(Task task){
        super(task.id, task.title, task.time, task.description);
        this.endTime = task.time;
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


    @XmlJavaTypeAdapter(value = CalendarAdapter.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), endTime);
    }
}
