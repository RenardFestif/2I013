package com.musidroid;

import java.util.ArrayList;
import java.util.ListIterator;

import l2i013.musidroid.util.NoteName;
import model.Global;
import model.extended.InstrumentPartX;
import model.extended.PartitionX;

public class Model {
    ArrayList<Position> xys;

    public Model() {
        xys = new ArrayList<Position>();
    }

    public void addRemove(int x, int y, int caseX, int caseY, int d, int offset, float pas) {


        PartitionX partitionX = Global.getPartition();
        int position = Global.partSelect;

        NoteName noteName = null;                           //nom de la note

        switch (caseY){
            case 0:
                noteName = NoteName.SI;
                break;
            case 1:
                noteName = NoteName.LADIESE;
                break;
            case 2:
                noteName = NoteName.LA;
                break;
            case 3:
                noteName = NoteName.SOLDIESE;
                break;

            case 4:
                noteName = NoteName.SOL;
                break;

            case 5:
                noteName = NoteName.FADIESE;
                break;

            case 6:
                noteName = NoteName.FA;
                break;


            case 7 :
                noteName = NoteName.MI;
                break;


            case 8 :
                noteName = NoteName.REDIESE;
                break;

            case 9 :
                noteName = NoteName.RE;
                break;

            case 10 :
                noteName = NoteName.DODIESE;
                break;

            case 11 :
                noteName = NoteName.DO;
                break;

            default:
                break;
        }
        assert noteName != null;

        for(int i=0;i<xys.size();i++){
            if(xys.get(i).getX()+offset*pas==x && xys.get(i).getY()==y) {
                xys.remove(i);
                partitionX.removeNote(position,caseX+offset, noteName);

                return;
            }
        }
        xys.add(new Position(x+(int)(offset*pas),y,d));
        partitionX.addNote(position, caseX+offset, noteName, d);
        System.out.println("Offset "+offset);
        System.out.println(caseX+offset);

    }


    public ArrayList<Position> getArray (){
        return xys;
    }


    public void reset(){
        xys.clear();
    }


}
