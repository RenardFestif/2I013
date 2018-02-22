package com.musidroid;

public class Position {
    int x, y;
    Position(int x, int y) { this.x = x; this.y = y; }
    public Integer getX() { return x; }
    public Integer getY() { return y; }


    public boolean equals(Object obj){
        if (obj == null) return false;
        if(obj == this) return true;
        if(this.getClass() != obj.getClass()) return false;
        Position tmp = (Position) obj;
        if(tmp.getX() == this.getX())
            if(tmp.getY()==this.getY())
                return true;
        return false;
    }

}