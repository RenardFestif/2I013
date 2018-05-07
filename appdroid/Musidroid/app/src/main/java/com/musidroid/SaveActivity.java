package com.musidroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import model.Global;

public class SaveActivity extends AppCompatActivity {

    TheApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        /*Mode Immersif*/
        Global.fullScreenCall(this);

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


    public void onClickSavePart(View view){
        //Creation d'un String avec le code XML
        String xmlText = Global.toXML();

        //Recup du nom de la partition => nom de la sauvegarde
        EditText nameView = findViewById(R.id.namePartitionEditText);
        String namePartition = nameView.getText().toString();

        //Creation du fichier XML
        FileOutputStream oc = null;
        try {
            oc = openFileOutput(namePartition+".xml", Context.MODE_PRIVATE);
            oc.write(xmlText.getBytes());
            oc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        finish();

    }

    public void onClickAbord (View view){
        finish();
    }




}
