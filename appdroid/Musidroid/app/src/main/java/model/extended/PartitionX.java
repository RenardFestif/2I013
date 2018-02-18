package model.extended;

import java.util.ArrayList;

import exceptions.OutZeroToTen;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import musidroid.InstrumentPart;
import musidroid.Partition;

/**
 * Created by Jörmungandr on 12/02/2018.
 */

public class PartitionX extends Partition {
    private ArrayList<InstrumentPartX> instrumentPartX;


    public PartitionX (int t){
        super(t);
        instrumentPartX = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return instrumentPartX.size();
    }

    @Override
    public int addPart(InstrumentName n, int o) {
        super.addPart(n, o);
        instrumentPartX.add(new InstrumentPartX(n, o));
        return getSize()-1;
    }

    @Override
    public void addNote(int i, int t, NoteName n, int d) {
        super.addNote(i, t, n, d);
        instrumentPartX.get(i).addNote(t,n,d);
    }

    @Override
    public void removePart(int i) {
        super.removePart(i);
        instrumentPartX.remove(i);
    }

    @Override
    public InstrumentPart getPart(int i) {
        if (instrumentPartX.isEmpty()) {
            return null;
        }
        return instrumentPartX.get(i);
    }


    public ArrayList<InstrumentPartX> getPartsX() {
        return instrumentPartX;
    }


    public String getName(int i){
        return instrumentPartX.get(i).getName();
    }

    public void setName(int i, String name){
        instrumentPartX.get(i).setName(name);
    }


}
