package org.netcracker.unc.group16.model;

import org.netcracker.unc.group16.annotations.Displayed;
import org.netcracker.unc.group16.annotations.FieldSettings;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;


public class Task {


    private int id;


    @Displayed
    @FieldSettings(displayName = "Название", editable = true, orderNumb = 1)
    private String title;


    @Displayed
    @FieldSettings(displayName = "Дата", editable = true, orderNumb = 2)
    private Calendar time;


    @Displayed
    @FieldSettings(displayName = "Описание", editable = true, orderNumb = 3)
    private String description;


    private String comment;

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


    public void setId(int id) {
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
        return time;
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


    public String getComment(){
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
