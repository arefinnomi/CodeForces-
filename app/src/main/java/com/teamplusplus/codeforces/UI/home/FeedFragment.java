package com.teamplusplus.codeforces.UI.home;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.cfobject.BlogEntry;
import com.teamplusplus.codeforces.connections.BlogEntryApiRequest;
import com.teamplusplus.codeforces.connections.PostIdFetch;
import com.teamplusplus.codeforces.data.BlogEntriesDBHelper;
import com.teamplusplus.codeforces.data.TitleBlogEntriesIdsDBHelper;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    //for handling the FeedPostIdLoad class from being invoked while running once
    private boolean runningFeedPostIdLoad = false;
    private BlogShowAdapter mAdepter;


    public FeedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.feedlistview, container, false);

//
        BlogEntriesDBHelper blogEntriesDBHelper = new BlogEntriesDBHelper(getContext());
        ArrayList<BlogEntry> arrayList = new ArrayList<>(blogEntriesDBHelper.getAllBlogEntry());

        mAdepter = new BlogShowAdapter(getContext(), arrayList);


        listView.setAdapter(mAdepter);
        listView.setOnScrollListener(new BlogFeedOnScrollListener());


        if (mAdepter.getCount() != 0) new FeedPostIdLoad().execute();
        return listView;
    }


    private class FeedPostIdLoad extends AsyncTask<Void, Void, Void> {


        int newLoadedPost = 0;

        @Override
        protected Void doInBackground(Void... params) {


            if (!PostIdFetch.nextPostIdInDatabase) {
                try {
                    PostIdFetch.loadPostId(getContext());
                    BlogEntriesDBHelper blogEntriesDBHelper = new BlogEntriesDBHelper(getContext());
                    for (int postsID : PostIdFetch.postsId) {

                        if (!blogEntriesDBHelper.has(postsID)) {
                            newLoadedPost++;
                            try {
                                BlogEntryApiRequest blogEntryApiRequest = new BlogEntryApiRequest(postsID);
                                blogEntryApiRequest.request();
                                BlogEntry blogEntry = new BlogEntry(blogEntryApiRequest.getJsonString());
                                blogEntriesDBHelper.add(blogEntry);
                            } catch (JSONException | MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                BlogEntriesDBHelper blogEntriesDBHelper = new BlogEntriesDBHelper(getContext());

                TitleBlogEntriesIdsDBHelper titleBlogEntriesIdsDBHelper = new TitleBlogEntriesIdsDBHelper(getContext());
                for (int postsID : titleBlogEntriesIdsDBHelper.getAllBlogEntry()) {

                    if (newLoadedPost > 8) break;
                    if (!blogEntriesDBHelper.has(postsID)) {
                        newLoadedPost++;
                        try {
                            BlogEntryApiRequest blogEntryApiRequest = new BlogEntryApiRequest(postsID);
                            blogEntryApiRequest.request();
                            BlogEntry blogEntry = new BlogEntry(blogEntryApiRequest.getJsonString());
                            blogEntriesDBHelper.add(blogEntry);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            runningFeedPostIdLoad = true;
            Toast.makeText(getContext(), "Please wait!\nLoading post", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            runningFeedPostIdLoad = false;

            Toast.makeText(getContext(), "Finished.", Toast.LENGTH_SHORT).show();

            if (newLoadedPost != 0) {
                BlogEntriesDBHelper blogEntriesDBHelper = new BlogEntriesDBHelper(getContext());
                mAdepter.clear();
                mAdepter.addAll(blogEntriesDBHelper.getAllBlogEntry());
                mAdepter.sort();
                newLoadedPost = 0;
            }
        }
    }


    private class BlogFeedOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, final int totalItemCount) {

            if (runningFeedPostIdLoad) return;
            if (totalItemCount == 0) {
                new FeedPostIdLoad().execute();
            } else {
                if (totalItemCount - firstVisibleItem - visibleItemCount == 0) {
                    new FeedPostIdLoad().execute();
                }
            }
        }

    }
}
