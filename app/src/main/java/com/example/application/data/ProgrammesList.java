package com.example.application.data;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.application.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProgrammesList extends ArrayAdapter<Programme> {

        private Activity context;
        private ArrayList<Programme> programmes;

        public ProgrammesList(Activity context, ArrayList<Programme> programmes) {
            super((Context) context, R.layout.list_programmes_layout, programmes);
            this.context = (Activity) context;
            this.programmes = programmes;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.list_programmes_layout, null, true);

            TextView nom = (TextView) listViewItem.findViewById(R.id.nom);
            TextView date_debut = (TextView) listViewItem.findViewById(R.id.date_debut);
            TextView durée = (TextView) listViewItem.findViewById(R.id.durée);
            TextView maladie = (TextView) listViewItem.findViewById(R.id.maladie);


            Programme programme = programmes.get(position);
            nom.setText(programme.getNom());
            date_debut.setText(programme.getDate_debut());
            durée.setText(programme.getDurée());
            maladie.setText(programme.getMaladie());


            return listViewItem;
        }
    }

