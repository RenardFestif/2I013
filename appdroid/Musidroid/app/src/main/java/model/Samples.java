package model;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.NoteOff;


import com.leff.midi.util.MidiEventListener;
import com.leff.midi.MidiTrack;

import com.leff.midi.util.MidiProcessor;
import l2i013.musidroid.util.NoteName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.extended.PartitionX;




public class Samples implements MidiEventListener {

    private static int tracks;
    private static ArrayList<Integer> created;
    private static boolean noteOn;
    private String mLabel;
    public static final NoteName[] NOTE_NAMES = {NoteName.DO, NoteName.DODIESE, NoteName.RE, NoteName.REDIESE, NoteName.MI,
                                                NoteName.FA, NoteName.FADIESE, NoteName.SOL, NoteName.SOLDIESE, NoteName.LA,
                                                NoteName.LADIESE, NoteName.SI};



    public Samples(String label) {
        this.mLabel = label;
    }

    @Override
    public void onStart(boolean fromBeginning) {
        if(fromBeginning) {
            System.out.println(this.mLabel + " Started");
        } else {
            System.out.println(this.mLabel + " resumed");
        }

    }

    @Override
    public void onEvent(MidiEvent midiEvent, long l) {

        NoteOn note = (NoteOn) midiEvent;

        //Si InstrumentPart est déjà créee
        if(created.contains((Integer) note.getChannel())){


        }
        int notename = note.getNoteValue()%12;

        int octave = (note.getNoteValue()/12)-1;

        System.out.println(note.getChannel()+" Instant : "+note.getTick()+" // "+"Valeur : "+NOTE_NAMES[notename]+ " // " + " Octave : "+ octave);


    }

    @Override
    public void onStop(boolean finished) {
    if(finished) {
            System.out.println(this.mLabel + " Finished");
        } else {
        System.out.println(this.mLabel + " paused");
        }

    }

        public static void read(String filename) {
        MidiFile midi = null;

            try {
                midi = new MidiFile(new File(filename));
            } catch (IOException var7) {
                System.err.println(var7);
                return;
            }

            //Nombre de Part minimum
            System.out.println(midi.getTrackCount());
            tracks = midi.getTrackCount();

            ArrayList<MidiTrack> miditracks = midi.getTracks();

            //tempo
            System.out.println(midi.getResolution());

            //On cree la partition
            PartitionX partitionX = new PartitionX(midi.getResolution());


            MidiProcessor processor = new MidiProcessor(midi);
            
            Samples ep = new Samples("Individual Listener");
            processor.registerEventListener(ep, NoteOn.class);


            //Permet de mettre les parties crées + set param de base
            created = new ArrayList<>();
            noteOn = false;

            processor.start();


        }
}




