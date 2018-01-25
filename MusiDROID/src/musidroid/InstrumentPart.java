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
    
    /*Used Method for getNotes()*/
    private ArrayList<Note> copyNotes (){
        ArrayList<Note> ret = new ArrayList<>();
        for (int i=0; i<notes.size(); i++){
            ret.add(notes.get(i));
        }
        return ret;
    }


    public ArrayList<Note> getNotes(){ //ERROR
        if(notes.isEmpty() || notes.size() == 1){
                return notes;
        }
        else{
            ArrayList<Note> cpy = this.copyNotes();     //Creation d'une copie modifiable de notes
            ArrayList<Note> ret1 = new ArrayList<>();
            ArrayList<Note> ret = new ArrayList<>();
            Note not = null;
            
            //Tri par Hauteur
            for(int j=cpy.size(); 0<j; j-- ){
                not = cpy.get(0);
                for(int i=1; i<cpy.size(); i++){
                    if (cpy.get(i).getName().getNum()< not.getName().getNum())
                        not = cpy.get(i);
                }
                ret1.add(not);
                cpy.remove(not);
            }
            //Tri par instant
            for(int j=ret1.size(); 0<j; j-- ){
                not = ret1.get(0);
                for(int i=1; i<ret1.size(); i++){
                    if (ret1.get(i).getInstant()< not.getInstant())
                        not = ret1.get(i);
                }
                ret.add(not);
                ret1.remove(not);
            }
            
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
