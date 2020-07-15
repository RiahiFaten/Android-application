package com.example.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.data.Programme;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AjouterprogrammesActivity extends AppCompatActivity {
    EditText editTextNom;
    EditText editTextDatedebut;
    EditText editTextDuree;
    EditText editTextMaladie;
    Button buttonAjouter;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouterprogrammes);


        Intent intent = getIntent();
        final String patientid = intent.getStringExtra(ProfileActivity.PATIENT_ID);


        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextDatedebut = (EditText) findViewById(R.id.editTextdatedebut);
        editTextDuree = (EditText) findViewById(R.id.editTextduree);
        editTextMaladie = (EditText) findViewById(R.id.editTextmaladie);
        buttonAjouter = (Button) findViewById(R.id.buttonAjouter);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom = editTextNom.getText().toString().trim();
                String date_debut = editTextDatedebut.getText().toString().trim();
                String durée = editTextDuree.getText().toString().trim();
                String maladie = editTextMaladie.getText().toString().trim();
                if ((!TextUtils.isEmpty(nom)) && (!TextUtils.isEmpty(date_debut)) && (!TextUtils.isEmpty(durée)) && (!TextUtils.isEmpty(maladie))){

                String idP = databaseReference.child("programmes").push().getKey();
                int d = Integer.parseInt(durée);
                Programme programme = new Programme(idP, nom, date_debut, d, maladie, patientid);
                databaseReference.child("programmes").child(idP).setValue(programme, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        makeText("programme ajouté");
                        editTextNom.setText("");
                        editTextDatedebut.setText("");
                        editTextDuree.setText("");
                        editTextMaladie.setText("");

                    }
                });
            } else {
                 makeText("Veuillez remplir tous les champs");
            }


            }
        });
    }
  public void makeText(String message){Toast.makeText(this, message, Toast.LENGTH_LONG).show();}
}

