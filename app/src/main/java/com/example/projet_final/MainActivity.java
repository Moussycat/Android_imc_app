package com.example.projet_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity {
    //init var
    Toolbar myToolbar = null;
    Button calculBtn = null;
    EditText inputText = null;
    SharedPreferences preferences = null;
    TextView prenom_stock = null;
    TextView text_stock = null;
    ImageView image = null;
    MediaPlayer mediaPlayer, mediaImc;


    public final static int ACTIVITE2_CHOICE = 0;

    public final static String SAUV_FIRSTNAME= "sauv firstname";
    //on click go to secondActivity page
    //Intent secondActivity = new Intent(MainActivity.this, secondActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mediaImc = MediaPlayer.create(MainActivity.this,R.raw.start);
        mediaImc.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //animation drawable
        ConstraintLayout constraintLayout= findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable= (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //input text if not
        inputText = findViewById(R.id.EditText);
        prenom_stock = findViewById(R.id.prenom_stock);
        text_stock = findViewById(R.id.text_stock);
        //Button
        calculBtn = findViewById(R.id.button);
        calculBtn.setOnClickListener(calculateListener);
        //stock and reuse SAUV_FIRSTNAME
        preferences = getPreferences(MODE_PRIVATE);
        String prenomSauv = preferences.getString(SAUV_FIRSTNAME,
                "");
        prenom_stock.setText(prenomSauv);
        inputText.setText(prenomSauv);
        text_stock.setText("Entrez votre prénom ou appyer sur le bouton");
        //Image animation
        image = findViewById(R.id.imageView);
        Animation imgAnimation = AnimationUtils.loadAnimation(this,R.anim.anim );
        image.startAnimation(imgAnimation);
    }
    View.OnClickListener calculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if(inputText.getText().toString().equals("")){
                text_stock.setText("Entrez un prénom");
            }
            else{
                mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.click);
                mediaPlayer.start();
                Intent secondActivity = new Intent(MainActivity.this, secondActivity.class);
                startActivityForResult(secondActivity, ACTIVITE2_CHOICE);
                String valeurPrenom = String.valueOf(inputText.getText());

                preferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SAUV_FIRSTNAME, valeurPrenom);
                prenom_stock.setText(valeurPrenom); //show Name went we click on "Calculer"
                editor.commit();
                //startActivity(secondActivity);
            }
        }};

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // On vérifie tout d'abord à quel intent on fait référence ici à l'aide de notre identifiant
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITE2_CHOICE) {
            // On affiche le bouton qui a été choisi
            if(secondActivity.ResultatIMC==null){
                text_stock.setText("Votre dernier calcul était faux");
            }else {
                Double imc = data.getDoubleExtra(secondActivity.ResultatIMC,0);
                Toast.makeText(this, "Vous avez un imc de " + imc,
                        Toast.LENGTH_SHORT).show();
                text_stock.setText("Votre dernier IMC était de "+ imc +  "\nVoulez_vous le recalculer ?");
            }

        }
    }



}