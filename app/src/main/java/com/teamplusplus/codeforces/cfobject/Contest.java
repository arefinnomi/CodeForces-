package com.teamplusplus.codeforces.cfobject;

import org.json.JSONException;
import org.json.JSONObject;


public class Contest {
    private int id;
    private String name;
    private String type;
    private String phase;
    private boolean frozen;
    private long durationSeconds;
    private long startTimeSeconds;
    private long relativeTimeSeconds;

    public Contest(final String singleContestRowData) throws JSONException {
        JSONObject contestJson = new JSONObject(singleContestRowData);
        name = contestJson.getString("name");
        id = contestJson.getInt("id");
        type = contestJson.getString("type");
        phase = contestJson.getString("phase");
        frozen = contestJson.getBoolean("frozen");
        durationSeconds = contestJson.getLong("durationSeconds");
        startTimeSeconds = contestJson.getLong("startTimeSeconds") * 1000;
        relativeTimeSeconds = contestJson.getLong("relativeTimeSeconds");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public long getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(long startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }

    public long getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public void setRelativeTimeSeconds(long relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds;
    }
}