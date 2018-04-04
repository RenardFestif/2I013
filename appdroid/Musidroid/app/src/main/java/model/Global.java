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

    /*Dans touch Board = Si le le doigt a glisse*/
    public static boolean moved = false;




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

    // ATTENTION A SUP LE TAMPON
    public static void reset(){
        partitions = null;
    }
}
