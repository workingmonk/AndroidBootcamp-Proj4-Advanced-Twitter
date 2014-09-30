package com.yahoo.workmonk.advancedtwitter.models;

import org.json.JSONObject;

/**
 * Created by workmonk on 9/22/14.
 */
public class User {
    private String name;
    private long uid;
    private String screenName;
    private String description;
    private int followers_count;
    private int friends_count;
    private String profileImageUrl;

    public static User fromJson(JSONObject jsonObject){
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.description = jsonObject.getString("description");
            user.followers_count = jsonObject.getInt("followers_count");
            user.friends_count = jsonObject.getInt("friends_count");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return description;
    }

    public int getFollowersCount() {
        return followers_count;
    }

    public int getFriendsCount() {
        return friends_count;
    }
}
