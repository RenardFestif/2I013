package com.musidroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import model.Global;

public class NavigateurActivity extends AppCompatActivity {

    //Représente le texte qui s'affiche quand la liste est vide
    private TextView mEmpty = null;

    //La liste qui contient nos fichiers et répertoires
    private ListView mList = null;

    //Notre adaptateur personnalisé qui lie les fichiers à la liste
    private FileAdapter mAdapter = null;

    //Représente le répertoire actuel
    private File mCurrentFile = null;

    //Couleur voulue pour les répertoires
    private int mColor = 0;


   //Indique si l'utilisateur est à la racine ou pas
   //Pour savoir s'il veut quitter

    private boolean mCountdown = false;


    ListView listView;
    ArrayList<String> files = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigateur);



        // On récupère la ListView de notre activité
        mList = (ListView) findViewById(R.id.list_fichier);

        // On vérifie que le répertoire externe est bien accessible
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // S'il ne l'est pas, on affiche un message
            mEmpty = (TextView) mList.getEmptyView();
            mEmpty.setText("Vous ne pouvez pas accéder aux fichiers");
        } else {
            // S'il l'est, on déclare qu'on veut un menu contextuel sur les éléments de la liste
            registerForContextMenu(mList);


            // On récupère la racine de la carte SD pour qu'elle soit le répertoire consulté au départ
            //mCurrentFile = Environment.getExternalStorageDirectory();

            // On change le titre de l'activité pour y mettre le chemin actuel
            //setTitle(mCurrentFile.getAbsolutePath());

            // On récupère la liste des fichiers dans le répertoire actuel
            File[] fichiers = File.listRoots();

            // On transforme le tableau en une structure de données de taille variable
            ArrayList<File> liste = new ArrayList<File>();
            for(File f : fichiers)
                liste.add(f);

            mAdapter = new FileAdapter(this, android.R.layout.simple_list_item_1, liste);
            // On ajoute l'adaptateur à la liste
            mList.setAdapter(mAdapter);
            // On trie la liste
            mAdapter.sort();

            // On ajoute un Listener sur les items de la liste
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                // Que se passe-t-il en cas de clic sur un élément de la liste ?
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                    File fichier = mAdapter.getItem(position);
                    // Si le fichier est un répertoire…
                    if(fichier.isDirectory())
                        // On change de répertoire courant
                        updateDirectory(fichier);
                    else
                        // Sinon, on lance l'item
                        seeItem(fichier);
                }
            });
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE

                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    /**
     * On enlève tous les éléments de la liste
     */

    public void setEmpty() {
        // Si l'adaptateur n'est pas vide…
        if(!mAdapter.isEmpty())
            // Alors on le vide !
            mAdapter.clear();
    }

    /**
     * Utilisé pour naviguer entre les répertoires
     * @param pFile le nouveau répertoire dans lequel aller
     */

    public void updateDirectory(File pFile) {
        // On change le titre de l'activité
        setTitle(pFile.getAbsolutePath());

        // L'utilisateur ne souhaite plus sortir de l'application
        mCountdown = false;

        // On change le répertoire actuel
        mCurrentFile = pFile;
        // On vide les répertoires actuels
        setEmpty();

        // On récupère la liste des fichiers du nouveau répertoire
        File[] fichiers = mCurrentFile.listFiles();

        // Si le répertoire n'est pas vide…
        if(fichiers != null) {
            // On les ajoute à  l'adaptateur
            for(File f : fichiers)
                mAdapter.add(f);
            // Puis on le trie
            mAdapter.sort();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Si on a appuyé sur le retour arrière
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // On prend le parent du répertoire courant
            File parent = mCurrentFile.getParentFile();
            // S'il y a effectivement un parent
            if(parent != null)
                updateDirectory(parent);
            else {
                // Sinon, si c'est la première fois qu'on fait un retour arrière
                if(mCountdown != true) {
                    // On indique à l'utilisateur qu'appuyer dessus une seconde fois le fera sortir
                    Toast.makeText(this, "Nous sommes déjà à la racine ! Cliquez une seconde fois pour quitter", Toast.LENGTH_SHORT).show();
                    mCountdown  = true;
                } else
                    // Si c'est la seconde fois, on sort effectivement
                    finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Utilisé pour visualiser un fichier
     * @param pFile le fichier à visualiser
     */
    private void seeItem(File pFile) {

        Intent i = new Intent(Intent.ACTION_VIEW);

        String ext = pFile.getName().substring(pFile.getName().indexOf(".") + 1).toLowerCase();
        if(ext.equals("mid")){
            /*On fait des trucs */
        }
        else{
            Toast.makeText(this, "Ce n'est pas un fichier MIDI", Toast.LENGTH_SHORT).show();
        }

    }








}
