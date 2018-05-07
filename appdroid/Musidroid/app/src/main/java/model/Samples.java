package model;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ChannelEvent;
import com.leff.midi.event.NoteOff;


import com.leff.midi.util.MidiEventListener;
import com.leff.midi.MidiTrack;

import com.leff.midi.util.MidiProcessor;
import l2i013.musidroid.util.NoteName;
import l2i013.musidroid.util.InstrumentName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import model.extended.PartitionX;




public class Samples implements MidiEventListener {

    private static final InstrumentName instrumentName = InstrumentName.ACOUSTIC_GRAND_PIANO;
    //private static final Boolean NOTE_ON = true;
    //private static final Boolean NOTE_OFF = false;



    private static int tracks;

    /*Index de la partieinstrumental*/
    //private static ArrayList<Integer> createdIndex;

    /*Compte le nombre d'apparition de la note (NoteOn et NoteOFF) si integer == 2 alors on ajoute la note*/
    private static ArrayList<HashMap<NoteName,Integer>> notes;

    /*Partition a créer*/
    static PartitionX partitionX;

    private static int tempo;


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
        int index = note.getChannel();
        

        //12 etant le nombre de notes diffferentes
        int notename = note.getNoteValue() % 12;
        //10 etant la division choisie pour l'octave
        int octave = (note.getNoteValue() / 10);
        //Instant actuel
        int instant = (int) note.getTick();

        System.out.println(NOTE_NAMES[notename]+" "+octave+" "+instant+ " "+index);



        //Si InstrumentPart est déjà créee
        if (notes.size()>=index+1) {

            if (notes.get(index).containsKey(NOTE_NAMES[notename])){
                int oldInstant = notes.get(index).get(NOTE_NAMES[notename]);

                //Permet de rejouer cette note à un instant different
                notes.get(index).remove(NOTE_NAMES[notename]);

                int duree = ((int)(note.getTick()) - oldInstant)/tempo;

                /*Ici on ajout dans la partition*/
                partitionX.addNote(index, (int)note.getTick()/tempo-duree, NOTE_NAMES[notename],duree);


            }
            else{

                notes.get(index).put(NOTE_NAMES[notename], instant);
            }

        } else {




                notes.add(index,new HashMap<NoteName, Integer>());
                notes.get(index).put(NOTE_NAMES[notename], instant);

                partitionX.addPartX(instrumentName,octave,"Part : " +String.valueOf(index));

                /*Creation d'une partie*/
            //}


        }
    }

    @Override
    public void onStop (boolean finished) {
    if(finished) {

           // Partition créee est mise a jour dans global
           Global.partitions = partitionX;

           Global.isWriting = false;


        } else {
        System.out.println(this.mLabel + " paused");
        }

    }



    public static void read(String filename) {
        Global.isWriting = true;
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
        tempo = midi.getResolution();
        System.out.println(tempo);

        //On cree la partition
        partitionX = new PartitionX(midi.getResolution());



        MidiProcessor processor = new MidiProcessor(midi);

        Samples ep = new Samples("Individual Listener");
        processor.registerEventListener(ep, NoteOn.class);



        //Permet de mettre les parties crées + set param de base
        //createdIndex = new ArrayList<>();
        notes = new ArrayList<>();


        processor.start();


    }
}




