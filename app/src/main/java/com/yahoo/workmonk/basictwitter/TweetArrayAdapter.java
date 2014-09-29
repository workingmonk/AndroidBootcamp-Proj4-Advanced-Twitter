package com.yahoo.workmonk.basictwitter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.workmonk.basictwitter.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by workmonk on 9/22/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet>{

    public TweetArrayAdapter(Context context, List<Tweet> tweets){
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        View v;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        TextView tvTweetTime = (TextView) v.findViewById(R.id.tvTweetTime);

        ivProfileImage.setImageResource(android.R.color.transparent);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvUserName.setText("@" + tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTweetTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        return v;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        relativeDate = relativeDate.replace(" minutes", "m");
        relativeDate = relativeDate.replace(" minute", "m");
        relativeDate = relativeDate.replace(" ago", "");
        if(relativeDate.contains("second")){
            relativeDate = "Now";
        }

        return relativeDate;
    }

}
