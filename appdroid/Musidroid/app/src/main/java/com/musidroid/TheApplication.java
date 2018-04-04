package com.musidroid;

import android.app.Application;

import model.Global;

public class TheApplication extends Application {

    public ModelArray modelArray;
    @Override
    public void onCreate() {
        super.onCreate();
        modelArray = new ModelArray();

    }

    public ModelArray getModelArray(){return modelArray;}

    // ATTENTION SUPPRIME LE MODEL


    public void newModel (){
        modelArray = new ModelArray();
    }



}
