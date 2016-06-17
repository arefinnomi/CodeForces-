package com.teamplusplus.codeforces.connections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BlogEntryApiRequest {


    /**
     * The refer contains HTTP refer with method Name to access the data of
     * BlogEntry View
     */
    private final static String refer = "http://codeforces.com/api/blogEntry.view?";

    /**
     * lang contains parameter of return object post id
     */
    private final String blogEntryId;
    /**
     * to hold return json file
     */
    private String jsonString;
    /**
     * lang contains parameter of return object language
     */
    private String lang = "en";
    /**
     * url contains the full HTTP-request with protocol, host, methodName &
     * parameters
     */
    private URL url;

    /**
     * create object with blog entry id & preferred language
     *
     * @param blogEntryId the id of blog entry
     * @throws MalformedURLException
     */

    public BlogEntryApiRequest(int blogEntryId) throws MalformedURLException {
        this.blogEntryId = "" + blogEntryId;
        this.url = new URL(refer + "lang" + "=" + this.lang + "&" + "blogEntryId" + "=" + this.blogEntryId);
    }

    /**
     * create object with blog entry id & preferred language
     *
     * @param blogEntryId the id of blog entry
     * @param lang        en/ru - language of return object of api request ;
     * @throws MalformedURLException
     */
    public BlogEntryApiRequest(int blogEntryId, String lang) throws MalformedURLException {
        this.lang = lang;
        this.blogEntryId = blogEntryId + "";

        this.url = new URL(refer + "lang" + "=" + this.lang + "&" + "blogEntryId" + "=" + this.blogEntryId);

    }

    /**
     * create object with blog entry id & preferred language
     *
     * @param blogEntryId the id of blog entry
     * @throws MalformedURLException
     */

    public BlogEntryApiRequest(String blogEntryId) throws MalformedURLException {
        this.blogEntryId = blogEntryId;

        this.url = new URL(refer + "lang" + "=" + this.lang + "&" + "blogEntryId" + "=" + this.blogEntryId);

    }

    /**
     * create object with blog entry id & preferred language
     *
     * @param blogEntryId the id of blog entry
     * @param lang        en/ru - language of return object of api request ;
     * @throws MalformedURLException
     */
    public BlogEntryApiRequest(String blogEntryId, String lang) throws MalformedURLException {
        this.lang = lang;
        this.blogEntryId = blogEntryId;

        this.url = new URL(refer + "lang" + "=" + this.lang + "&" + "blogEntryId" + "=" + this.blogEntryId);

    }

    /**
     * @throws IOException
     */
    public void request() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        jsonString = response.body().string();
    }

    public String getJsonString() {
        return jsonString;
    }
}
