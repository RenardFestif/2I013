package com.musidroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import l2i013.musidroid.util.InstrumentName;
import model.Global;


public class InstrumentActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_INSTRU = "com.musidroid.INSTRU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);

        //Mis au point du mode fullScreen Imerssif
        Global.fullScreenCall(this);

        LinearLayout sv = (LinearLayout) findViewById(R.id.checkboxLayout);

        for (InstrumentName instru : InstrumentName.values()){
            final CheckBox cb = new CheckBox(this);
            cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cb.setText(instru.toString());
            cb.setId(instru.getNum());
            sv.addView(cb);
            cb.isClickable();

            cb.setOnClickListener(this);



        }

    }

    public void preView(View view){
        this.finish();
    }


    @Override
    public void onClick(View v) {


        Intent intent = new Intent(this, OctaveActivity.class);


        CheckBox cb = (CheckBox) v;
        String instru = cb.getText().toString();
        System.out.println(instru);
        intent.putExtra(EXTRA_INSTRU, instru);
        cb.setChecked(false);
        startActivity(intent);

        finish();

    }
}
