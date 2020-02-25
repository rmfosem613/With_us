package com.example.with_us.Model;

//게시판 데이터 담기
public class Board {

    public Board(String purpose, String date, String title, String content, String boardid) {
        this.purpose = purpose;
        this.date = date;
        this.title = title;
        this.content = content;
        this.boardid = boardid;
    }



        //    private String id;
    String purpose;
    String date;
    String title;
    String content;
    String boardid;


    public Board() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

