package model;


import java.util.ArrayList;

import model.extended.PartitionX;


/**
 * Created by Jörmungandr on 08/02/2018.
 */

public class Global {
    public static PartitionX partitions ;
    public static int partSelect = -1;




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


}
