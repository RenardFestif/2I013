package com.musidroid;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;

import model.Global;
import model.extended.PartitionX;


public class EditionActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomeSwipe customeSwipe;



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

        final TabLayout tabLayout =(TabLayout) findViewById(R.id.menuTabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){

                    //Position icone + => Add partie
                    case 1:
                        onClickAdd(tabLayout);
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

            }
        });



    }

    public void onClickAdd(View view){
        Intent intent = new Intent(this, InstrumentActivity.class);
        startActivity(intent);


    }

    public void onClickDelete(View view){

    }

    public void onClickEdit(View view){

    }
}
