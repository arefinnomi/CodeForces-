package com.teamplusplus.codeforces.UI.home;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamplusplus.codeforces.R;
import com.teamplusplus.codeforces.cfobject.Contest;

import java.util.List;


class ContestShowAdapter extends ArrayAdapter<Contest> {
    public ContestShowAdapter(Context context, List<Contest> objects) {
        super(context, R.layout.single_upcoming_contest_view, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.single_upcoming_contest_view, null);
        }
        Contest contest = getItem(position);

        long durationSeconds = contest.getDurationSeconds();

        long hour = durationSeconds / 3600;

        String hour1;
        String minute1;

        if (hour < 10) {
            hour1 = "0" + hour;
        } else {
            hour1 = "" + hour;
        }

        long minute = (durationSeconds - (hour * 3600)) / 60;

        if (minute < 10) {
            minute1 = "0" + minute;
        } else {
            minute1 = "" + minute;
        }

        String time = hour1 + ":" + minute1;

        ((TextView) v.findViewById(R.id.contest)).setText(contest.getName());
        ((TextView) v.findViewById(R.id.startTime)).setText(DateUtils.getRelativeDateTimeString(getContext(), contest.getStartTimeSeconds(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
        ((TextView) v.findViewById(R.id.duration)).setText(time);

        return v;
    }
}
