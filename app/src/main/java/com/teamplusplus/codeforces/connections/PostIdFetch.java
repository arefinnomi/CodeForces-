package com.teamplusplus.codeforces.connections;

import android.content.Context;

import com.teamplusplus.codeforces.data.TitleBlogEntriesIdsDBHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostIdFetch {

    public static final ArrayList<Integer> postsId = new ArrayList<>();
    public static boolean nextPostIdInDatabase = false;
    public static short currentPage = 1;

    public static void loadPostId(Context context) throws IOException {


        String url = "http://codeforces.com/page/" + currentPage;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String html = response.body().string();


        Document doc;
        doc = Jsoup.parse(html);
        Elements elements = doc.select("div.topic");
        Element childDiv;
        String href;
        int index;

        TitleBlogEntriesIdsDBHelper titleBlogEntriesIdsDBHelper = new TitleBlogEntriesIdsDBHelper(context);
        int id;
        for (Element divTopic : elements) {
            childDiv = divTopic.select("a").first();
            href = childDiv.attr("href");
            index = href.lastIndexOf('/') + 1;
            id = Integer.parseInt(href.substring(index));

            if (titleBlogEntriesIdsDBHelper.has(id) && titleBlogEntriesIdsDBHelper.has(44012)) {
                nextPostIdInDatabase = true;
                currentPage = 1;
                return;
            } else if (!titleBlogEntriesIdsDBHelper.has(id)) {
                titleBlogEntriesIdsDBHelper.add(id);
                postsId.add(id);
            }

        }
        currentPage++;
    }


}