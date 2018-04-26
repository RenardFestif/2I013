package com.musidroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.Toast;

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

                    case 3:
                        onClickSetTempo(tabLayout);
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
            recreate();
            Global.unSelect();
        }
        else
            Toast.makeText(EditionActivity.this, "sélectionnez une partie instrumental :D", Toast.LENGTH_LONG).show();

    }

    public void onClickEdit(View view){

        if (Global.isPartSelected()){
            Intent intent = new Intent(this, TouchActivity.class);
            startActivity(intent);

            /**A chaque clique sur EDIT on charge un nouveau model calqué depuis la partionX**/

            PartitionX partitionX = Global.getPartition();
            ArrayList<Model> modelArray = ModelArray.getmodels();
            modelArray.clear();
            for (int i = 0; i<partitionX.getPartsX().size();i++){
                Model model = new Model();

                for (int j=0; j<partitionX.getPart(i).getNotes().size(); j++ ){
                    Note note = partitionX.getPart(i).getNotes().get(j);
                    int x = note.getInstant();
                    int duration = note.getDuration();
                    int y = note.getName().getNum();
                    model.addModel(x,y,duration);
                }
                modelArray.add(model);

            }



        }
        else{
            Toast.makeText(EditionActivity.this, "sélectionnez une partie instrumental :D", Toast.LENGTH_LONG).show();
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

            int tempo = Global.partitions.getTempo();
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditionActivity.this);
            View tView = getLayoutInflater().inflate(R.layout.dialog_tempo, null);

            final TextView textview =(TextView)tView.findViewById(R.id.text_dialog_bpm);
            textview.setText(tempo +" Bpm");
            mBuilder.setView(tView);

            SeekBar seekBar = (SeekBar) tView.findViewById(R.id.seekBarTempo);
            seekBar.setProgress(tempo);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int newtempo = progress;
                    textview.setText(newtempo+" Bpm");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Global.partitions.setTempo(seekBar.getProgress());
                }
            });
            final AlertDialog dialog = mBuilder.create();

            Button button = (Button) tView.findViewById(R.id.buton_dialog_tempo);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });





            dialog.show();
        }

        public void onClickSave(View view){
            Intent intent = new Intent(this, SaveActivity.class);
            startActivity(intent);
        }




        public void onClickExit(View view){
            Global.unSelect(); //Remet la position du custom swipe a -1
            finish();

        }
}
