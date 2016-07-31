package com.teamplusplus.codeforces.UI.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.UI.FullBlogEntryActivity;
import com.teamplusplus.codeforces.UI.userDetails.UserDetailsActivity;
import com.teamplusplus.codeforces.cfobject.BlogEntry;
import com.teamplusplus.codeforces.cfobject.CodeforcesHtmlBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class BlogShowAdapter extends RecyclerView.Adapter<BlogShowAdapter.BlogShowViewHolder> {


    Context context;
    private List<BlogEntry> blogEntryList;

    public BlogShowAdapter(Context context, List<BlogEntry> blogEntryList) {
        this.blogEntryList = blogEntryList;
        this.context = context;
    }

    @Override
    public BlogShowAdapter.BlogShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_single_post, parent, false);
        return new BlogShowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogShowAdapter.BlogShowViewHolder holder, int position) {
        final BlogEntry blogEntry = blogEntryList.get(position);


    /* formatting unix timestamp with DateUtils*/
        String string = (String) DateUtils.getRelativeDateTimeString(context, blogEntry.getCreationTimeSeconds(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        holder.time_textView.setText(string);

        final String author = blogEntry.getAuthorHandle();
        TextView authorTextView = holder.author_textView;
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

        holder.rating_textView.setText(string);


        TextView postTitle = holder.postName_textView;
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
        WebView webView = holder.post_webView;
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

        holder.tag_textView.setText(blogEntry.getTagsToString());

    }

    @Override
    public int getItemCount() {
        return blogEntryList.size();
    }

    private void startFullBlogEntryActivity(Context context, BlogEntry blogEntry) {
        Intent intent = new Intent(context, FullBlogEntryActivity.class);
        intent.putExtra("entryId", String.valueOf(blogEntry.getId()));
        context.startActivity(intent);
    }

    public void sort() {

        Collections.sort(blogEntryList, new Comparator<BlogEntry>() {
            @Override
            public int compare(BlogEntry lhs, BlogEntry rhs) {
                if (lhs.isLessThan(rhs)) return 1;
                return -1;
            }
        });

        notifyDataSetChanged();
    }

    public static class BlogShowViewHolder extends RecyclerView.ViewHolder {

        protected TextView postName_textView;
        protected TextView author_textView;
        protected TextView time_textView;
        protected WebView post_webView;
        protected TextView tag_textView;
        protected TextView rating_textView;

        public BlogShowViewHolder(View itemView) {
            super(itemView);

            post_webView = (WebView) itemView.findViewById(R.id.post_webView);
            postName_textView = (TextView) itemView.findViewById(R.id.postName_textView);
            author_textView = (TextView) itemView.findViewById(R.id.author_textView);
            time_textView = (TextView) itemView.findViewById(R.id.time_textView);
            rating_textView = (TextView) itemView.findViewById(R.id.rating_textView);
            tag_textView = (TextView) itemView.findViewById(R.id.tag_textView);
        }
    }

}
