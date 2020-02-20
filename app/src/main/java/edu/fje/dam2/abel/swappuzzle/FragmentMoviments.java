package edu.fje.dam2.abel.swappuzzle;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Chronometer;
import android.widget.TextView;

import java.text.CollationElementIterator;

public class FragmentMoviments extends Fragment {
    TextView moviments;
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temps,
                container, false);

        text= view.findViewById(R.id.text);
        moviments = view.findViewById(R.id.moviments);

        return view;
    }

    public void afegirMoviment (String nom){
        moviments.setText(nom);
    }
}






