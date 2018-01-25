package maintest;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import musidroid.Partition;

public class TestPartition {
	
	public static void main(String[] args) {
		
		Partition p1 = new Partition(2);
		Partition p2 = new Partition(3);
		Partition p3 = new Partition(4);
		
		System.out.println(p1.getTempo());
		p3.setTempo(1);
		System.out.println(p3.getTempo());
		
		System.out.println(p2.addPart(InstrumentName.AGOGO, 2));
		System.out.println(p2.addPart(InstrumentName.ACCORDION, 5));
		
		p2.addNote(0, 5, NoteName.RE, 2);
		
		System.out.println(p2.getSize());
		System.out.println(p2.getPart(0));
		
		System.out.println(p2.addPart(InstrumentName.ACCORDION, -10));
		
	}

}
