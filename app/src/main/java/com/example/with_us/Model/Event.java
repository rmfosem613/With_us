package com.example.with_us.Model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private String postid;
    private String eventimage;
    private String description;
    private String publisher;
    private String eventtitle;
    private String eventdate;

    public Event(String postid, String eventimage, String description, String publisher, String eventtitle, String eventdate) {
        this.postid = postid;
        this.eventimage = eventimage;
        this.description = description;
        this.publisher = publisher;
        this.eventtitle = eventtitle;
        this.eventdate = eventdate;
    }

    public Event() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getEventimage() {
        return eventimage;
    }

    public void setEventimage(String eventimage) {
        this.eventimage = eventimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventtitle() {
        return eventtitle;
    }

    public void setEventtitle(String eventtitle) {
        this.eventtitle = eventtitle;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @NonNull
    @Override
    public String toString() {
        String content = "작성자 : " + publisher + ", 제목 :" + eventtitle;

        return content;
    }
}
