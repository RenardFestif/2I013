package musidroid;

import java.util.ArrayList;

import exceptions.OutZeroToTen;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;

public class Partition {
	
	private int t;                                  //t correspond à la cadence
	private ArrayList<InstrumentPart> instruments;
	
	public Partition(int t){
		this.t = t;
		instruments = new ArrayList<>();
	}
	
	public int getTempo(){ return t; }
	
	public InstrumentPart getPart(int i){
		if (instruments.isEmpty()){
			return null;
		}
		return instruments.get(i);
	}
	
	public ArrayList<InstrumentPart> getParts(){
		return instruments;
	}
	
	public int getSize(){
		return instruments.size(); 
	}
	
	public void setTempo(int t){
		this.t = t; 
	}
	
	public int addPart(InstrumentName n, int o){
		if (o<0 || o>10){ 
			try {
				throw new OutZeroToTen();
			} catch (OutZeroToTen e) {
				e.getMessage();
				e.printStackTrace();
			}
			return -1;
		}
		InstrumentPart ajout = new InstrumentPart(n, o);
		instruments.add(ajout);
		return getSize()-1;
	}
	
	public void addNote (int i, int t, NoteName n, int d ){
		instruments.get(i).addNote(t, n, d);
	}
	
	public void removePart(int i){
		instruments.remove(i);
	}
}
