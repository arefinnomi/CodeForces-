package com.teamplusplus.codeforces.UI.userDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.cfobject.UserInfo;
import com.teamplusplus.codeforces.connections.UserInfoAPIRequest;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserDetailsActivityFragment extends Fragment {

    public UserDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootVew = inflater.inflate(R.layout.fragment_user_details, container, false);

        Intent intent = getActivity().getIntent();
        String handle = intent.getStringExtra("handle");
        LoadUserInfo loadUserInfo = new LoadUserInfo();
        loadUserInfo.execute(handle);
        return rootVew;
    }


    private class LoadUserInfo extends AsyncTask<String, Void, Void> {

        UserInfo userInfo = null;

        @Override
        protected Void doInBackground(String... params) {

            String userHandle = params[0];
            UserInfoAPIRequest userInfoAPIRequest = null;

            try {
                userInfoAPIRequest = new UserInfoAPIRequest(userHandle);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            try {
                if (userInfoAPIRequest != null) {
                    userInfoAPIRequest.request();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (userInfoAPIRequest != null) {
                    userInfo = new UserInfo(userInfoAPIRequest.getJsonString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            updateUI();
        }

        private void updateUI() {
            ((TextView) getActivity().findViewById(R.id.country_name)).setText(userInfo.getCountry());
            ((TextView) getActivity().findViewById(R.id.city_name)).setText(userInfo.getCity());
            ((TextView) getActivity().findViewById(R.id.organization_name)).setText(userInfo.getOrganization());
            ((TextView) getActivity().findViewById(R.id.email)).setText(userInfo.getEmail());
            String temp = "" + userInfo.getContribution();
            ((TextView) getActivity().findViewById(R.id.contribution)).setText(temp);
            ((TextView) getActivity().findViewById(R.id.rank)).setText(userInfo.getRank());
            temp = "" + userInfo.getRating();
            ((TextView) getActivity().findViewById(R.id.rating)).setText(temp);
            ((TextView) getActivity().findViewById(R.id.maxrank)).setText(userInfo.getMaxRank());
            temp = "" + userInfo.getMaxRating();
            ((TextView) getActivity().findViewById(R.id.maxRating)).setText(temp);
            ((TextView) getActivity().findViewById(R.id.vkid)).setText(userInfo.getVkId());
            ((TextView) getActivity().findViewById(R.id.openid)).setText(userInfo.getOpenId());
            temp = "" + userInfo.getFriend_of_count();
            ((TextView) getActivity().findViewById(R.id.friend_of_count)).setText(temp);
            ((TextView) getActivity().findViewById(R.id.user_name)).setText(userInfo.getUserString());
            TextView lastOnLine = (TextView) getActivity().findViewById(R.id.lastonline);
            if ((System.currentTimeMillis() - userInfo.getLastOnlineTimeSeconds()) / (1000 * 60) < 5) {
                lastOnLine.setTextColor(getResources().getColor(R.color.online));
                lastOnLine.setTypeface(null, Typeface.BOLD);
                temp = "online now";
                lastOnLine.setText(temp);
            } else lastOnLine.
                    setText(DateUtils.getRelativeDateTimeString(getContext(), userInfo.getLastOnlineTimeSeconds(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 1));
            TextView registered = (TextView) getActivity().findViewById(R.id.Registered);
            registered.setText(DateUtils.getRelativeDateTimeString(getContext(), userInfo.getRegistrationTimeSeconds(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 1));

            final ImageView imageView = (ImageView) getActivity().findViewById(R.id.title_photo);
            Picasso.get().load(userInfo.getTitle_photo()).into(imageView);
        }
    }
}
