package com.teamplusplus.codeforces.connections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoAPIRequest {

    private final static String refer = "http://codeforces.com/api/user.info?";
    /**
     * used by class
     */
    private final String user_name;
    private String jsonString;
    private String lang = "en";
    private URL url;

    public UserInfoAPIRequest(String userString) throws MalformedURLException {
        this.user_name = userString;
        this.url = new URL(refer + "lang" + "=" + this.lang + "&" + "handles"
                + "=" + this.user_name);
    }

    public UserInfoAPIRequest(String userString, String lang)
            throws MalformedURLException {
        this.user_name = userString;
        this.lang = lang;
        this.url = new URL(refer + "lang" + "=" + this.lang + "&" + "handles"
                + "=" + this.user_name);
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
