package model.extended;

import l2i013.musidroid.util.NoteName;
import musidroid.Note;

/**
 * Created by louisbraud on 04/03/2018.
 */

public class NoteX extends Note {

    public NoteX(int t, NoteName n, int d) {
        super(t, n, d);
    }

    @Override
    public String toString() {
        return "<Note instant="+super.getInstant()+" name="+super.getName()+" duree="+super.getDuration()+" />";
    }
}
