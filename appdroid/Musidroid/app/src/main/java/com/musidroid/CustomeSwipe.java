package com.musidroid;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;

import model.Global;
import model.extended.PartitionX;

/**
 * Created by Jörmungandr on 14/02/2018.
 */

public class CustomeSwipe extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList <String> names = new ArrayList<>();

    public CustomeSwipe(Context context){
        this.context = context;
        nameUpdate();
    }

    @Override
    public int getCount() {
        return names.size();
        
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.custom_swipe,container,false);
        final TextView textView = (TextView) view.findViewById(R.id.textNamePart);
        textView.setText(names.get(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Global.isPartSelected()){
                    Global.unSelect();
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));

                }
                else {
                    Global.setPartSelect(position);
                    v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBrokenWhite));
                }

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    //Methode pas du tout Optimise Car update a chaque instance
    public void nameUpdate (){
        PartitionX partitionX = Global.getPartition();

        for (int i=0; i<partitionX.getSize(); i++){
            names.add(partitionX.getName(i));
        }
    }


}
