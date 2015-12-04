package org.netcracker.unc.group16.model;

import org.netcracker.unc.group16.annotations.NotDisplayed;
import org.netcracker.unc.group16.annotations.FieldSettings;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;


public class Task implements Cloneable {
    @FieldSettings(displayName = "ID", editable = true, orderNumb = 0)
    protected Integer id;

    @FieldSettings(displayName = "Название", editable = true, orderNumb = 1)
    protected String title;

    @FieldSettings(displayName = "Дата", editable = true, orderNumb = 2)
    protected Calendar time;

    @FieldSettings(displayName = "Описание", editable = true, orderNumb = 4)
    protected String description;

    public  Task() {

    }

    public Task(int id, String title, Calendar time, String description) {
        this.id = id;
        this.title = title;
        this.time = (Calendar) time.clone();
        this.description = description;
    }

    public Task(Task task ) {
        this.id = task.id;
        this.title = task.title;
        this.time = (Calendar) task.time.clone();
        this.description = task.description;
    }

    
    public int getId() {
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
        this.time = (Calendar) time.clone();
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


}
