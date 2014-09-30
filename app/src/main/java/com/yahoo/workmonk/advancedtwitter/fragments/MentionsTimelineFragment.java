package com.yahoo.workmonk.advancedtwitter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.workmonk.advancedtwitter.TwitterApplication;
import com.yahoo.workmonk.advancedtwitter.TwitterClient;
import com.yahoo.workmonk.advancedtwitter.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by workmonk on 9/29/14.
 */
public class MentionsTimelineFragment extends TweetsListFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline();
    }

    public void populateTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler(){
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
