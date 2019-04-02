/**
 *
 */
package com.teamplusplus.codeforces.cfobject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author joy
 */
public class UserInfo {

    /**
     * to contain the return json object fields
     */
    private String handle;
    private String email = "not available";
    private int friend_of_count;
    private int contribution;
    private String rank = "not available";
    private String rating = "not available";
    private String maxRank = "not available";
    private String maxRating = "not available";
    private long lastOnlineTimeSeconds;
    private long registrationTimeSeconds;
    private String firstName = "not available";
    private String lastName = "not available";
    private String country = "not available";
    private String city = "not available";
    private String organization = "not available";
    private String vkId = "not available";
    private String openId = "not available";
    private String title_photo = "not available";
    private String avatar = "not available";
    private String userString = "not available";


    /**
     * for parsing the json file for the return object data if HTTP request
     * response status codes is ok
     *
     * @param rowData takes row json file of HTTP request
     * @throws JSONException
     */

    public UserInfo(final String rowData) throws JSONException {
        JSONObject rootData = new JSONObject(rowData);

        if (rootData.getString("status").equals("OK")) {

            JSONArray jsonArray = rootData.getJSONArray("result");
            JSONObject result;

            result = jsonArray.getJSONObject(0);
            handle = result.getString("handle");
            try {
                email = result.getString("email");

            } catch (JSONException e) {

                email = "not available";
            }
            try {
                firstName = result.getString("firstName");

            } catch (JSONException e) {

                firstName = "not available";
            }

            try {

                lastName = result.getString("lastName");

            } catch (JSONException e) {

                lastName = "not available";
            }

            if (firstName.equals("not available") && lastName.equals("not available")) {
                userString = "";
            } else if (!firstName.equals("not available") && lastName.equals("not available")) {
                userString = firstName;
            } else if (firstName.equals("not available") && !lastName.equals("not available")) {
                userString = lastName;
            } else if (!firstName.equals("not available") && !lastName.equals("not available")) {
                userString = firstName + " " + lastName;
            }

            try {
                country = result.getString("country");
            } catch (JSONException e) {

                country = "not available";
            }

            try {

                city = result.getString("city");

            } catch (JSONException e) {

                city = "not available";
            }

            try {

                organization = result.getString("organization");

            } catch (JSONException e) {

                organization = "not available";
            }

            friend_of_count = result.getInt("friendOfCount");
            contribution = result.getInt("contribution");
            try {

                rank = result.getString("rank");

            } catch (JSONException e) {

                rank = "not available";
            }

            try {

                maxRank = result.getString("maxRank");

            } catch (JSONException e) {

                maxRank = "not available";
            }

            try {
                vkId = result.getString("vkId");
            } catch (JSONException e) {
                vkId = "not available";
            }

            try {
                openId = result.getString("openId");
            } catch (JSONException e) {
                openId = "not available";
            }
            title_photo = "http:" + result.getString("titlePhoto");
            avatar = "http:" + result.getString("avatar");
            lastOnlineTimeSeconds = result
                    .getLong("lastOnlineTimeSeconds") * 1000;
            registrationTimeSeconds = result
                    .getLong("registrationTimeSeconds") * 1000;
            try {
                maxRating = result.getString("maxRating");
            } catch (JSONException e) {
                maxRating = "not available";
            }

            try {
                rating = result.getString("rating");
            } catch (JSONException e) {
                rating = "not available";
            }

        } else throw new JSONException("result !=\"ok\"");
    }

    public String getHandle() {
        return handle;
    }

    public String getEmail() {
        return email;
    }

    public int getFriend_of_count() {
        return friend_of_count;
    }

    public int getContribution() {
        return contribution;
    }

    public String getRank() {
        return rank;
    }

    public String getRating() {
        return rating;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public String getMaxRating() {
        return maxRating;
    }

    public long getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public long getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getOrganization() {
        return organization;
    }

    public String getVkId() {
        return vkId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getTitle_photo() {
        return title_photo;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserString() {
        return userString;
    }
}
