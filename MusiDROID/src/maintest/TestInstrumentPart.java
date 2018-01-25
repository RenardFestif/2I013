package maintest;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import musidroid.InstrumentPart;

public class TestInstrumentPart {
	
	public static void main(String[] args){
		
		InstrumentPart i1 = new InstrumentPart(InstrumentName.AGOGO, 0);
		InstrumentPart i2 = new InstrumentPart(InstrumentName.AGOGO, 10);
		InstrumentPart i3 = new InstrumentPart(InstrumentName.AGOGO, -1);
		
		System.out.println(i1);
		System.out.println(i2);
		System.out.println(i3);
		
		i1.setInstrument(InstrumentName.ACCORDION);
		i1.setOctave(2);
		
		System.out.println(i1);
		
		i2.addNote(1, NoteName.FA, 4);
		i2.addNote(1, NoteName.DO, 1);
		i2.addNote(4, NoteName.RE, 3);
		
		System.out.println(i2);
		
		i2.removeNote(1, NoteName.DO);
		
		System.out.println(i2);
		
	}
}
