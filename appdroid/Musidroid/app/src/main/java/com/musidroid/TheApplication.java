package com.musidroid;

import android.app.Application;

import com.musidroid.Model;

public class TheApplication extends Application {
    Model m;
    @Override
    public void onCreate() {
        super.onCreate();
        m = new Model();
    }
    public Model getModel() {
        return m;
    } }
