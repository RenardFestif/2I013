package model.extended;

import l2i013.musidroid.util.InstrumentName;
import musidroid.InstrumentPart;
import musidroid.Partition;

/**
 * Created by Jörmungandr on 12/02/2018.
 */

public class InstrumentPartX extends InstrumentPart {
    private String name;

    public InstrumentPartX (InstrumentName instrumentName, int octave){
        super(instrumentName,octave);

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


}
