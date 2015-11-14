package org.netcracker.unc.group16;

import java.util.Date;



/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public class Task {
    private int id;
    private String title;
    private Date time;
    private String comment;


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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
