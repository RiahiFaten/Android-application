package com.example.application;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewWelcome;
    private Button buttonLogout;
    private Button consulterProgrammes; // consultation
    private Button ajouterProgrammes; // ajout
    private Button activerRappels; // ajout
    private Button consulterTemperatures;


    public static final String PATIENT_ID = "patientid"; // ajout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createNotificationChannel(); //notif

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        consulterProgrammes = (Button) findViewById(R.id.consulterProgrammes);
        ajouterProgrammes = (Button) findViewById(R.id.ajouterProgrammes);
        activerRappels = (Button) findViewById(R.id.activerRappels);
        consulterTemperatures = (Button) findViewById(R.id.consulterTemperatures);



        //displaying logged in user name
        textViewWelcome.setText("Bienvenue "+ user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        consulterProgrammes.setOnClickListener(this);
        ajouterProgrammes.setOnClickListener(this);
        activerRappels.setOnClickListener(this);
        consulterTemperatures.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(view == consulterProgrammes){

            //closing activity
            finish();
            //starting consultation activity
            startActivity(new Intent(this, ConsulterprogrammesActivity.class));
        }
        if(view == ajouterProgrammes){
            //closing activity
            finish();

            //starting ajout activity
            Intent intent = new Intent(getApplicationContext(), AjouterprogrammesActivity.class);//ajout
            intent.putExtra(PATIENT_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());//ajout
            startActivity(intent);//ajout

        }
        if(view == activerRappels){
            Toast.makeText(this, "Rappels Activés", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NotificationBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();
            long tensecondsinmillis = 1000*10;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 10);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tensecondsinmillis, pendingIntent);

        }

        if(view == consulterTemperatures){

            //closing activity
            finish();
            //starting consultation activity
            startActivity(new Intent(this, TemperatureActivity.class));
        }
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NotifChannel";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifier", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}