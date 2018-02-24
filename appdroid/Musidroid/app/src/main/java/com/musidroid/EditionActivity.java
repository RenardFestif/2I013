package com.musidroid;

import android.content.Intent;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;


import java.util.ArrayList;

import model.Global;
import model.extended.PartitionX;


public class EditionActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomeSwipe customeSwipe;
    TheApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        viewPager=(ViewPager) findViewById(R.id.CarousselViewPager);
        customeSwipe = new CustomeSwipe(this);
        viewPager.setAdapter(customeSwipe);






        //Generation de Partition
        //PartitionX partitionX = Global.getPartition();




        //Menu
        final TabLayout tabLayout =(TabLayout) findViewById(R.id.menuTabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){

                    //Position icone + => Add partie
                    case 1:
                        onClickAdd(tabLayout);
                        break;

                    // Select une Partie
                    case 2:
                        onClickEdit(tabLayout);
                        break;

                    case 0:
                        onClickDelete(tabLayout);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 2:
                        onClickEdit(tabLayout);
                        break;
                    case 0:
                        onClickDelete(tabLayout);
                        break;
                    default:
                        break;

                }

            }
        });



    }

    public void onClickAdd(View view){
        Intent intent = new Intent(this, InstrumentActivity.class);
        startActivity(intent);


    }

    public void onClickDelete(View view){

        if (Global.isPartSelected()){
            int position = Global.partSelect;
            ArrayList<Model> modelArray = ModelArray.getmodels();

            modelArray.remove(position);
            PartitionX partitionX = Global.getPartition();
            partitionX.removePart(position);
            Intent intent = new Intent(this, EditionActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            System.err.println("Select a Part");
        }

    }

    public void onClickEdit(View view){

        if (Global.isPartSelected()){
            Intent intent = new Intent(this, TouchActivity.class);
            startActivity(intent);

            //Mdrr je sais meme pas pq ca marche j'ai pas fait exprès

            int position = Global.partSelect;
            PartitionX partitionX = Global.getPartition();
            ArrayList<Model> modelArray = ModelArray.getmodels();

            for (int i = 0; i<partitionX.getPartsX().size();i++){
                Model model = new Model();
                modelArray.add(model);

            }



        }
        else{
            System.err.println("Select a Part");
        }
    }


    public void onClickPlay(View view){
        // Charger les notes dans la partition



    }


}
