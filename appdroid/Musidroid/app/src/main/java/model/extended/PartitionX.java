package model.extended;

import java.util.ArrayList;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import model.Global;
import musidroid.InstrumentPart;
import musidroid.Partition;


public class PartitionX extends Partition{
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

    // Utiliser dans la creation a partir du XML car decalage d'indentation
    public void addPartX(InstrumentName name, int o, String namepart){
        super.addPart(name, o);
        instrumentPartX.add(new InstrumentPartX(name,o,namepart));
    }

    public void removeNote(int i, int t, NoteName n){
        InstrumentPart instrumentPart = super.getPart(i);
        instrumentPart.removeNote(t, n);
        instrumentPartX.get(i).removeNote(t,n);
    }


    @Override
    public void removePart(int i) {
        super.removePart(i);
        instrumentPartX.remove(i);
    }

    @Override
    public InstrumentPartX getPart(int i) {
        if (instrumentPartX.isEmpty()) {
            return null;
        }
        return instrumentPartX.get(i);
    }


    public void resetPartsNote(){
        for (int i=0; i<this.getPartsX().size(); i++){
            InstrumentPart part = this.getPart(i);
            part.getNotes().clear();
        }
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

    public void setInstrumentPartX (int index, InstrumentName instru){
        super.getPart(index).setInstrument(instru);
        instrumentPartX.get(index).setInstrument(instru);
        return;
    }

    @Override
    public String toString() {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n<Partition tempo=\""+super.getTempo()+"\">";
        ArrayList<InstrumentPartX> instrumentPartXArrayList = Global.getPartition().getPartsX();
        InstrumentPartX instrumentPartX = null;
        for(int i=0;i<instrumentPartXArrayList.size();i++) {
            instrumentPartX = instrumentPartXArrayList.get(i);
            s += "\n\t" + instrumentPartX.toString();
        }

        s+="\n</Partition>";
        return s;
    }
}
