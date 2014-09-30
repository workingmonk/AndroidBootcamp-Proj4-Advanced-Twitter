package com.yahoo.workmonk.advancedtwitter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.workmonk.advancedtwitter.fragments.TweetsListFragment;
import com.yahoo.workmonk.advancedtwitter.fragments.UserTimelineFragment;
import com.yahoo.workmonk.advancedtwitter.models.User;

import org.json.JSONObject;


public class ProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String username = getIntent().getStringExtra("username");
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));
        if(username!=null)
            loadProfileInfo(username);
        else
            loadProfileInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    public void loadProfileInfo(){
        TwitterApplication.getRestClient().getLoggedInUserInfo(
            new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject json){
                    User u = User.fromJson(json);
                    getActionBar().setTitle("@" + u.getScreenName());
                    populateProfileHeader(u);
                    populateUserTweets(u.getScreenName());
                }
            });
    }

    public void loadProfileInfo(String username){
        TwitterApplication.getRestClient().getUserInfo(username,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        User u = User.fromJson(json);
                        getActionBar().setTitle("@" + u.getScreenName());
                        populateProfileHeader(u);
                        populateUserTweets(u.getScreenName());
                    }
                });
    }

    public void populateProfileHeader(User u) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollower = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(u.getName());
        tvTagline.setText(u.getTagline());
        tvFollower.setText(u.getFollowersCount() + " Followers");
        tvFollowing.setText(u.getFriendsCount() + " Following");
        ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
    }

    public void populateUserTweets(String username){
//        Bundle bundle = new Bundle();
//        bundle.putString("username", username);
        // set Fragmentclass Arguments
        UserTimelineFragment userTimelineFragment = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
        userTimelineFragment.setUsername(username);
        userTimelineFragment.populateTimeline();

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
}
