package com.musidroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.AlertDialog;
import java.io.IOException;
import org.xml.sax.SAXException;
import android.widget.TextView;
import android.widget.Button;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import model.Samples;
import model.extended.PartitionX;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import model.Global;
import android.content.Intent;
import android.widget.Toast;

public class ChargerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);


        //Mis au point du mode fullScreen Imerssif
        Global.fullScreenCall(this);

        ListView listePartition = findViewById(R.id.list_partitions);

        String[] files = fileList();                //recuperation de tout les fichiers
        final List<String> fileList = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {         // On les mets dans une arraylist
            String tampon = files[i];
            if (tampon.contains(".xml") || tampon.contains(".mid"))
                fileList.add(files[i]);
        }

        //Partie adapteur
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        listePartition.setAdapter(adapter);

        listePartition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                //Creation d'une boite de dialogue
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChargerActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_load_erase, null);


                /*Mise au point du nom dans TextView*/
                final String name = fileList.get(position);
                TextView fileName = (TextView) mView.findViewById(R.id.fileName);
                fileName.setText(name);

                //Ajout de la view dans la boite de dialogue
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                /*Action sur le boutton cancel*/
                Button cancel = (Button) mView.findViewById(R.id.buttonCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                /*Fin sur le Bouton cancel*/

                /*traitement du Bouton supprimer*/
                Button supprimer = (Button) mView.findViewById(R.id.buttonDelete);
                supprimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFile(fileList.get(position));
                        fileList.remove(position);
                        dialog.dismiss();
                        recreate();
                    }
                });
                /* Fin traitement bouton supprimer*/

                /*Traitement du Bouton charger*/
                Button charger = (Button) mView.findViewById(R.id.buttonLoad);

                /*Si c'est un fichier XML*/
                if ( fileList.get(position).contains(".xml")){
                    charger.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Action de lecture du Document XML
                            String fileDir = getFilesDir()+"/"+fileList.get(position); //Chemin absolu du fichier selectionné
                            Document document = readXML(fileDir);
                            loadPartition(document);
                            onMidiCharger(v);
                        }
                    });
                }

                /*Si c'est un fichier midi*/
                else{
                    charger.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        Toast.makeText(ChargerActivity.this, "Construction de la partition en cours ! Un peu de patience ;)", Toast.LENGTH_LONG).show();
                        Samples.read(getFilesDir()+"/"+fileList.get(position));
                        while (Global.isWriting){
                             /*On attend la fin de la creation de la partition*/
                        }
                         onMidiCharger(v);
                        }
                    });
                }
                /*Fin Traitement bouton charger*/

                //Permet l'affichage de la boite de dialogue.
                dialog.show();
            }

        });
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


    public Document readXML(String fname) {
        //Creation d'un document XML à partir d'un fichier
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(fname));
        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (final SAXException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void loadPartition (Document document){
        final Element racine = document.getDocumentElement();   //Element Racine (Partiton)
        final NodeList racineNoeuds = racine.getChildNodes();   //Liste des enfants - parcours avec un for -

        PartitionX partitionX = new PartitionX(Integer.parseInt(racine.getAttribute("tempo"))); //Nouvelle Partition

        int cpt = 0;    //Partie instrumental dans l'arraylist

        //PARCOURS INSTRUMENTPART
        for (int i = 0; i<racineNoeuds.getLength(); i++) {
            //Verification si le noeud est un Element
            if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element instrumentPart = (Element) racineNoeuds.item(i);
                NodeList noeudNote = instrumentPart.getChildNodes();

                String nameInstrumentPart = instrumentPart.getAttribute("Name");
                int octave = Integer.parseInt(instrumentPart.getAttribute("Octave"));
                String strInstrumentName = instrumentPart.getAttribute("Instrument");

                partitionX.addPartX(InstrumentName.valueOf(strInstrumentName),octave, nameInstrumentPart);


                //PARCOURS NOTES POUR CHAQUES INSTUMENTPART
                for (int j = 0; j <noeudNote.getLength(); j++){
                    if(noeudNote.item(j).getNodeType() == Node.ELEMENT_NODE){

                         Element note = (Element) noeudNote.item(j);

                         int instant =Integer.parseInt(note.getAttribute("instant"));
                         String nameNote = note.getAttribute("name").replace("#", "DIESE");
                         int duree =Integer.parseInt(note.getAttribute("duree"));

                         System.out.println(instant);

                         partitionX.addNote(cpt,instant,NoteName.valueOf(nameNote),duree);
                    }
                }
                cpt++; //Instrument suivant;
            }
        }
        Global.partitions = partitionX;
    }

    public void onClickExitCharger(View view){
        finish();
    }

    public void onMidiCharger(View view){
        Intent intent = new Intent(this, EditionActivity.class);
        //Flag pour faire revenir au top Edition activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickImporterMidi(View view){
        Intent intent = new Intent(this, NavigateurActivity.class);
        startActivity(intent);
    }

}
