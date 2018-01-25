
package maintest;

import java.io.File;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.MidiFile2I013;
import l2i013.musidroid.util.NoteName;
import musidroid.Partition;

/* @author The Mighty Jörmungandr */
public class TestMidi {
    public static void main(String[] args){
        
        //mettre le chemin dans le constructeur
        File f = new File("");
                
        Partition p = new Partition(300);
        
        p.addPart(InstrumentName.VIOLIN, 6);
        p.addNote(0, 0, NoteName.MI, 1);
        p.addNote(0, 1, NoteName.MI, 1);
        p.addNote(0, 2, NoteName.MI, 2);
        p.addNote(0, 4, NoteName.DO, 1);
        p.addNote(0, 5, NoteName.MI, 1);
        p.addNote(0, 6, NoteName.SOL, 1);
        
        p.addPart(InstrumentName.VOICE_OOHS, 5);
        p.addNote(1, 8, NoteName.SOL, 2);
        
        MidiFile2I013.write(f, p);
    }
}
