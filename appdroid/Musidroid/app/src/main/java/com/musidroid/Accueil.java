package com.musidroid;



import android.content.Intent;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Chronometer;

import java.io.File;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.MidiFile2I013;
import l2i013.musidroid.util.NoteName;
import musidroid.Partition;

import model.Global;


public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Global.fullScreenCall(this);
        welcome();



    }

    @Override
    protected void onResume() {
        super.onResume();
        welcome();
    }

    public void welcome(){

        Chronometer chronometer = (Chronometer) findViewById(R.id.chroo);
        final long startTime = System.currentTimeMillis();
        chronometer.start();

        /**<3 MICHEL SARDOU <3**/
        Partition intro = new Partition(210);
        intro.addPart(InstrumentName.TRUMPET, 5);


        /**Voix principale**/
        intro.addNote(0,0, NoteName.SOL,2);
        intro.addNote(0,2, NoteName.REDIESE,1);
        intro.addNote(0,3, NoteName.SOL,2);
        intro.addNote(0,5, NoteName.REDIESE,1);
        intro.addNote(0,6, NoteName.SOL,2);
        intro.addNote(0,8, NoteName.REDIESE,1);
        intro.addNote(0,9, NoteName.SOLDIESE,1);
        intro.addNote(0,10, NoteName.SOL,1);
        intro.addNote(0,11, NoteName.FA,1);
        intro.addNote(0,12, NoteName.SOL,2);
        intro.addNote(0,14, NoteName.REDIESE,1);
        intro.addNote(0,15, NoteName.SOL,2);
        intro.addNote(0,17, NoteName.REDIESE,1);
        intro.addNote(0,18, NoteName.SOL,2);
        intro.addNote(0,20, NoteName.REDIESE,1);
        intro.addNote(0,21, NoteName.SOLDIESE,1);
        intro.addNote(0,22, NoteName.SOL,1);
        intro.addNote(0,23, NoteName.FA,1);
        intro.addNote(0,24, NoteName.REDIESE,2);
        intro.addNote(0,26, NoteName.DO,1);
        intro.addNote(0,27, NoteName.REDIESE,2);
        intro.addNote(0,29, NoteName.DO,1);
        intro.addNote(0,30, NoteName.REDIESE,2);
        intro.addNote(0,32, NoteName.DO,1);
        intro.addNote(0,33, NoteName.REDIESE,1);
        intro.addNote(0,34, NoteName.RE,1);
        intro.addNote(0,35, NoteName.REDIESE,1);
        intro.addNote(0,36, NoteName.FA,4);


        /**Voix 2**/

        // intro.addPart(InstrumentName.GUNSHOT, 4);
        //
        // intro.addNote(1, 0, NoteName.FA,1);
        // intro.addNote(1, 2, NoteName.FA,1);
        // intro.addNote(1, 4, NoteName.FA,1);
        // intro.addNote(1, 6, NoteName.FA,1);
        // intro.addNote(1, 8, NoteName.REDIESE,1);
        // intro.addNote(1, 10, NoteName.REDIESE,1);
        // intro.addNote(1, 12, NoteName.REDIESE,1);
        // intro.addNote(1, 14, NoteName.REDIESE,1);
        // intro.addNote(1, 16, NoteName.DODIESE,1);
        // intro.addNote(1, 18, NoteName.DODIESE,1);
        // intro.addNote(1, 20, NoteName.DODIESE,1);
        // intro.addNote(1, 22, NoteName.DODIESE,1);
        // intro.addNote(1, 24, NoteName.LADIESE,1);
        // intro.addNote(1, 28, NoteName.LADIESE,1);
        // intro.addNote(1, 30, NoteName.LADIESE,1);
        // intro.addNote(1, 32, NoteName.LADIESE,1);
        // intro.addNote(1, 34, NoteName.FA,1);
        // intro.addNote(1, 36, NoteName.FA,1);
        //





        TheApplication app = (TheApplication)(this.getApplicationContext());
        MidiFile2I013.write(new File(app.getFilesDir(), "tmp.mid"), intro);

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

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if (System.currentTimeMillis()-startTime >= 11000){

                    nextView(chronometer);
                }
            }
        });





    }

    public void nextView(View view){

        Intent intent = new Intent(this, Accueil2.class);
        startActivity(intent);

    }


}
