package com.yahoo.workmonk.basictwitter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.workmonk.basictwitter.R;

/**
 * Created by workmonk on 9/29/14.
 */
public class TweetsListFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        //Assign our view references

        //Return the layout
        return v;
    }


}
