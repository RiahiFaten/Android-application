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

import com.example.application.data.Prise;
import com.example.application.data.Programme;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AjouterprisesActivity extends AppCompatActivity {

    EditText editTextDescription;
    EditText editTextQuantité;
    EditText editTextDate;
    EditText editTextHeure;
    EditText editTextRefMed;

    Button buttonAjouter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouterprises);

        Intent intent = getIntent();
        final String patientid = intent.getStringExtra(ConsulterprisesActivity.PATIENT_ID);
        final String programmeid = intent.getStringExtra(ConsulterprisesActivity.PROG_ID);

        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextQuantité = (EditText) findViewById(R.id.editTextQté);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextHeure = (EditText) findViewById(R.id.editTextHeure);
        editTextRefMed = (EditText) findViewById(R.id.editTextRefMed);

        buttonAjouter = (Button) findViewById(R.id.buttonAjouter);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = editTextDescription.getText().toString().trim();
                String quantité = editTextQuantité.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String heure = editTextHeure.getText().toString().trim();
                String refmed = editTextRefMed.getText().toString().trim();

                if ((!TextUtils.isEmpty(description)) && (!TextUtils.isEmpty(quantité)) && (!TextUtils.isEmpty(date)) && (!TextUtils.isEmpty(heure)) && (!TextUtils.isEmpty(refmed))){

                    String idI = databaseReference.child("prises").push().getKey();
                    int d = Integer.parseInt(quantité);
                    Prise prise = new Prise(idI, description, date,d, heure, programmeid,refmed,patientid);
                    databaseReference.child("prises").child(idI).setValue(prise, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            makeText("Instruction ajoutée");
                            editTextDescription.setText("");
                            editTextQuantité.setText("");
                            editTextDate.setText("");
                            editTextHeure.setText("");
                            editTextRefMed.setText("");

                        }
                    });}
                else{
                    makeText("Veuillez remplir tous les champs");
                }


            }
        });
    }
    public void makeText(String message){Toast.makeText(this, message, Toast.LENGTH_LONG).show();}
}



