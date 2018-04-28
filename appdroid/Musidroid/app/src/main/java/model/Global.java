package model;


import java.util.ArrayList;

import model.extended.PartitionX;

import android.view.View;
import android.os.Build;
import android.app.Activity;


/**
 * Created by Jörmungandr on 08/02/2018.
 */


// Variables Global à l'application
public class Global {
    public static PartitionX partitions ;
    public static int partSelect = -1;

    /*Pas de la surfaceview pour retrouver les coordonées en brut*/
    public static float pas;

    /*Dans touch Board = Si le le doigt a glisse*/
    public static boolean moved = false;

    /*Coefficient de deplacement pour la seekbar */
    public static int coeffdep = 4;

    /*OffSet en terme de d'instant*/
    public static int offset;


    public static void addPartition(PartitionX p){
        partitions = p ;
    }

    public static PartitionX getPartition (){
        return partitions;
    }

    public static void setPartSelect (int position){
        partSelect = position;
    }

    public static void unSelect (){
        partSelect = -1;
    }

    public static boolean isPartSelected (){
        if (partSelect == -1)
            return false;
        return true;
    }

    public static String toXML(){
        return partitions.toString();
    }

    public static void fullScreenCall(final Activity activity) {

        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOptions);
        


        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {

                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.*
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                        }
                    }
                });
    }

}
