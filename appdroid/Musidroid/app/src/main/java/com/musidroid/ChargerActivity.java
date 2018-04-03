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
import model.extended.PartitionX;
import l2i013.musidroid.util.InstrumentName;
import l2i013.musidroid.util.NoteName;
import model.extended.InstrumentPartX;
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

        /**PARCOURS DES INSTRUMENTPART**/
        for (int i = 0; i<racineNoeuds.getLength(); i++) {
            if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {       //On test si le noeud est bien un noeud et non un commentaire
                Element instrumentPart = (Element)racineNoeuds.item(i);   //Element InstrumentPart

                /** DEBUG **/
                System.out.println(instrumentPart.getNodeName());
                System.out.println(instrumentPart.getAttribute("Octave"));
                System.out.println(instrumentPart.getAttribute("Name"));
                System.out.println(instrumentPart.getAttribute("Instrument"));
                /**DEBUG**/

                String instru = instrumentPart.getAttribute("Instrument");  //Nom de l'instrument (String) il faut le convertir
                InstrumentName instrumentName = InstrumentName.valueOf(instru);     //String convertie en Instrument Name

                InstrumentPartX instrumentPartX= new InstrumentPartX(instrumentName,Integer.parseInt(instrumentPart.getAttribute("Octave")));
                instrumentPartX.setName(instrumentPart.getAttribute("Name"));


               // partitionX.addPart(instrumentName, Integer.parseInt(instrumentPart.getAttribute("Octave")));    //Partition ajouté
               // //partitionX.setName(i,instrumentPart.getAttribute("Name") );                                     //Nom de la partition mis a jour

                NodeList noeudNotes = instrumentPart.getElementsByTagName("Note");     //Noeud des Notes

                /**PARCOURS DES NOTES POUR CHAQUES INSTRUMENT PART**/
                for (int j = 0; j<noeudNotes.getLength(); j++){
                    if(noeudNotes.item(j).getNodeType() == Node.ELEMENT_NODE){

                        Element note = (Element) noeudNotes.item(i);      //Element note

                        /** DEBUG **/

                        System.out.println(note.getAttribute("instant"));
                        System.out.println(note.getAttribute("name"));
                        System.out.println(note.getAttribute("duree"));
                        /**DEBUG**/

                        NoteName noteName = NoteName.valueOf(note.getAttribute("name").replace("#", "DIESE"));  //Le replace permet de correler avec l'enum de NotName
                        instrumentPartX.addNote(Integer.parseInt(note.getAttribute("instant")),noteName, Integer.parseInt(note.getAttribute("duree")));
                       // partitionX.addNote(i,Integer.parseInt(note.getAttribute("instant")),noteName, Integer.parseInt(note.getAttribute("duree")));    //erreur sur le i

                    }
                }

                partitionX.addPartX(instrumentPartX);






            }
        }

        Global.addPartition(partitionX);
        Global.partSelect = -1;

        Intent intent = new Intent(this, EditionActivity.class);
        startActivity(intent);
        finish();


    }

}
