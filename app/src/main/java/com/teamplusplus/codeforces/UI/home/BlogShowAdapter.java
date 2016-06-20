package com.teamplusplus.codeforces.UI.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.UI.FullBlogEntryActivity;
import com.teamplusplus.codeforces.UI.userDetails.UserDetailsActivity;
import com.teamplusplus.codeforces.cfobject.BlogEntry;
import com.teamplusplus.codeforces.cfobject.CodeforcesHtmlBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Comparator;
import java.util.List;


class BlogShowAdapter extends ArrayAdapter<BlogEntry> {


    public BlogShowAdapter(Context context, List<BlogEntry> objects) {

        super(context, R.layout.cardview_single_post, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.cardview_single_post, null);
        }

        BlogEntry blogEntry = getItem(position);

        if (blogEntry != null) {
            BlogEntryShortView(v, blogEntry);
        }
        return v;
    }


    private void BlogEntryShortView(View v, final BlogEntry blogEntry) {
        final Context context = getContext();
    /* formatting unix timestamp with DateUtils*/
        String string = (String) DateUtils.getRelativeDateTimeString(getContext(), blogEntry.getCreationTimeSeconds(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        ((TextView) v.findViewById(R.id.time_textview)).setText(string);

        final String author = blogEntry.getAuthorHandle();
        TextView authorTextView = ((TextView) v.findViewById(R.id.author_text_view));
        authorTextView.setText(author);
        authorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent userDetailsIntent = new Intent(context, UserDetailsActivity.class);
                userDetailsIntent.putExtra("handle", author);
                context.startActivity(userDetailsIntent);
            }
        });


        if (blogEntry.getRating() > 0) string = "+" + blogEntry.getRating();
        else string = "" + blogEntry.getRating();

        ((TextView) v.findViewById(R.id.rating_textview)).setText(string);


        TextView postTitle = ((TextView) v.findViewById(R.id.postname_textview));
        postTitle.setText(blogEntry.getTitle());
        postTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startFullBlogEntryActivity(context, blogEntry);
            }
        });


        /**WebView updating*/
        Document document = Jsoup.parse(blogEntry.getContent());
        Elements elements = document.select("p");
        CodeforcesHtmlBuilder codeforcesHtmlBuilder;
        if (elements.size() >= 2)
            codeforcesHtmlBuilder = new CodeforcesHtmlBuilder("<div class=\"ttypography\">"
                    + "<p>" + elements.get(0).html() + "</p>" + "<p>" + elements.get(1).html() + "\n" +
                    "<a href=\"http://www.teemplusplus.com\"> see full post</a>" + "</p>" + "</div>");
        else codeforcesHtmlBuilder = new CodeforcesHtmlBuilder(document.html() + "\n" +
                "<a href=\"http://www.teemplusplus.com\">see full post</a>");
        WebView webView = (WebView) v.findViewById(R.id.postshow_shortform_webview);
        string = codeforcesHtmlBuilder.getHtml();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.equals("http://www.teemplusplus.com/")) {
                    startFullBlogEntryActivity(context, blogEntry);
                } else if (url.startsWith("http://codeforces.com/profile")) {
                    String handle = url.substring(url.lastIndexOf('/') + 1);

                    Intent userDetailsIntent = new Intent(context, UserDetailsActivity.class);
                    userDetailsIntent.putExtra("handle", handle);
                    context.startActivity(userDetailsIntent);
                } else {
                    Uri webPage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }
                }
                return true;
            }
        });
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadDataWithBaseURL("http://codeforces.com", string, "text/html", "utf-8", null);

        ((TextView) v.findViewById(R.id.tag_textview)).setText(blogEntry.getTagsToString());
    }

    private void startFullBlogEntryActivity(Context context, BlogEntry blogEntry) {
        Intent intent = new Intent(context, FullBlogEntryActivity.class);
        intent.putExtra("entryId", String.valueOf(blogEntry.getId()));
        context.startActivity(intent);
    }

    public void sort() {

        super.sort(new Comparator<BlogEntry>() {
            @Override
            public int compare(BlogEntry lhs, BlogEntry rhs) {
                if (lhs.isLessThan(rhs)) return 1;
                return -1;
            }
        });


        notifyDataSetChanged();
    }
}
