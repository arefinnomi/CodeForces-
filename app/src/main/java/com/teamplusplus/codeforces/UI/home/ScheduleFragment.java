package com.teamplusplus.codeforces.UI.home;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.cfobject.Contest;
import com.teamplusplus.codeforces.cfobject.ContestAll;
import com.teamplusplus.codeforces.connections.ContestListAPIRequest;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {


    private ContestShowAdapter contestShowAdapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ListView listView = (ListView) inflater.inflate(R.layout.schedule_list, container, false);

        ArrayList<Contest> contestList = new ArrayList<>();


        contestShowAdapter = new ContestShowAdapter(getContext(), contestList);

        listView.setAdapter(contestShowAdapter);
        new Load().execute();

        return listView;
    }

    private class Load extends AsyncTask<Void, Void, Void> {

        ContestAll contestAll = null;

        @Override
        protected Void doInBackground(Void... params) {

            ContestListAPIRequest contestListAPIRequest = null;
            try {
                contestListAPIRequest = new ContestListAPIRequest();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                if (contestListAPIRequest != null) {
                    contestListAPIRequest.request();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (contestListAPIRequest != null) {
                    contestAll = new ContestAll(contestListAPIRequest.getJsonString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < contestAll.size(); i++) {
//                if(contestAll.getContest(i).getPhase().equals("BEFORE"))
                contestShowAdapter.add(contestAll.getContest(i));
            }
        }
    }


}
