package com.example.samuel.voicedemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Samuel on 17/01/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    Context ctx;
    public PagerAdapter(Context ctx,FragmentManager fm) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if(position == 0){
            fragment = new Say();
        }
        else{
            fragment = new Dial();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Say";
        }
        else{
           return "Dial";
        }

    }
}
