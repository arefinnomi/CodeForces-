package com.teamplusplus.codeforces.UI.home;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ArrayList<BlogEntry> arrayList;
    //for handling the FeedPostIdLoad class from being invoked while running once
    private boolean runningFeedPostIdLoad = false;
    private BlogShowAdapter mAdepter;

    public FeedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blog_feed, container, false);
        BlogEntriesDBHelper blogEntriesDBHelper = new BlogEntriesDBHelper(getContext());
        arrayList = new ArrayList<>(blogEntriesDBHelper.getAllBlogEntry());
        new FeedPostIdLoad().execute();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.blogFeed_List);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        mAdepter = new BlogShowAdapter(getContext(), arrayList);
        recyclerView.addOnScrollListener(new BlogFeedOnScrollListener());
        recyclerView.setAdapter(mAdepter);

        return rootView;
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
                        ++newLoadedPost;
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
                arrayList.clear();
                arrayList.addAll(blogEntriesDBHelper.getAllBlogEntry());
                mAdepter.notifyDataSetChanged();
                //TODO sort arrayList
                newLoadedPost = 0;
            }
        }
    }

    private class BlogFeedOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            final int totalItemCount = linearLayoutManager.getItemCount();

            if (runningFeedPostIdLoad) return;
            if (totalItemCount == 0) {
                new FeedPostIdLoad().execute();
            } else {
                if (lastVisibleItem + 1 >= totalItemCount) {
                    new FeedPostIdLoad().execute();
                } else if (firstVisibleItem == 0 && dy < 0) {
                    PostIdFetch.nextPostIdInDatabase = false;
                    PostIdFetch.currentPage = 1;
                    new FeedPostIdLoad().execute();
                }
            }
        }
    }

}
