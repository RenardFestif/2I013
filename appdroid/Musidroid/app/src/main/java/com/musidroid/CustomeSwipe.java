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


public class CustomeSwipe extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList <String> names = new ArrayList<>();
    TextView textViewPrev;

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
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));

        //Selection partie Menu Slide
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(!Global.isPartSelected()){
                  Global.setPartSelect(position);
                  textView.setBackgroundResource(R.color.colorBrokenWhite);
                  textViewPrev = textView;

                  System.out.println(Global.partSelect);
              }
              else if (Global.partSelect == position){
                  textView.setBackgroundResource(R.color.colorWhite);
                  Global.unSelect();
                  System.out.println(Global.partSelect);
              }
              else if (Global.partSelect != position && Global.isPartSelected()){
                  textView.setBackgroundResource(R.color.colorBrokenWhite);
                  Global.setPartSelect(position);
                  unselect(textViewPrev);
                  textViewPrev = textView;
                  System.out.println(Global.partSelect);
              }
              
              //  if(Global.isPartSelected() && Global.partSelect != position){
              //      Global.setPartSelect(position);
              //      textView.setBackgroundResource(R.color.colorBrokenWhite);
              //      //textViewPrev = textView;
              //  }
              //  else if(Global.isPartSelected() &&  Global.partSelect == position){
              //      Global.unSelect();
              //      unselect(textView);
              //  }
              //  else if (!Global.isPartSelected() &&)
              //  else {
              //      Global.setPartSelect(position);
              //      unselect(textViewPrev);
              //
              //  }


            }
        });

        container.addView(view);
        return view;
    }

    public void unselect(TextView textView){
        textView.setBackgroundResource(R.color.colorWhite);
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
