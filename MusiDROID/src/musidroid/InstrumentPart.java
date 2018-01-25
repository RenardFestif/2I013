package musidroid;

import java.util.ArrayList;
import exceptions.OutZeroToTen;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;

public class InstrumentPart {

    private ArrayList<Note> notes;
    private InstrumentName n;
    private int o;

    public InstrumentPart(InstrumentName n, int o){
            if(o<0 || o>10) {
                    try {
                            throw new OutZeroToTen();
                    } catch (OutZeroToTen e) {
                            System.err.println("[Erreur] Octave non comprise entre 0 et 10");
                            return;
                    }
            }
            this.n = n;
            this.o = o;
            notes = new ArrayList<>();
    }

    public InstrumentName getInstrument(){ return n; }
    public int getInstrumentNum(){ return n.getNum(); }
    public int getOctave(){ return o; }

    public ArrayList<Note> getNotes(){ //ERROR
            if(notes.isEmpty() || notes.size() == 1){
                    return notes;
            }
            else{
                    ArrayList<Note> copie = notes;
                    ArrayList<Note> ret = new ArrayList<>();
                    for(int i=0;i<copie.size();i++){
                            Note tmp = notes.get(0);
                            for(int j=1;j<copie.size();j++){
                                    if(copie.get(j).getInstant() < tmp.getInstant()){
                                            tmp = copie.get(j);
                                    }
                                    if(copie.get(j).getInstant() == tmp.getInstant()){
                                            if(copie.get(j).getName().getNum() < tmp.getName().getNum()){
                                                    tmp = copie.get(j);
                                            }
                                    }
                                    else {
                                            return notes;
                                    }
                            }
                            ret.add(tmp);
                            copie.remove(tmp);
                    }
                    ret.add(copie.get(copie.size()-1));
                    return ret;
            }		
    }


    public void setInstrument(InstrumentName n){ this.n = n; }

    public void setOctave(int o){ this.o = o; }

    public void addNote(int t, NoteName n, int d){
            Note note = new Note(t, n, d);
            notes.add(note);
    }

    /*Might be usefull later*/
    public void addNote(Note note){                                  
        notes.add(note);
    }

    public void removeNote(int t, NoteName n){
            for(int i=0; i<notes.size(); i++){
                    if((notes.get(i).getInstant() == t) && (notes.get(i).getName() == n))
                            notes.remove(i);
            }
    }


    public String toString(){
            String s = "NomInstrument : "+getInstrument()+", Octave : "+getOctave()+"\n";
            ArrayList<Note> res = getNotes();

            for(int i=0; i<res.size(); i++){
                    s+=res.get(i).getName();
            }
            return s+"\n";
    }

}
