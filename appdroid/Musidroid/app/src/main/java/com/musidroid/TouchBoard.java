package com.musidroid;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.ArrayList;


public class TouchBoard extends SurfaceView implements SurfaceHolder.Callback {

    TheApplication app;

    private static final int longueur = 13;



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
        Model m = app.getModel();
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
        SurfaceView view = (SurfaceView) findViewById(R.id.boardSurface);
        float pas = view.getHeight()/longueur;
        int action = event.getAction();
        switch (action) {
            case MotionEvent. ACTION_DOWN: {

                int caseX = (int) (x/pas);
                int caseY = (int) (y/pas);
                int xC = (int) (caseX * pas + pas/2);
                int yC = (int) (caseY * pas + pas/2);
                app.getModel().addRemove(xC, yC);
                reDraw();
                return true;
            } default:
                return false;
        }
    }


}
