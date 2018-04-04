package com.musidroid;

import com.musidroid.Model;

import java.util.ArrayList;


public class ModelArray {
    public static ArrayList<Model> models;

    public ModelArray(){
        models = new ArrayList<>();
    }

    public Model getModel(int position){
        return models.get(position);
    }

    public ArrayList<Model> getModels(){ return models; }

    public  static ArrayList<Model> getmodels(){
        return models;
    }

    public void addModel (Model model){
        models.add(model);
    }

    //public void reset(){
    //    for (int i=0;i<models.size(); i++) {
    //        models.get(i).reset();
    //    }
    //
    //
    //}
}
