package com.example.with_us.Model;

public class User {

    private String id;
    private String username;
    private String subject;
    private String imageurl;

    public User(String id, String username, String subject, String imageurl) {
        this.id = id;
        this.username = username;
        this.subject = subject;
        this.imageurl = imageurl;

    }

    public User() {
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
