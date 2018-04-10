package com.musidroid;

public class Position {
    int x, y;
    int duration;
    Position(int x, int y) { this.x = x; this.y = y; }
    Position(int x, int y, int duration){
        this(x,y);
        this.duration = duration;
    }


    public Integer getX() { return x; }
    public Integer getY() { return y; }
    public int getDurartion(){ return duration; }


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

    public String toString(){
        return "X ="+getX()+"\t Y="+getY();
    }

}