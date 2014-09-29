package com.yahoo.workmonk.basictwitter.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by workmonk on 9/22/14.
 */
public class Tweet {
    private String body;
    private long uid;
    private String createdAt;
    private User user;

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);


                Tweet tweet = Tweet.fromJson(tweetJson);
                if(tweet!=null){
                    tweets.add(tweet);
                }
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }

        }


        return tweets;
    }

    public static Tweet fromJson(JSONObject jsonObject){
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


        return tweet;
    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

}
