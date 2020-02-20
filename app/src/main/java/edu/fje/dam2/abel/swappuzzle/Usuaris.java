package edu.fje.dam2.abel.swappuzzle;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

public class Usuaris {
    private String nom;
    private String puntuacio;

    public Usuaris(){
    }

    public Usuaris(String nom, String puntuacio) {
        this.nom = nom;
        this.puntuacio = puntuacio;
    }


    public String getNom() {
        return nom;
    }

    public String getPuntuacio() {
        return puntuacio;
    }
}