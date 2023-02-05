package com.example.projet_final;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class secondActivity extends AppCompatActivity {
    //init var
    Button returnHomeBtn = null;
    Button raz = null;
    TextView IMCtext,petit_message;
    CheckBox megaF = null;
    EditText poids;
    EditText taille ;
    TextView result = null;
    Button calculBtn = null;
    Button graphBtn = null;
    RadioGroup group = null;
    Spinner liste;
    MediaPlayer mediaPlayer,mediaPlayerIMC,mediaPlayerRAZ,mediaPlayerClear;
    public final static String ResultatIMC = "Resultat.IMC";
    String defaut = "Entrez vos mesures. Vous devez cliquer sur le bouton « Calculer l'IMC » pour obtenir un résultat.";
    AlertDialog.Builder builder;
    String d,m,y;
    String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //activation bouton up
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //button return
        myToolbar.setNavigationOnClickListener(clickToolbar);
        //liste
        liste = (Spinner) findViewById(R.id.spinner1);
        List<String> exemple = new ArrayList<String>();
        exemple.add("Homme");
        exemple.add("Femme");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, exemple);
        liste.setAdapter(adapter);
        //radioButton
        RadioGroup radioGroup = new RadioGroup(this);
        RadioButton radioMetre = new RadioButton(this);
        RadioButton radioCenti = new RadioButton(this);
        // add bp to radiogroup
        group = (RadioGroup) findViewById(R.id.group);
        radioGroup.addView(radioMetre, 0);
        radioGroup.addView(radioCenti, 1);
        int idradio = radioGroup.getCheckedRadioButtonId();
        //sound
        mediaPlayer = MediaPlayer.create(secondActivity.this,R.raw.error);
        mediaPlayerIMC= MediaPlayer.create(secondActivity.this,R.raw.calcul_imc);
        mediaPlayerRAZ= MediaPlayer.create(secondActivity.this,R.raw.click);
        mediaPlayerClear= MediaPlayer.create(secondActivity.this,R.raw.clear);
        //megafonction
        megaF = (CheckBox) findViewById(R.id.checkBox);
        megaF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //checkbox
                IMCtext = findViewById(R.id.result);
                if (megaF.isChecked()) {
                    IMCtext.setTextSize(20); //ne fonctionne pas encore
                } else {
                    IMCtext.setTextSize(16); //ne fonctionne pas encore
                }
            }
            });
            //weight
            poids = findViewById(R.id.poids_input);
            //height
            taille = findViewById(R.id.taille_input);
            //Boutton calculer
            calculBtn = findViewById(R.id.buttoncalcul);
            calculBtn.setOnClickListener(calculateListener);
            //bp raz
            raz = (Button) findViewById(R.id.raz);
            raz.setOnClickListener(razListener);
            builder = new AlertDialog.Builder(this);
            //resultat
            IMCtext = (TextView) findViewById(R.id.result);
            //message
            petit_message = (TextView) findViewById(R.id.petit_message);
            //empty input
            if(poids.getText().toString().trim().isEmpty() || taille.getText().toString().trim().isEmpty() || poids.getText().toString().trim().equals("O") || taille.getText().toString().trim().equals("0")){
                Toast.makeText(getApplicationContext(),"Entrer un poids et une taille",
                        Toast.LENGTH_SHORT).show();
            }
            //bp graph
            graphBtn = findViewById(R.id.graphiqueView);
            graphBtn.setOnClickListener(GraphListener);
    }
    // Listener du bouton de remise à zéro
    private final View.OnClickListener razListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayerRAZ.start();
            if (poids.getText().toString().trim().isEmpty() || taille.getText().toString().trim().isEmpty() || poids.getText().toString().trim().equals("O") || taille.getText().toString().trim().equals("0")){
                Toast.makeText(getApplicationContext(),"Entrer un poids et une taille",
                        Toast.LENGTH_SHORT).show();
            }else {
                //using string.xml
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
                //dialog message
                builder.setMessage("Attention, vous allez supprimer les données ?").setCancelable(false).setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        Toast.makeText(getApplicationContext(),"RAZ activé",
                                Toast.LENGTH_SHORT).show();
                        mediaPlayerClear.start();
                        poids.getText().clear();
                        taille.getText().clear();
                        IMCtext.setText(defaut);
            }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"RAZ non activé",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();

            }
        }
    };
    //bp up retour
    final View.OnClickListener clickToolbar = new View.OnClickListener() {// clic sur le bouton Up
        @Override
        public void onClick(View v) {
            Intent MainActivity = new Intent(secondActivity.this, MainActivity.class);
            startActivity(MainActivity);
        }
    };
    //show menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //item toolbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mail:
                String cc = ""; // leave it empty
                String objet = "IMC";
                String corps = "Mon est IMC est de "+ResultatIMC;
                Intent emailActivite = new Intent(Intent.ACTION_SENDTO);
                emailActivite.setData(Uri.parse("mailto:" + cc));
                emailActivite.putExtra(Intent.EXTRA_SUBJECT, objet);
                emailActivite.putExtra(Intent.EXTRA_TEXT, corps);
                startActivity(emailActivite);
                return true;
            case R.id.action_calendar:
                //open calendar window
                Calendar calendar = Calendar.getInstance();
                int annee = calendar.get(Calendar.YEAR);
                int mois = calendar.get(Calendar.MONTH);
                int jour = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(secondActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int annee, int mois, int jourdumois) {
                        // code to handle the selected date
                    }
                },
                        annee, mois, jour); //took annee mois jour on annee mois and jourdumois
                datePickerDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //bp calculer
    View.OnClickListener calculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(poids.getText().toString().trim().isEmpty() || taille.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),"Entrer un poids et une taille",
                        Toast.LENGTH_SHORT).show();
            }else{
                RadioButton centiradio = findViewById(R.id.radioCenti);
                String weightString = String.valueOf(poids.getText());
                Double weightStringVal = Double.parseDouble(weightString);
                String heightString = String.valueOf(taille.getText());
                Double heightStringVal = Double.parseDouble(heightString);
                Double imc ;

                //test poids and taille limits
                if ((weightString.equals("") && heightString.equals(""))||(weightString.equals("0") && heightString.equals("0"))){
                    Toast.makeText(getApplicationContext(),"Erreur de saisi ! Merci d'entrer les informations", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }else if(weightStringVal<20 || weightStringVal >250){
                    Toast.makeText(getApplicationContext(),"Le poids est hors borne (inf à 20kg ou sup à 250kg)",Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }else if((heightStringVal<100 || heightStringVal>250)&& centiradio.isChecked()){
                    Toast.makeText(getApplicationContext(),"La taille est hors borne (inf à 100cm/1m ou sup à 250cm/2.5m)",Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }else if((heightStringVal<1 || heightStringVal>2.5)&& !centiradio.isChecked()){
                    Toast.makeText(getApplicationContext(),"La taille est hors borne (inf à 1m ou sup à 2.5m)",Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                } else{

                    if(centiradio.isChecked()){
                        imc = (weightStringVal/((heightStringVal/100)*(heightStringVal/100)));


                    }else{
                        imc = weightStringVal/(heightStringVal*heightStringVal);
                    }
                    //category imc
                    if (imc < 18.5) {
                        category = "Sous poids";
                        petit_message.setText("Tu es en-dessous du poids idéal, prends soin de toi");
                    } else if (imc >= 18.5 && imc <= 24.9) {
                        category = "Poids normal";
                        petit_message.setText("Tu es dans la norme");
                    } else if (imc >= 25 && imc <= 29.9) {
                        category = "Surpoids";
                        petit_message.setText("Tu es au dessus du poids idéal, ça arrive ne t'en fait pas");
                    } else {
                        category = "Obèsité";
                        petit_message.setText("Les chiffres ont peut leurs faire dire ce que l'on veut");
                    }
                    //view text
                    if(!megaF.isChecked()){
                        IMCtext.setText("Votre IMC est de "+imc);
                    }
                    else{ //when mega fonction checked and press calculer bp
                        if(liste.getSelectedItem().toString()=="Homme"){
                            IMCtext.setText(String.format("Monsieur, votre IMC est de %.2f\nVous êtes dans la catégorie %s", imc, category));
                        }else if(liste.getSelectedItem().toString()=="Femme"){
                            IMCtext.setText(String.format("Madame, votre IMC est de %.2f\nVous êtes dans la catégorie %s", imc, category));
                        }
                    }

                    //stock imc
                    mediaPlayerIMC.start();
                    Intent resultat = new Intent();
                    resultat.putExtra(ResultatIMC, imc);
                    setResult(RESULT_OK, resultat);
                    //finish();
                }
                }
            }
    };
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            poids.getText().clear();
            taille.getText().clear();
            IMCtext.setText(defaut);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    // Listener du bouton de remise à zéro
    private final View.OnClickListener GraphListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton centiradio = findViewById(R.id.radioCenti);
            String heightString = String.valueOf(taille.getText());
            Double heightStringVal = Double.parseDouble(heightString);
            if (poids.getText().toString().trim().isEmpty() || taille.getText().toString().trim().isEmpty() || poids.getText().toString().trim().equals("O") || taille.getText().toString().trim().equals("0")){
                Toast.makeText(getApplicationContext(),"Entrer un poids et une taille",
                        Toast.LENGTH_SHORT).show();
            }else if(centiradio.isChecked()&&(heightStringVal>100 && heightStringVal<250)){
                Intent GraphActivity = new Intent(secondActivity.this, thirdActivity.class);
                GraphActivity.putExtra("Poids", String.valueOf(poids.getText()));
                GraphActivity.putExtra("Taille", String.valueOf(taille.getText()));
                startActivity(GraphActivity);
            }else if(!centiradio.isChecked() || (heightStringVal>1 && heightStringVal<2.5)){
                Toast.makeText(getApplicationContext(),"La taille en cm",
                        Toast.LENGTH_SHORT).show();
            }


        }
    };

}