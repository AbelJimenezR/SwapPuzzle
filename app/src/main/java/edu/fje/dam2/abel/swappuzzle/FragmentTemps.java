package edu.fje.dam2.abel.swappuzzle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Chronometer;

public class FragmentTemps extends Fragment {

    private Chronometer crono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temps,
                container, false);


        crono = (Chronometer) getActivity().findViewById(R.id.crono);

        return view;
    }

}