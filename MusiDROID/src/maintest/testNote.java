package maintest;

import musidroid.Note;
import l2i013.musidroid.util.NoteName;

public class testNote {

	public static void main (String [] args){
		
		Note n = new Note(-50, NoteName.DO, 2);
		Note n1 = new Note(0, NoteName.DO, 10);
		Note n2 = new Note(-10, NoteName.DO, -5);
		Note n3 = new Note(5, NoteName.DO, -5);
		
		System.out.println(n.getDuration());
		System.out.println(n2.getName());
		System.out.println(n1.getInstant());
		System.out.println(n3.getDuration());
		
	}
}
