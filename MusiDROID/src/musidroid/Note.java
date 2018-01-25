package musidroid;

import exceptions.NegatifException;
import exceptions.NegatifOrEqualException;
import l2i013.musidroid.util.NoteName;;

public class Note {
	
	private int t;
	private NoteName n;
	private int d;
	
	public Note(int t, NoteName n, int d){
		if( t < 0) {
			try {
				throw new NegatifException();
			} catch (NegatifException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}
		if( d <= 0) {
			try {
				throw new NegatifOrEqualException();
			} catch (NegatifOrEqualException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}
		this.t = t;
		this.n = n;
		this.d = d;
	}
	
	public int getInstant(){ return t; }
	
	public NoteName getName(){ return n; }
	
	public int getDuration(){ return d; }
	
}
