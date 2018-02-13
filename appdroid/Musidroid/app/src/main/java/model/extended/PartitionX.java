package model.extended;

import musidroid.Partition;

/**
 * Created by Jörmungandr on 12/02/2018.
 */

public class PartitionX extends Partition {
    private String name;

    public PartitionX ( int tempo ){
        super(tempo);
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


}
