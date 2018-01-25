package l2i013.musidroid.util;

public enum NoteName {
    DO,
    DODIESE,
    RE,
    REDIESE,
    MI,
    FA,
    FADIESE,
    SOL,
    SOLDIESE,
    LA,
    LADIESE,
    SI;

    public String toString() {
        return super.toString().replace("DIESE", "#");
    }

    public int getNum() {
        return this.ordinal();
    }

    public static NoteName ofNum(int i) { return values()[i]; }


}
