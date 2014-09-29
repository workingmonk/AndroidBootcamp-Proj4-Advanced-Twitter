package com.yahoo.workmonk.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.workmonk.basictwitter.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TimelineActivity extends Activity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        populateTimeline();
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String max_id = String.valueOf(tweets.get(totalItemsCount-1).getUid() - 1);
                loadMoreDataFromApi(max_id);
            }
        });
    }

    public void onAddNewTweet(MenuItem mi){
        Intent i = new Intent(this, ComposeActivity.class);
        startActivity(i);
    }

    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {
                aTweets.addAll(Tweet.fromJsonArray(jsonArray));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadMoreDataFromApi(String max_id){
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {
                aTweets.addAll(Tweet.fromJsonArray(jsonArray));
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
        }, max_id);
    }

}
