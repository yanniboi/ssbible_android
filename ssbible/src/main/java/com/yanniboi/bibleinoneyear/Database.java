package com.yanniboi.bibleinoneyear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "NodeDB";

    // Books table name
    private static final String TABLE_PAGES = "page";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NID = "nid";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_YOUTUBE = "youtube";

    private static final String[] COLUMNS = {KEY_ID,KEY_NID,KEY_TITLE,KEY_AUTHOR, KEY_YOUTUBE};

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_PAGE_TABLE = "CREATE TABLE page ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nid INTEGER, "+
                "title TEXT, "+
                "youtube TEXT, "+
                "author TEXT )";


        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, "Welcome to the App"); // get title
        values.put(KEY_NID, 0); // get title
        values.put(KEY_AUTHOR, "yanniboi"); // get author
        values.put(KEY_YOUTUBE, "icO_tRjilDI"); // get author


        db.execSQL(CREATE_PAGE_TABLE);
        db.insert(TABLE_PAGES, // table
                null, //nullColumnHack
                values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS page");

        // create fresh books table
        this.onCreate(db);
    }

    public void addPage(Entry page){
        //for logging
        Log.d("addPage", page.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NID, page.getNid()); // get nid
        values.put(KEY_TITLE, page.getTitle()); // get title
        values.put(KEY_AUTHOR, page.getAuthor()); // get author
        values.put(KEY_YOUTUBE, page.getYoutube()); // get author

        // 3. insert
        db.insert(TABLE_PAGES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Entry getPage(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_PAGES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build page object
        Entry page = new Entry();
        page.setId(cursor.getString(0));
        if (cursor.getString(1) == null) {
            page.setNid(0);
        }
        else {
            page.setNid(Integer.parseInt(cursor.getString(1)));
        }
        page.setTitle(cursor.getString(2));
        page.setAuthor(cursor.getString(3));

        //log
        Log.d("getBook("+id+")", page.toString());

        // 5. return book
        return page;
    }

    public Entry getPagebyNid(int nid){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_PAGES, // a. table
                        COLUMNS, // b. column names
                        " nid = ?", // c. selections
                        new String[] { String.valueOf(nid) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            Boolean success = cursor.moveToFirst();
            if (!success) {
                return new Entry();
            }
        }


        // 4. build book object
        Entry page = new Entry();
        page.setId(cursor.getString(0));
        if (cursor.getString(1) == null) {
            page.setNid(0);
        }
        else {
            page.setNid(Integer.parseInt(cursor.getString(1)));
        }
        page.setTitle(cursor.getString(2));
        page.setAuthor(cursor.getString(3));
        page.setYoutube(cursor.getString(4));

        //log
        Log.d("getBook("+nid+")", page.toString());

        // 5. return book
        return page;
    }

    public List<Entry> getAllPages() {
        List<Entry> pages = new LinkedList<Entry>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PAGES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Entry page = null;
        if (cursor.moveToFirst()) {
            do {
                page = new Entry();
                page.setId(cursor.getString(0));
                if (cursor.getString(1) == null) {
                    page.setNid(0);
                }
                else {
                    page.setNid(Integer.parseInt(cursor.getString(1)));
                }
                page.setTitle(cursor.getString(2));
                page.setAuthor(cursor.getString(3));

                // Add book to books
                pages.add(page);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", pages.toString());

        // return books
        return pages;
    }

}
