package com.example.myapplication.User;

public class Bookdeets {

    private String bookname;
    private String image;
    private String author;
    private String id;
    private String category;

    public Bookdeets() {
    }

    public Bookdeets(String bookname, String image, String author, String publisher, String link, String desc, String id,String category) {
        this.bookname = bookname;
        this.image = image;
        this.author = author;
        this.id = id;
        this.category=category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
