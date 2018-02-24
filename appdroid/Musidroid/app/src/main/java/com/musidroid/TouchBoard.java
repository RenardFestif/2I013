package com.musidroid;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Switch;


import java.util.ArrayList;

import l2i013.musidroid.util.NoteName;
import model.Global;
import model.extended.PartitionX;


public class TouchBoard extends SurfaceView implements SurfaceHolder.Callback  {

    TheApplication app;

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
        int x, y;
        
        c.drawColor(Color.LTGRAY);
        p.setColor(Color.DKGRAY);
        for (int i = 0; i < view.getWidth(); i+=view.getHeight()/longueur) {
            for(int j = 0; j < view.getWidth(); j+=view.getHeight()/longueur) {

                x = (int) (i/pas);
                y = (int) (j/pas);
                int xC = (int) (x * pas + pas/2);
                int yC = (int) (y * pas + pas/2);
            c.drawCircle(xC,yC, 5, p);
            }

        }


        ArrayList<Position> xys = m.getArray();


        for(int i=0;i<xys.size();i++){
            c.drawCircle(xys.get(i).getX(), xys.get(i).getY(), 25, p);

        }


        //Mise à jour de la partie Instru
        updatePart(position,pas);
        System.out.println("STOP");



    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

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

                int caseX = (int) (x/pas);
                int caseY = (int) (y/pas);
                int xC = (int) (caseX * pas + pas/2);
                int yC = (int) (caseY * pas + pas/2);
                app.getModelArray().getModel(position).addRemove(xC, yC);


                reDraw();
                return true;

            }

            default:
                return false;
        }
    }

    public void updatePart(int position, float pas){

        ArrayList<Model> modelArrayList = ModelArray.getmodels();   //On Recup' tout les models
        PartitionX partitionX = Global.getPartition();              //Recup' les parties
        

        for (int i=0; i<modelArrayList.size(); i++) {               // Parcours de tout les models
            Model model = modelArrayList.get(i);                    // Selection d'un model
            for (int j = 0; j < model.getArray().size(); j++) {     // Parcours des Positions
                Position positionsXY = model.getPosition(j);
                int caseX = (int) (positionsXY.getX()/pas);         //Instant
                int caseY = (int) (positionsXY.getY()/pas);         //Hauteur


                NoteName noteName = null;                           //nom de la note

                switch (caseY){
                    case 0:
                        noteName = NoteName.SI;
                        break;
                    case 1:
                        noteName = NoteName.LADIESE;
                        break;
                    case 2:
                        noteName = NoteName.LA;
                        break;
                    case 3:
                        noteName = NoteName.SOLDIESE;
                        break;

                    case 4:
                        noteName = NoteName.SOL;
                        break;

                    case 5:
                        noteName = NoteName.FADIESE;
                        break;

                    case 6:
                        noteName = NoteName.FA;
                        break;


                    case 7 :
                        noteName = NoteName.MI;
                        break;


                    case 8 :
                        noteName = NoteName.REDIESE;
                        break;

                    case 9 :
                        noteName = NoteName.RE;
                        break;

                    case 10 :
                        noteName = NoteName.DODIESE;
                        break;

                    case 11 :
                        noteName = NoteName.DO;
                        break;

                    default:
                        break;
                }
                assert noteName != null;            //On affirme que noteName n'est pas nul

                partitionX.addNote(i,caseX,noteName,1);

            }
        }


        for (int i=0; i<partitionX.getPartsX().size();i++){
            for (int j=0; j<partitionX.getPart(i).getNotes().size(); j++){
                System.out.println(partitionX.getPart(i).getNotes().get(j).getName());
            }
        }



    }

}
