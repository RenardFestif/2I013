package model.extended;

import java.util.ArrayList;

import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import model.Global;
import musidroid.InstrumentPart;
import model.extended.*;
import musidroid.Note;
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

    @Override
    public void addNote(int t, NoteName n, int d) {
        super.addNote(t, n, d);

    }


    @Override
    public String toString() {
        String s = "<InstrumentPart Name="+this.getName()+" Octave="+super.getOctave()+" Instrument="+super.getInstrument();
       ArrayList<Note> notes = (ArrayList<Note>) (this.getNotes());
       NoteX notex = null;
       Note note = null;
       for(int i=0;i<notes.size();i++){
           note = notes.get(i);
           notex = new NoteX(note.getInstant(), note.getName(),note.getDuration());
           s+= "\n\t\t"+notex.toString();
       }
        s+="\n\t/>";
        return s;
    }
}
