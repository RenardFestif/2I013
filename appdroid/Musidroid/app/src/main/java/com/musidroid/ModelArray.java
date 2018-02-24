package com.musidroid;

import com.musidroid.Model;

import java.util.ArrayList;

/**
 * Created by Jörmungandr on 23/02/2018.
 */

public class ModelArray {
    private static ArrayList<Model> models;

    public ModelArray(){
        models = new ArrayList<>();
    }

    public Model getModel(int position){
        return models.get(position);
    }

    public  static ArrayList<Model> getmodels(){
        return models;
    }

    public void addModel (Model model){
        models.add(model);
    }
}
