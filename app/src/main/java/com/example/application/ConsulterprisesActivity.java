package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConsulterprisesActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTextView;
    String userId;
    TextView details;
    private Button ajouterInstruction;
    private Button retourProgrammes;

    String programmeId;

    private FirebaseAuth firebaseAuth;
    String prises ;
    public static final String PROG_ID = "programmeid";
    public static final String PATIENT_ID = "patientid";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulterprises);

        //Intent intent = getIntent();
        //final String progId = intent.getStringExtra(ConsulterprogrammesActivity.PROGRAMME_ID);
       // idProg = progId;
        prises="";
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId= user.getUid();
        mTextView = (TextView) findViewById(R.id.mTextView);
        details = (TextView) findViewById(R.id.details);
        ajouterInstruction = (Button) findViewById(R.id.ajouterInstruction);
        retourProgrammes = (Button) findViewById(R.id.retourProgrammes);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("prises");
        ajouterInstruction.setOnClickListener(this);
        retourProgrammes.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        final String progId = intent.getStringExtra(ConsulterprogrammesActivity.PROGRAMME_ID);
        final String nomprog = intent.getStringExtra(ConsulterprogrammesActivity.NOM);
        final String maladieprog = intent.getStringExtra(ConsulterprogrammesActivity.MALADIE);
        final String datedebutprog = intent.getStringExtra(ConsulterprogrammesActivity.DATEDEBUT);
        final int dureeprog = intent.getIntExtra(ConsulterprogrammesActivity.DUREE,1);

        details.setText("Nom de Programme: "+nomprog+"\n"+"Maladie: "+maladieprog+"\n"+"Date Debut: "+datedebutprog+"\n"+"Durée: "+Integer.toString(dureeprog)+"\n");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Prise prise = keyNode.getValue(Prise.class);

                    String numPrise = prise.getNumPrise();
                    String description = prise.getDescription();
                    String date = prise.getDate();
                    int qté = prise.getQté();
                    String heure = prise.getHeure();
                    programmeId = prise.getProgrammeId();
                    String refMed = prise.getRefMed();
                    String idPatient = prise.getIdPatient();

                  if ((idPatient.contentEquals(userId)) && (programmeId.contentEquals(progId))){
                     prises = prises + "Référence Médicament: "+refMed+"\n"+"Date: "+date+"\n"+"Heure: "+heure+"\n"+"Quantité: "+Integer.toString(qté)+"\n\n";
                    }
                    else{prises="aucune instruction pour le moment!";}

                    mTextView.setText(prises);


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
        if(v == retourProgrammes){

            //closing activity
            finish();
            //starting consultation activity
            startActivity(new Intent(this, ConsulterprogrammesActivity.class));
        }
        if(v == ajouterInstruction){
            //closing activity
            finish();

            //starting ajout activity
            Intent intent = new Intent(getApplicationContext(), AjouterprisesActivity.class);
            intent.putExtra(PATIENT_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
            intent.putExtra(PROG_ID, programmeId);

            startActivity(intent);

        }
    }
}
