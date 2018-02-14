package com.musidroid;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jörmungandr on 14/02/2018.
 */

public class CustomeSwipe extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String [] test = {"bonjour", "En revoir", "Salut", "Wazaaah"};

    public CustomeSwipe(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return test.length;
        
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_swipe,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textNamePart);
        textView.setText(test[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
