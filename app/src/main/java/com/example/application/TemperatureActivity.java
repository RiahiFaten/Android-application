package com.example.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.data.Prise;
import com.example.application.data.Programme;
import com.example.application.data.ProgrammesList;
import com.example.application.data.Temperature;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TemperatureActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ; //temp

    TextView tempMesures;
    String userId;

    EditText editTextddegres;
    EditText editTextdate;

    Button buttonAjouter;


    private FirebaseAuth firebaseAuth;
    String temperatures ;


    DatabaseReference databaseReference;
    DatabaseReference databaseReferencea = FirebaseDatabase.getInstance().getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        temperatures="";
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId= user.getUid();
        tempMesures = (TextView) findViewById(R.id.tempMesures);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("temperatures");
       // ajouterInstruction.setOnClickListener(this);
      //  retourProgrammes.setOnClickListener(this);

        editTextddegres = (EditText) findViewById(R.id.degres);
        editTextdate = (EditText) findViewById(R.id.date);

        buttonAjouter = (Button) findViewById(R.id.buttonAjouter);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String degres = editTextddegres.getText().toString().trim();
                String date = editTextdate.getText().toString().trim();
                String patient = userId;

                if ((!TextUtils.isEmpty(degres)) && (!TextUtils.isEmpty(date))){

                    String numT = databaseReferencea.child("temperatures").push().getKey();
                    Temperature temp = new Temperature(numT, degres, date,patient);
                    databaseReferencea.child("temperatures").child(numT).setValue(temp, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            makeText("Mesure ajoutée");
                            editTextddegres.setText("");
                            editTextdate.setText("");


                        }
                    });
                //Si la température est supérieure à 40, un message sera envoyé au médecin
                int t = Integer.parseInt(degres);
                if (t>40){
                    sendSMSMessage(firebaseAuth.getCurrentUser().getEmail(),t);
                }

                }
                else{
                    makeText("Veuillez remplir tous les champs");
                }


            }
        });
    }
    public void makeText(String message){Toast.makeText(this, message, Toast.LENGTH_LONG).show();}


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temperatures="";
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Temperature temperature = keyNode.getValue(Temperature.class);

                    String numTemp = temperature.getNumTemp();
                    String degre = temperature.getDegre();
                    String dateT = temperature.getDateT();
                    String numP = temperature.getNumP();

                    if (numP.contentEquals(userId)){
                        temperatures = temperatures + degre+"                             "+dateT+"\n";
                    }
                    else{temperatures="     -"+"                             "+"               -"+"\n"+"aucune température sauvegardée pour le moment!";}

                    tempMesures.setText(temperatures);


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    String phoneNo;
    String message;
    protected void sendSMSMessage(String emailPatient,int tempPatient) {
        phoneNo = "Numéro_du_medecin";
        message = "Le patient "+emailPatient+"a une température "+tempPatient;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Un SMS a été envoyé à votre médecin",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Le SMS n'a pas pu etre envoyé, veuillez contacter votre médecin", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}
