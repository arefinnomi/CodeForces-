package com.teamplusplus.codeforces.connections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ContestListAPIRequest {

    private final static String refer = "http://codeforces.com/api/contest.list?";
    private String jsonString;
    private String lang = "en";
    private URL url;

    public ContestListAPIRequest(String lang) throws MalformedURLException {
        this.lang = lang;
        this.url = new URL(refer + "lang" + "=" + this.lang);
    }

    public ContestListAPIRequest() throws MalformedURLException {
        this.url = new URL(refer + "lang" + "=" + this.lang);
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
