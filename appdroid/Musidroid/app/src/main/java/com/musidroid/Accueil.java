package com.musidroid;



import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;




public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
       /* TextView tx = (TextView)findViewById(R.id.textAccueil);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Champagne & Limousines Bold.ttf");
        tx.setTypeface(custom_font);
*/


    }

    public void nextView(View view){
        Intent intent = new Intent(this, Accueil2.class);
        startActivity(intent);

    }


}
