package com.example.myapplication.User;

public class Bookdeets {

    private String bookname;
    private String image;
    private String author;
    private String Publisher;
    private String link;
    private String Desc;
    private String id;

    public Bookdeets() {
    }

    public Bookdeets(String bookname, String image, String author, String publisher, String link, String desc, String id) {

        this.bookname = bookname;
        this.image = image;
        this.author = author;
        Publisher = publisher;
        this.link = link;
        Desc = desc;
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
