package com.example.with_us.Model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private String portfoliotitle;
    private String portfoliodate;

    public Post(String postid, String postimage, String description, String publisher, String portfoliotitle, String portfoliodate) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.portfoliotitle = portfoliotitle;
        this.portfoliodate = portfoliodate;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPortfoliotitle() {
        return portfoliotitle;
    }

    public void setPortfoliotitle(String portfoliotitle) {
        this.portfoliotitle = portfoliotitle;
    }

    public String getPortfoliodate() {
        return portfoliodate;
    }

    public void setPortfoliodate(String portfoliodate) {
        this.portfoliodate = portfoliodate;
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
        String content = "작성자 : " + publisher + ", 제목 :" + portfoliotitle;

        return content;
    }
}
