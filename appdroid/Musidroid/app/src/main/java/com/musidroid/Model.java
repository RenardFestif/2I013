package com.musidroid;

import java.util.ArrayList;
import java.util.ListIterator;

public class Model {
    ArrayList<Position> xys;

    public Model() {
        xys = new ArrayList<Position>();
    }

    public void addRemove(int x, int y) {
        for(int i=0;i<xys.size();i++){
            if(xys.get(i).getX()==x && xys.get(i).getY()==y) {
                xys.remove(i);
                return;
            }
        }
        xys.add(new Position(x,y));
    }


    public ArrayList<Position> getArray (){
        return xys;
    }


}
