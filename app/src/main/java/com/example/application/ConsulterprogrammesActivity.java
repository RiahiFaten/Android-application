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

public class ConsulterprogrammesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    ListView mListView;
    String userId;
    //String idP;
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> noms = new ArrayList<>();
    ArrayList<String> maladies = new ArrayList<>();
    ArrayList<Integer> durées = new ArrayList<>();
    ArrayList<String> datesdebuts = new ArrayList<>();


    private FirebaseAuth firebaseAuth;
    ArrayList<String> programmes = new ArrayList<String>();
    public static final String PROGRAMME_ID = "programmeid";
    public static final String NOM = "nom";
    public static final String MALADIE = "maladie";
    public static final String DUREE = "durée";
    public static final String DATEDEBUT = "datedebut";


    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulterprogrammes);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId= user.getUid();
      //////////////  m = (TextView) findViewById(R.id.m);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("programmes");

        //////////programmes = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.listView);
        programmes.add("Titre                   Date Début              Durée");

        mListView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Programme prog = keyNode.getValue(Programme.class);

                    //programmes.add(prog);

                    String nom = prog.getNom();
                    String maladie = prog.getMaladie();
                    int durée = prog.getDurée();
                    String date_debut = prog.getDate_debut();
                    String idP = prog.getIdP();
                    String idPatient = prog.getIdPatient();

                    if (idPatient.contentEquals(userId)) {
                        String programme = nom + "    " + date_debut + "               " + Integer.toString(durée);
                        programmes.add(programme);
                        ids.add(idP);
                        noms.add(nom);
                        maladies.add(maladie);
                        datesdebuts.add(date_debut);
                        durées.add(durée);
                    }


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConsulterprogrammesActivity.this,
                        android.R.layout.simple_list_item_1, programmes);
                mListView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //closing activity
        finish();

        String i=ids.get(position-1);
        String n=noms.get(position-1);
        String m=maladies.get(position-1);
        String dd=datesdebuts.get(position-1);
        int d=durées.get(position-1);

        Intent intent = new Intent(getApplicationContext(), ConsulterprisesActivity.class);
        intent.putExtra(PROGRAMME_ID, i);
        intent.putExtra(NOM, n);
        intent.putExtra(MALADIE, m);
        intent.putExtra(DATEDEBUT, dd);
        intent.putExtra(DUREE, d);

        startActivity(intent);
    }
}


