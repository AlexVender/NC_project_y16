package org.netcracker.unc.group16.model;

import org.netcracker.unc.group16.annotations.Displayed;
import org.netcracker.unc.group16.annotations.FieldSettings;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;


public class Task {
    private Integer id;


    @Displayed
    @FieldSettings(displayName = "Название", editable = true, orderNumb = 1)
    private String title;


    @Displayed
    @FieldSettings(displayName = "Дата", editable = true, orderNumb = 2)
    private Calendar time;


    @Displayed
    @FieldSettings(displayName = "Описание", editable = true, orderNumb = 3)
    private String description;

    public  Task() {

    }

    public Task(int id, String title, Calendar time, String description) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.description = description;
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
        this.time = time;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
