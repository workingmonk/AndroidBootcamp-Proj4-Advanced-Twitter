package com.yahoo.workmonk.advancedtwitter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.workmonk.advancedtwitter.TwitterApplication;
import com.yahoo.workmonk.advancedtwitter.TwitterClient;
import com.yahoo.workmonk.advancedtwitter.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by workmonk
 */

public class UserTimelineFragment extends TweetsListFragment{
    String username = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        populateTimeline();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void populateTimeline() {
        client.getUserTimeline(username, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                addAll(Tweet.fromJsonArray(jsonArray));
            }

            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                Log.d("debug", throwable.toString());
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                Log.d("debug", throwable.toString());
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", throwable.toString());
                Log.d("debug", s.toString());
            }
        });
    }
}
