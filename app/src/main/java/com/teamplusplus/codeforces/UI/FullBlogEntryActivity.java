package com.teamplusplus.codeforces.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.UI.userDetails.UserDetailsActivity;
import com.teamplusplus.codeforces.cfobject.BlogEntry;
import com.teamplusplus.codeforces.cfobject.CodeforcesHtmlBuilder;
import com.teamplusplus.codeforces.data.BlogEntriesDBHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FullBlogEntryActivity extends AppCompatActivity {

    private static final String LOG_TAG = FullBlogEntryActivity.class.getSimpleName();

    private static final String CODEFORCES_SHARE_HASH_TAG = " #Codeforces++";
    private String postUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_blog_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String entryId = intent.getStringExtra("entryId");
        BlogEntriesDBHelper blogEntriesDBHelper = new BlogEntriesDBHelper(this);
        BlogEntry blogEntry = blogEntriesDBHelper.getBlogEntryById(Integer.parseInt(entryId));

        postUrl = "codeforces.com/blog/entry/" + blogEntry.getId();
        View v = findViewById(R.id.full_blog_entry_scrollView);
        entryFullView(v, blogEntry);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(blogEntry.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_full_blog_entry, menu);
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_blog_entry_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
        return true;
    }


    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                postUrl + CODEFORCES_SHARE_HASH_TAG);
        return shareIntent;
    }

    private void entryFullView(View v, BlogEntry blogEntry) {

        final Context context = this;

    /* formatting unix timestamp with DateUtils*/
        String string = (String) DateUtils.getRelativeDateTimeString(this, blogEntry.getCreationTimeSeconds(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        ((TextView) v.findViewById(R.id.time_textView)).setText(string);

        final String author = blogEntry.getAuthorHandle();
        TextView authorTextView = ((TextView) v.findViewById(R.id.author_textView));
        authorTextView.setText(author);
        authorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent userDetailsIntent = new Intent(context, UserDetailsActivity.class);
                userDetailsIntent.putExtra("handle", author);
                startActivity(userDetailsIntent);
            }
        });


        if (blogEntry.getRating() > 0) string = "+" + blogEntry.getRating();
        else string = "" + blogEntry.getRating();

        ((TextView) v.findViewById(R.id.rating_textView)).setText(string);


        /**WebView updating*/
        Document document = Jsoup.parse(blogEntry.getContent());

        Log.d("crushed", blogEntry.getId() + "");

        CodeforcesHtmlBuilder codeforcesHtmlBuilder = new CodeforcesHtmlBuilder(document.html());
        WebView webView = (WebView) v.findViewById(R.id.post_webView);
        string = codeforcesHtmlBuilder.getHtml();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://codeforces.com/profile")) {
                    String handle = url.substring(url.lastIndexOf('/') + 1);
                    Intent userDetailsIntent = new Intent(context, UserDetailsActivity.class);
                    userDetailsIntent.putExtra("handle", handle);
                    startActivity(userDetailsIntent);
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

        ((TextView) v.findViewById(R.id.tag_textView)).setText(blogEntry.getTagsToString());
    }
}
