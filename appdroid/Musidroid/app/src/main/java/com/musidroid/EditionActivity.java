package com.musidroid;

import android.app.Application;
import android.content.Intent;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;


import java.io.File;
import java.util.ArrayList;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.MidiFile2I013;
import l2i013.musidroid.util.NoteName;
import model.Global;
import model.extended.InstrumentPartX;
import model.extended.PartitionX;
import musidroid.InstrumentPart;
import musidroid.Note;
import musidroid.Partition;


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

                    case 3:
                        onClickSetTempo(tabLayout);
                        break;

                    case 4:
                        onClickSave(tabLayout);
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
        finish();


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


    public void onClickPlay(View view) {

        //Recup de la partition
        Partition p = Global.getPartition();


            TheApplication app = (TheApplication)(this.getApplicationContext());
            MidiFile2I013.write(new File(app.getFilesDir(), "tmp.mid"), p);

            MediaPlayer mPlayer = new MediaPlayer();
            MediaPlayer.OnPreparedListener mPrepared =
                    new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer playerM) {
                        }
                    };
            mPlayer.setOnPreparedListener(mPrepared);
            try {
                File f = new File(app.getFilesDir(), "tmp.mid");
                String path = f.getPath();
                mPlayer.setDataSource(path);
                mPlayer.setLooping(false);
                mPlayer.prepare();
                if (!mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            } catch (Exception e) {
            }

        }


        public void onClickSetTempo(View view){

        }

        public void onClickSave(View view){
            Intent intent = new Intent(this, SaveActivity.class);
            startActivity(intent);
        }



        // Err Fermeture
        public void onClickExit(View view){

            finish();

        }
}
