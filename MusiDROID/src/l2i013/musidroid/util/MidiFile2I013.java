package l2i013.musidroid.util;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import musidroid.InstrumentPart;
import musidroid.Note;
import musidroid.Partition;

public class MidiFile2I013 {

    public static final Integer OCTAVE_NUM = 12;
    public static final Integer OCTAVE_DELTA = -1;
    public static final int VELOCITY = 127; //max

    MidiFile midiFile;

    //public MidiFile2I013(Partition p) {
    static MidiFile midiFileOfPartition(Partition p) {

	    ArrayList<MidiTrack> tracks = new ArrayList<>();
        MidiTrack tempoTrack = new MidiTrack();
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo t = new Tempo();
        t.setBpm(p.getTempo()); //t.setBpm(400); //

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(t);
        tracks.add(tempoTrack);

	int channel = 0;
        for (InstrumentPart ip : p.getParts()) {
	    MidiTrack track = new MidiTrack();
	    track.insertEvent(new ProgramChange(0, channel, ip.getInstrument().getNum()));
	    for (Note note : ip.getNotes()) {
		//int pitch = note.getName().getNum() + (OCTAVE_NUM * (ip.getOctave() + OCTAVE_DELTA));
            int pitch = note.getName().getNum() + (OCTAVE_NUM * (ip.getOctave() + OCTAVE_DELTA));
		track.insertNote(channel, pitch, VELOCITY,
				 note.getInstant() * p.getTempo(),
				 note.getDuration() * p.getTempo());
	    }
            tracks.add(track);
	    channel++;
        }
	//midiFile = new MidiFile(p.getResolution(), tracks);
	//midiFile = new MidiFile(p.getTempo(), tracks);
        return new MidiFile(p.getTempo(), tracks);
    }

    public static void write(File f, Partition p) {

	try {
	    //midiFile.writeToFile(f);
        midiFileOfPartition(p).writeToFile(f);
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
