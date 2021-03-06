package org.netcracker.unc.group16.model;

import org.netcracker.unc.group16.annotations.FieldSettings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;
import java.util.Objects;


//@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Appointment.class)
public class Task implements Cloneable {
    @FieldSettings(displayName = "ID", editable = false, orderNumb = 0)
    protected Integer id;

    @FieldSettings(displayName = "Тема", editable = true, orderNumb = 1)
    protected String title;

    @FieldSettings(displayName = "Дата", editable = true, orderNumb = 2)
    protected Calendar time;

    @FieldSettings(displayName = "Описание", editable = true, orderNumb = 4)
    protected String description;

    public  Task() {

    }

    public Task(String title, Calendar time, String description) {
        this(0, title, time, description);
    }

    public Task(int id, String title, Calendar time, String description) {
        this.id = id;
        this.title = title;
        this.time = (Calendar) time.clone();
        this.time.set(Calendar.SECOND, 0);
        this.time.set(Calendar.MILLISECOND, 0);
        this.description = description;
    }

    public Task(Task task ) {
        this(task.id, task.title, task.time, task.description);
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @XmlJavaTypeAdapter(value = CalendarAdapter.class)
    public Calendar getTime() {
        return (Calendar)time.clone();
    }

    public void setTime(Calendar time) {
        if (time == null) {
            this.time = null;
            return;
        }

        this.time = (Calendar) time.clone();
        this.time.set(Calendar.SECOND, 0);
        this.time.set(Calendar.MILLISECOND, 0);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Object clone() {
        try {
            Task result = (Task)super.clone();
            result.id = id;
            result.title = title;
            result.time = (time != null) ? (Calendar) time.clone() : null;
            result.description = description;
            return result;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(time, task.time) &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, time, description);
    }
}
