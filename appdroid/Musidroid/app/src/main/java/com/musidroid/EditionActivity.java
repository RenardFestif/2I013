package com.musidroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.io.File;
import java.util.ArrayList;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.MidiFile2I013;
import model.Global;
import model.extended.PartitionX;
import musidroid.Note;
import musidroid.Partition;


public class EditionActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomeSwipe customeSwipe;
    TheApplication app;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Intent créee mis a jour dans global
        Global.editionActivity = this.getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);

        //Mis au point du mode fullScreen Imerssif
        Global.fullScreenCall(this);

        viewPager= (ViewPager) findViewById(R.id.CarousselViewPager);
        customeSwipe = new CustomeSwipe(this);
        viewPager.setAdapter(customeSwipe);

        Toast.makeText(EditionActivity.this, "Swipe à droite ou à gauche pour changer de partie Instrumental :D", Toast.LENGTH_LONG).show();

        //Menu d'édition
        final TabLayout tabLayout =(TabLayout) findViewById(R.id.menuTabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    //Position icone + => Add partie
                    case 0:
                        onClickAdd(tabLayout);
                        break;
                    // Select une Partie
                    case 1:
                        onClickEdit(tabLayout);
                        break;
                    case 2:
                        onClickSetTempo(tabLayout);
                        break;
                    case 3:
                        onClickSetInstru(tabLayout);
                        break;

                    case 4:
                        onClickSave(tabLayout);
                        break;

                    case 5 :
                        onClickDelete(tabLayout);
                        break;
                    default:
                        Toast.makeText(EditionActivity.this, "A toi de jouer l'artiste !", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            //Identique à tabSelected
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        onClickAdd(tabLayout);
                        break;
                    case 1:
                        onClickEdit(tabLayout);
                        break;
                    case 2:
                        onClickSetTempo(tabLayout);
                        break;

                    case 3:
                        onClickSetInstru(tabLayout);
                        break;

                    case 4:
                        onClickSave(tabLayout);
                        break;

                    case 5 :
                        onClickDelete(tabLayout);
                        break;
                    default:
                        Toast.makeText(EditionActivity.this, "A toi de jouer l'artiste !", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    //Permet d'ajouter une partie instrumentale
    public void onClickAdd(View view){
        Intent intent = new Intent(this, InstrumentActivity.class);
        Global.unSelect();
        startActivity(intent);
    }


    public void onClickDelete(View view){
        if (Global.isPartSelected()){
            int position = Global.partSelect;
            ArrayList<Model> modelArray = ModelArray.getmodels();


            // on charge un nouveau model calqué depuis la partionX pour evité certaines erreurs
            // (Nottament quand on delete une partie avant d'avoir cliquer une fois sur EDIT)

            PartitionX partitionX = Global.getPartition();

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

            if (partitionX.getSize() == 0){
                Toast.makeText(EditionActivity.this, "Euhh... rien à supprimer", Toast.LENGTH_LONG).show();     //Toast dialog
            }
            modelArray.remove(position);
            partitionX.removePart(position);
            recreate();
            Global.unSelect();
        }
        else
            Toast.makeText(EditionActivity.this, "sélectionnez une partie instrumental :D", Toast.LENGTH_LONG).show();      //Toast dialog

    }

    public void onClickEdit(View view){
        if (Global.isPartSelected()){
            Intent intent = new Intent(this, TouchActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(EditionActivity.this, "sélectionnez une partie instrumental :D", Toast.LENGTH_LONG).show();
        }
    }


    public void onClickPlay(View view) {
        Partition p = Global.getPartition();
        if (p.getSize() == 0){
            Toast.makeText(EditionActivity.this, "Difficile de jouer un morceau sans partition ;)", Toast.LENGTH_LONG).show();
            return;
        }
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
            deleteFile("tmp.mid");
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

        public void onClickSetInstru (View v){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditionActivity.this);
            View tView = getLayoutInflater().inflate(R.layout.dialogue_instru, null);



            LinearLayout sv = (LinearLayout) tView.findViewById(R.id.checkboxLayoutDial);


            for (final InstrumentName instru : InstrumentName.values()){
                final CheckBox cb = new CheckBox(this);
                cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                cb.setText(instru.toString());
                cb.setId(instru.getNum());
                sv.addView(cb);
                cb.isClickable();
                cb.setOnClickListener(checkBoxListener); //{
         //           @Override
         //           public void onClick(View view) {
         //
         //               if (!Global.isPartSelected()){
         //                   Toast.makeText(EditionActivity.this, "sélectionnez une partie instrumentale :D", Toast.LENGTH_LONG).show();
         //               }
         //               else {
         //                   Global.partitions.setInstrumentPartX(Global.partSelect, instru);
         //
         //               }
         //
         //           }
         //       });



            }
            mBuilder.setView(tView);
            dialog = mBuilder.create();



            dialog.show();

        }

    private View.OnClickListener checkBoxListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox cb = (CheckBox)v ;
            if (!Global.isPartSelected()){
                        Toast.makeText(EditionActivity.this, "sélectionnez une partie instrumentale :D", Toast.LENGTH_LONG).show();
                        ((CheckBox) v).setChecked(false);
                    }
                    else {
                        Global.partitions.setInstrumentPartX(Global.partSelect, InstrumentName.valueOf(cb.getText().toString()));
                        dialog.dismiss();

                    }
        }
    };

        public void onClickExit(View view){
            Global.unSelect(); //Remet la position du custom swipe a -1

            //Activity fini donc intent fini mis a jour dans global
            Global.editionActivity = null;

            Intent intent = new Intent(this, Accueil2.class);
            //Flag pôur faire revenir au top l'acceuil
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


}
