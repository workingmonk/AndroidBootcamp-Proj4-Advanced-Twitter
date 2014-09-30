package com.yahoo.workmonk.advancedtwitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.workmonk.advancedtwitter.EndlessScrollListener;
import com.yahoo.workmonk.advancedtwitter.ProfileActivity;
import com.yahoo.workmonk.advancedtwitter.TwitterApplication;
import com.yahoo.workmonk.advancedtwitter.TwitterClient;
import com.yahoo.workmonk.advancedtwitter.models.Tweet;
import com.yahoo.workmonk.advancedtwitter.R;
import com.yahoo.workmonk.advancedtwitter.TweetArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by workmonk on 9/29/14.
 */
public class TweetsListFragment extends Fragment{
    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;
    protected TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        //Assign our view references
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String max_id = String.valueOf(tweets.get(totalItemsCount - 1).getUid() - 1);
                loadMoreDataFromApi(max_id);
            }
        });

        //Return the layout
        return v;
    }

    public void onProfileImageClick(View v){
        v.findViewById(R.id.ivProfileImage);
        RelativeLayout id = (RelativeLayout) v.getParent();
        TextView tvScreenName = (TextView) id.findViewById(R.id.tvUserName);
        String username = tvScreenName.getText().toString().toLowerCase().substring(1);
        Intent i = new Intent(getActivity(), ProfileActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void addAll(ArrayList<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    public void loadMoreDataFromApi(String max_id){
        client.getHomeTimeline(new JsonHttpResponseHandler(){
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
        }, max_id);
    }
}
