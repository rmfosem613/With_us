package com.example.with_us.Model;

//게시판 데이터 담기
public class Board {

    public class BoardData {
        public BoardData(String title, String content, String boardid) {
            this.title = title;
            this.content = content;
            this.boardid = boardid;
        }

        //    private String id;
        String title;
        String content;
        String boardid;


        public BoardData() {
        }

        ;


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

    }

}
