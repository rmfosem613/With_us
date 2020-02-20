package com.example.with_us.Model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Project {
    private String postid;
//    private String postimage;
    private String publisher;
    private String purpose_write;
    private String data_write;
    private String title_write;
    private String content_write;
    private String subject;

    public Project(String postid, String purpose_write, String data_write, String publisher, String title_write, String content_write) {
        this.postid = postid;
        this.purpose_write = purpose_write;
        this.data_write = data_write;
        this.publisher = publisher;
        this.title_write = title_write;
        this.content_write = content_write;
        this.subject = subject;
    }

    public Project() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPurpose_write() {
        return purpose_write;
    }

    public void setPurpose_write(String purpose_write) {
        this.purpose_write = purpose_write;
    }

    public String getData_write() {
        return data_write;
    }

    public void getData_write(String data_write) {
        this.data_write = data_write;
    }

    public String getTitle_write() {
        return title_write;
    }

    public void setTitle_write(String title_write) {
        this.title_write = title_write;
    }

    public String getContent_write() {
        return content_write;
    }

    public void setContent_write(String content_write) {
        this.content_write = content_write;
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
        String content = "작성자 : " + publisher + ", 제목 :" + title_write;

        return content;
    }
}
