package com.musidroid;

import android.app.Application;

import model.Global;

public class TheApplication extends Application {

    ModelArray modelArray;
    @Override
    public void onCreate() {
        super.onCreate();
        modelArray = new ModelArray();

    }

    public ModelArray getModelArray(){return modelArray;}

    public void newModel(ModelArray modelArray){
        this.modelArray = modelArray;
    }



}
