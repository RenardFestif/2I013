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

        //if(d==1) {
            /*Debug*/
            System.out.println(x);
            System.out.println(x + (int) (offset * pas));
            /*Fin*/
            for (int i = 0; i < xys.size(); i++) {
                if (xys.get(i).getX() == x + offset * pas && xys.get(i).getY() == y) {
                    xys.remove(i);
                    partitionX.removeNote(position, caseX + offset, noteName);
                    return;
                }
            }
            xys.add(new Position(x + (int) (offset * pas), y, d));
            partitionX.addNote(position, caseX + offset, noteName, d);

    }
    /**Dans Edition Activity on recharge un nouveau model on le calque sur PartionX**/
    public void addModel(int x, int y, int duration){


        int yC=0 ;   //On remet les Y à l'endroit car Si=11 et on veut Si = 0


        switch (y){
            case 0:
                yC = 11;
                break;
            case 1:
                yC = 10;
                break;
            case 2:
                yC = 9;
                break;
            case 3:
                yC = 8;
                break;

            case 4:
                yC = 7;
                break;

            case 5:
                yC = 6;
                break;

            case 6:
                yC = 5;
                break;


            case 7 :
                yC = 4;
                break;

            case 8 :
                yC = 3;
                break;

            case 9 :
                yC = 2;
                break;

            case 10 :
                yC = 1;
                break;

            case 11 :
                yC = 0;
                break;

            default:
                break;
        }

        int xC = (int) (x * Global.pas + Global.pas / 2);
        int realYC = (int) (yC * Global.pas + Global.pas / 2);
        xys.add(new Position(xC,realYC,duration));
    }


    public ArrayList<Position> getArray (){
        return xys;
    }


    public void reset(){
        xys.clear();
    }


}
