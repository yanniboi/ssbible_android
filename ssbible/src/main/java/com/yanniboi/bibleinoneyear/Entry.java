package com.yanniboi.bibleinoneyear;

/**
 * Created by yan on 22/04/14.
 */
public class Entry {

    /**
     * Header item type.
     */
    public static final int HEADER_ITEM = 0;

    /**
     * Category item type.
     */
    public static final int CATEGORY_ITEM = 1;

    /**
     * Entry item type.
     */
    public static final int ENTRY_ITEM = 2;

    private String id;
    private int nid;
    private String title;
    private int type;
    private int unreadCount;
    private String author;
    private String youtube;

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

    public String getId() {
        return id;
    }

    public int getNid() {
        return nid;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setId(String pageId) {
        this.id = pageId;
    }

    public void setNid(int pageNid) {
        this.nid = pageNid;
    }

    public void setType(int pageType) {
        this.type = pageType;
    }

    public void setTitle(String pageTitle) {
        this.title = pageTitle;
    }

    public void setAuthor(String pageAuthor) {
        this.author = pageAuthor;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void setYoutube(String pageYoutube) {
        this.youtube = pageYoutube;
    }



    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author
                + "]";
    }
}
