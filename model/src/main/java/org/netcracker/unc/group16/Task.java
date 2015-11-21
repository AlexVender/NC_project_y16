package org.netcracker.unc.group16;

import java.util.Calendar;
import java.util.Date;



/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public class Task {
    private int id;
    private String title;
    private Calendar time;
    private String comment;

    public  Task(){

    }
    public Task(int id, String title, Calendar time, String comment) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.comment = comment;
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

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
