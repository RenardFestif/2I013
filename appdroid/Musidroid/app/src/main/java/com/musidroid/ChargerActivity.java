package com.musidroid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import model.extended.PartitionX;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import model.Global;
import android.content.Intent;

public class ChargerActivity extends AppCompatActivity {

    private final static int ID_NORMAL_DIALOG = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);

        final View decorView = getWindow().getDecorView();

        ListView listePartition = findViewById(R.id.list_partitions);

        String[] files = fileList();                //recuperation de tout les fichiers
        final List<String> fileList = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {         // On les mets dans une arraylist
            String tampon = files[i];
            if (tampon.contains(".xml"))
                fileList.add(files[i]);

        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        listePartition.setAdapter(adapter);


        listePartition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChargerActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_load_erase, null);


                /*Setting name in textview*/
                String name = fileList.get(position);
                TextView fileName = (TextView) mView.findViewById(R.id.fileName);
                fileName.setText(name);


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
                charger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Action de lecture du Document XML
                        String fileDir = getFilesDir()+"/"+fileList.get(position); //Chemin absolu du fichier selectionné

                        Document document = readXML(fileDir);
                        loadPartition(document);

                    }
                });
                /*Fin Traitement bouton charger*/

                dialog.show();


            }

        });


    }


    public Document readXML(String fname) {

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

        /***PARCOURS INSTRUMENTPART***/
        for (int i = 0; i<racineNoeuds.getLength(); i++) {
            if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element instrumentPart = (Element) racineNoeuds.item(i);
                NodeList noeudNote = instrumentPart.getChildNodes();

                String nameInstrumentPart = instrumentPart.getAttribute("Name");
                int octave = Integer.parseInt(instrumentPart.getAttribute("Octave"));
                String strInstrumentName = instrumentPart.getAttribute("Instrument");

                partitionX.addPartX(InstrumentName.valueOf(strInstrumentName),octave, nameInstrumentPart);


                /***PARCOURS NOTES POUR CHAQUES INSTUMENTPART***/
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
        System.out.println(partitionX.toString());

        /**Lancement de l'activité menu**/

        Intent intent = new Intent(this, EditionActivity.class);
        startActivity(intent);
        finish();


    }

}
