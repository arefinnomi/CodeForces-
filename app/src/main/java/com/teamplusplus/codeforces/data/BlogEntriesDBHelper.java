package com.teamplusplus.codeforces.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.teamplusplus.codeforces.cfobject.BlogEntry;

import java.util.ArrayList;
import java.util.List;


public class BlogEntriesDBHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "codeforces";

    // Contacts table name
    private static final String TABLE_CONTACTS = "blogEntries";


    // Contacts Table Columns names
    private static final String KEY_id = "id";
    private static final String KEY_creationTimeSeconds = "creationTimeSeconds";
    private static final String KEY_authorHandle = "authorHandle";
    private static final String KEY_title = "title";
    private static final String KEY_content = "content";
    private static final String KEY_tags = "tags";
    private static final String KEY_rating = "rating";
    private static final String KEY_allowViewHistory = "allowViewHistory";
    private static final String KEY_locale = "locale";
    private static final String KEY_modificationTimeSeconds = "modificationTimeSeconds";
    private static final String KEY_originalLocale = "originalLocale";


    public BlogEntriesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void add(BlogEntry blogEntry) {

        if (!has(blogEntry)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_id, blogEntry.getId());
            values.put(KEY_creationTimeSeconds, blogEntry.getCreationTimeSeconds());
            values.put(KEY_authorHandle, blogEntry.getAuthorHandle());
            values.put(KEY_title, blogEntry.getTitle());
            values.put(KEY_content, blogEntry.getContent());
            values.put(KEY_tags, blogEntry.getTagsToString());
            values.put(KEY_rating, blogEntry.getRating());
            values.put(KEY_allowViewHistory, blogEntry.isAllowViewHistory());
            values.put(KEY_locale, blogEntry.getLocale());
            values.put(KEY_modificationTimeSeconds, blogEntry.getModificationTimeSeconds());
            values.put(KEY_originalLocale, blogEntry.getOriginalLocale());

            db.insert(TABLE_CONTACTS, null, values);
        }
    }

    public boolean has(BlogEntry blogEntry) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, null, KEY_id + "=?",
                new String[]{String.valueOf(blogEntry.getId())}, null, null, null, null);
        boolean flag = false;

        if (cursor.getCount() != 0) {
            flag = true;
            cursor.close();
        }
        return flag;
    }

    public boolean has(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, null, KEY_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        boolean flag = false;

        if (cursor.getCount() != 0) {
            flag = true;
        }
        cursor.close();
        return flag;
    }

    public BlogEntry getBlogEntryById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, null, KEY_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor == null) return null;
        cursor.moveToFirst();
        return getBlogEntryFormCursor(cursor);
    }

    private BlogEntry getBlogEntryFormCursor(Cursor cursor) {
        BlogEntry blogEntry;

        String tags[] = null;
        String temp = cursor.getString(5);
        if (!temp.equals("")) {
            int indexOfComma = 0;
            ArrayList<String> list = new ArrayList<>();
            int indexOfNextComma = temp.indexOf(',', indexOfComma + 1);
            while (indexOfNextComma != -1) {
                list.add(temp.substring(indexOfComma, indexOfNextComma - 1).trim());
                indexOfComma = 1 + indexOfNextComma;
                indexOfNextComma = temp.indexOf(',', indexOfComma);
            }
            list.add(temp.substring(indexOfComma).trim());
            tags = new String[list.size()];
            for (int i = 0; i < tags.length; i++) tags[i] = list.get(i);
        }
        blogEntry = new BlogEntry(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), tags, cursor.getInt(6), Boolean.parseBoolean(cursor.getString(7)),
                cursor.getString(8), cursor.getLong(9), cursor.getString(10));


        return blogEntry;
    }

    // Getting All Contacts
    public List<BlogEntry> getAllBlogEntry() {
        List<BlogEntry> blogEntryList = new ArrayList<>();
        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, KEY_creationTimeSeconds + " DESC");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BlogEntry blogEntry = getBlogEntryFormCursor(cursor);
                // Adding contact to list
                blogEntryList.add(blogEntry);
            } while (cursor.moveToNext());
        }

        // return contact list
        return blogEntryList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_id + " INTEGER," + KEY_creationTimeSeconds + " INTEGER,"
                + KEY_authorHandle + " TEXT, " + KEY_title + " TEXT, "
                + KEY_content + " TEXT, " + KEY_tags + " TEXT, "
                + KEY_rating + " INTEGER," + KEY_allowViewHistory + " TEXT, "
                + KEY_locale + " TEXT, " + KEY_modificationTimeSeconds + " INTEGER,"
                + KEY_originalLocale + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


}
