package com.teamplusplus.codeforces.cfobject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BlogEntry {


    private int id;
    private long creationTimeSeconds;
    private String authorHandle;
    private String title;
    private String content;
    private String[] tags;
    private int rating;
    private boolean allowViewHistory;
    private String locale;
    private long modificationTimeSeconds;
    private String originalLocale;

    public BlogEntry(int id, long creationTimeSeconds, String authorHandle, String title,
                     String content, String[] tags, int rating, boolean allowViewHistory,
                     String locale, long modificationTimeSeconds, String originalLocale) {
        this.id = id;
        this.creationTimeSeconds = creationTimeSeconds;
        this.authorHandle = authorHandle;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.rating = rating;
        this.allowViewHistory = allowViewHistory;
        this.locale = locale;
        this.modificationTimeSeconds = modificationTimeSeconds;
        this.originalLocale = originalLocale;
    }

    public BlogEntry() {

    }


    /**
     * for parsing the json file for the return object data if HTTP request
     * response status codes is ok
     *
     * @param rowData takes row json file of HTTP request
     * @throws JSONException
     */

    public BlogEntry(final String rowData) throws JSONException {

        JSONObject rootData = new JSONObject(rowData);

        JSONObject result;

		/* checking object status */

        if (rootData.getString("status").equals("OK")) {
            result = rootData.optJSONObject("result");


            id = result.getInt("id");
            originalLocale = result.getString("originalLocale");
            creationTimeSeconds = result.getLong("creationTimeSeconds") * 1000;
            allowViewHistory = result.getBoolean("allowViewHistory");
            rating = result.getInt("rating");
            authorHandle = result.getString("authorHandle");
            modificationTimeSeconds = result.getLong("modificationTimeSeconds") * 1000;

            Document document = Jsoup.parse(result.getString("title"));
            title = document.text();
            locale = result.getString("locale");
            content = result.getString("content");

            JSONArray tagsJsonArray = result.getJSONArray("tags");

            String temp[] = new String[tagsJsonArray.length()];

            for (int i = 0; i < tagsJsonArray.length(); i++) {
                temp[i] = tagsJsonArray.getString(i);
            }

            tags = temp;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAllowViewHistory() {

        return allowViewHistory;
    }

    public void setAllowViewHistory(boolean allowViewHistory) {
        this.allowViewHistory = allowViewHistory;
    }

    public String getAuthorHandle() {
        return authorHandle;
    }

    public void setAuthorHandle(String authorHandle) {
        this.authorHandle = authorHandle;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public long getModificationTimeSeconds() {
        return modificationTimeSeconds;
    }

    public void setModificationTimeSeconds(long modificationTimeSeconds) {
        this.modificationTimeSeconds = modificationTimeSeconds;
    }

    public String getOriginalLocale() {
        return originalLocale;
    }

    public void setOriginalLocale(String originalLocale) {
        this.originalLocale = originalLocale;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
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

    public long getCreationTimeSeconds() {
        return creationTimeSeconds;
    }

    public void setCreationTimeSeconds(long creationTimeSeconds) {
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public String getTagsToString() {
        String string = "";
        if (tags != null)
            for (int i = 0; i < tags.length; i++) {
                if (i == 0) string = tags[i];
                else string = string + ", " + tags[i];
            }

        return string;

    }


    public boolean isLessThan(BlogEntry blogEntry) {
        return this.id < blogEntry.id;
    }

}
