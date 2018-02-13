package model;


import model.extended.PartitionX;


/**
 * Created by Jörmungandr on 08/02/2018.
 */

public class Global {
    public static PartitionX partitions ;



    public static void addPartition(PartitionX p){
        partitions = p ;
    }

    public static PartitionX getPartition (){
        return partitions;
    }

}
