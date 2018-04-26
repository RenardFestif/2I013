package model;


import java.util.ArrayList;

import model.extended.PartitionX;


/**
 * Created by Jörmungandr on 08/02/2018.
 */


// Variables Global à l'application
public class Global {
    public static PartitionX partitions ;
    public static int partSelect = -1;

    /*Pas de la surfaceview pour retrouver les coordonées en brut*/
    public static float pas;

    /*Dans touch Board = Si le le doigt a glisse*/
    public static boolean moved = false;

    /*Coefficient de deplacement pour la seekbar */
    public static int coeffdep = 4;

    /*OffSet en terme de d'instant*/
    public static int offset;


    public static void addPartition(PartitionX p){
        partitions = p ;
    }

    public static PartitionX getPartition (){
        return partitions;
    }

    public static void setPartSelect (int position){
        partSelect = position;
    }

    public static void unSelect (){
        partSelect = -1;
    }

    public static boolean isPartSelected (){
        if (partSelect == -1)
            return false;
        return true;
    }

    public static String toXML(){
        return partitions.toString();
    }

}
