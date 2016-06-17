package com.teamplusplus.codeforces.cfobject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ContestAll {

    private Contest[] contestAll;

    public ContestAll(String rowData) throws JSONException {
        JSONObject rootData = new JSONObject(rowData);
        if (rootData.getString("status").equals("OK")) {
            JSONArray jsonArray = rootData.getJSONArray("result");
            JSONObject result;

            contestAll = new Contest[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                result = jsonArray.getJSONObject(i);
                contestAll[i] = new Contest(result.toString());
            }
        }
    }

    public Contest[] getContestAll() {
        return contestAll;
    }

    public int size() {
        return contestAll.length;
    }

    public Contest getContest(int index) {
        return contestAll[index];
    }
}
