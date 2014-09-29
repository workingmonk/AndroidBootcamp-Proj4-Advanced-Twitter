package com.yahoo.workmonk.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;


public class ComposeActivity extends Activity {
    private ImageView ivProfileImage;
    private TextView tvUserName;
    private TextView tvScreenName;
    private EditText etNewTweet;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImageCompose);
        tvUserName = (TextView) findViewById(R.id.tvUserNameCompose);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        populateUserInfo();
    }

    public void populateUserInfo(){
        client.getLoggedInUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    tvScreenName.setText("@" + jsonObject.getString("screen_name"));
                    tvUserName.setText(jsonObject.getString("name"));
                    ivProfileImage.setImageResource(android.R.color.transparent);
                    ImageLoader imageLoader = ImageLoader.getInstance();

                    imageLoader.displayImage(jsonObject.getString("profile_image_url"), ivProfileImage);
                } catch (Exception e){
                    Log.d("debug", e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                Log.d("debug", throwable.toString());
            }
        });
    }

    public void onDoneTweet(MenuItem mi){
        String tweetStr = etNewTweet.getText().toString();
        client.sendTweet(tweetStr, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                Log.d("debug", throwable.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
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
