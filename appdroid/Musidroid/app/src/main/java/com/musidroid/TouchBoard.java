package com.musidroid;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;


import java.util.ArrayList;

import l2i013.musidroid.util.NoteName;
import model.Global;
import model.extended.InstrumentPartX;
import model.extended.PartitionX;
import musidroid.Note;


public class TouchBoard extends SurfaceView implements SurfaceHolder.Callback  {

    TheApplication app;




    //Pour les Action sur la Surface
    ArrayList<Position> xyStored; //Pour le nom des notes
    ArrayList<Position> xyStored1; // Pour le dessin


    int d; //durée de la note si moved = true
    int xPrevious;
    int yPrevious;
    int radius = 25;

    public static final int longueur = 12;



    public TouchBoard(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    public TouchBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        app = (TheApplication) (context.getApplicationContext());
    }
    void reDraw() {
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            this.onDraw(c);
            getHolder().unlockCanvasAndPost(c);
        }
    }


    //Pas du tout optimise
    @Override
    public void onDraw(Canvas c) {
        SurfaceView view = (SurfaceView) findViewById(R.id.boardSurface);

        // On cherche le bon model dans l'array
        int position = Global.partSelect;
        Model m = app.getModelArray().getModel(position);


        Paint p = new Paint();


        float pas = view.getHeight()/longueur;
        //On set le pas dans Global
        Global.pas = pas;
        int x, y;

        c.drawColor(Color.LTGRAY);
        p.setColor(Color.DKGRAY);

        /* Dessin des petits points */
        for (int i = 0; i < view.getWidth(); i+=view.getHeight()/longueur) {
            for(int j = 0; j < view.getWidth(); j+=view.getHeight()/longueur) {

                x = (int) (i/pas);
                y = (int) (j/pas);
                int xC = (int) (x * pas + pas/2);
                int yC = (int) (y * pas + pas/2);
                c.drawCircle(xC,yC, 5, p);
            }

        }
        /* Fibn de dessin des petits points */

        ArrayList<Position> xys = m.getArray();


        for (int i = 0; i < xys.size(); i++) {

            if ((xys.get(i).getX()/pas) >= Global.offset && (xys.get(i).getX()/pas) <= view.getWidth()) {


                if (xys.get(i).getDurartion() == 1)
                        c.drawCircle(xys.get(i).getX()-Global.offset*pas, xys.get(i).getY(), radius, p);
                else {
                        // DESSIN D'UN RECT

                        // ne pas oublier le décalage de -Global.offset*pas <!>

                        int d = (xys.get(i).getDurartion());
                        int xXYS = (int)(xys.get(i).getX());
                        int caseXXYS = (int) (xXYS / pas);
                        int xf = d + caseXXYS - 1;
                        int coordXF = (int) ((xf * pas + pas / 2)-Global.offset*pas);
                        int yXYS = xys.get(i).getY();
                        c.drawRoundRect(xXYS - radius -Global.offset*pas, yXYS - radius, coordXF + radius, yXYS + radius, 20, 20, p);

                }
            }
       }




    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.boardSurface);
        float pas = surfaceView.getHeight()/longueur;
        //On set le pas dans Global
        Global.pas = pas;
        /**A chaque clique sur EDIT on charge un nouveau model calqué depuis la partionX**/

        PartitionX partitionX = Global.getPartition();
        ArrayList<Model> modelArray = ModelArray.getmodels();
        modelArray.clear();
        for (int i = 0; i<partitionX.getPartsX().size();i++){
            Model model = new Model();

            for (int j=0; j<partitionX.getPart(i).getNotes().size(); j++ ){
                Note note = partitionX.getPart(i).getNotes().get(j);
                int x = note.getInstant();
                int duration = note.getDuration();
                int y = note.getName().getNum();
                model.addModel(x,y,duration);
            }
            modelArray.add(model);

        }

    }
    @Override
    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {
        reDraw();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        int position = Global.partSelect;
        SurfaceView view = (SurfaceView) findViewById(R.id.boardSurface);
        float pas = view.getHeight()/longueur;


        int action = event.getAction();
        switch (action) {


            case MotionEvent.ACTION_DOWN : {
                xPrevious = x;
                yPrevious = y;
                return true;

            }

            case MotionEvent.ACTION_UP:


                int caseX = (int) (x / pas);
                int caseY = (int) (y / pas);
                int xC = (int) (caseX * pas + pas / 2);
                int yC = (int) (caseY * pas + pas / 2);

                if(xPrevious==x && yPrevious==y) {

                    app.getModelArray().getModel(position).addRemove(xC, yC, caseX, caseY,1, Global.offset, pas);
                    reDraw();

                }
                // On affiche les notes quand on releve le doigt
                else if (Global.moved){

                    Global.moved = false;  //fini de bouger
                    d = (int)(x/pas)-(int)(xPrevious/pas)+1;     //Durée
                    System.out.println(d);

                    //int caseXMove = (int)(xPrevious/pas);
                    //int caseYMove = (int)(yPrevious/pas);
                    //int xCMove = (int) (caseX * pas + pas / 2);
                    //int yCMove = (int) (caseY * pas + pas / 2);

                    caseX = (int)(xPrevious/pas);
                    caseY = (int)(yPrevious/pas);
                    xC = (int) (caseX * pas + pas / 2);
                    yC = (int) (caseY * pas + pas / 2);
                    app.getModelArray().getModel(position).addRemove(xC, yC, caseX, caseY ,d, Global.offset, pas); //Sur le premier temps
                    reDraw();


                }




                return true;


            case MotionEvent.ACTION_MOVE :


                if(yPrevious == y && x > xPrevious) {

                    Global.moved = true;
                }
                return true;


            default:
                return false;
        }
    }


    public boolean noteExist(InstrumentPartX instru, Note note){
        ArrayList<Note> notes = instru.getNotes();
        for(int i=0;i<notes.size();i++){
            Note n = notes.get(i);
            if(n.getInstant()==note.getInstant() && n.getName()==note.getName())
                return true;
        }
        return false;
    }






}
