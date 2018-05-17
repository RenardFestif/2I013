package com.musidroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import model.Global;

public class Accueil2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil2);

        Global.fullScreenCall(this);
    }

    //Lance la création d'un nouvelle partition
    public void instrumentChooseView (View view){
        Intent intent = new Intent(this, TempoActivity.class);
        startActivity(intent);
    }

    //Lance le chargement d'une partition sauvegardé
    public void onClickCharger (View view){
        Intent intent = new Intent(this, ChargerActivity.class);
        startActivity(intent);
    }

    public void onQuitter(View v){
        this.finish();
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
