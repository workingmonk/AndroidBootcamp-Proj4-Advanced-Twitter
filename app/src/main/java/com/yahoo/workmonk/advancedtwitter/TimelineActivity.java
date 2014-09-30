package com.yahoo.workmonk.advancedtwitter;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.workmonk.advancedtwitter.fragments.HomeTimelineFragment;
import com.yahoo.workmonk.advancedtwitter.fragments.MentionsTimelineFragment;
import com.yahoo.workmonk.advancedtwitter.fragments.TweetsListFragment;
import com.yahoo.workmonk.advancedtwitter.listener.FragmentTabListener;
import com.yahoo.workmonk.advancedtwitter.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;


public class TimelineActivity extends FragmentActivity {
    TweetsListFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setUpTabs();

    }

    public void onProfileImageClick(View v){
//        new TweetsListFragment().onProfileImageClick(v);
        TweetsListFragment abc = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.flContainer);
        abc.onProfileImageClick(v);
//        TweetsListFragment.onProfileImageClick(v);
    }

    private void setUpTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_home)
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setIcon(R.drawable.ic_mentions)
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
                                MentionsTimelineFragment.class));

        actionBar.addTab(tab2);
    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }


    public void onAddNewTweet(MenuItem mi){
        Intent i = new Intent(this, ComposeActivity.class);
        startActivity(i);
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



}
