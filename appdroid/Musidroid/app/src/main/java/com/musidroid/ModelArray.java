package com.musidroid;

import com.musidroid.Model;

import java.util.ArrayList;


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

    public ArrayList<Model> getModels(){return models;}

    public void addModel (Model model){
        models.add(model);
    }

    public static void setModels(ArrayList<Model> mod) {
        models = new ArrayList<>();
        for(int i=0;i<mod.size();i++){
            models.add(mod.get(i));
        }
    }


}
