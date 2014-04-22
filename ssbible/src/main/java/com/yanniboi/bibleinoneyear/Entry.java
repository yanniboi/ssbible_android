package com.yanniboi.bibleinoneyear;

/**
 * Created by yan on 22/04/14.
 */
public class Entry {

    private int id;
    private int nid;
    private String title;
    private String author;

    public Entry(){}

    public Entry(String title, String author) {
        super();
        this.nid = 0;
        this.title = title;
        this.author = author;
    }

    public Entry(String title, String author, int nid) {
        super();
        this.nid = nid;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return this.id;
    }

    public int getNid() {
        return this.nid;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setId(int pageId) {
        this.id = pageId;
    }

    public void setNid(int pageNid) {
        this.nid = pageNid;
    }

    public void setTitle(String pageTitle) {
        this.title = pageTitle;
    }

    public void setAuthor(String pageAuthor) {
        this.author = pageAuthor;
    }



    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author
                + "]";
    }
}
