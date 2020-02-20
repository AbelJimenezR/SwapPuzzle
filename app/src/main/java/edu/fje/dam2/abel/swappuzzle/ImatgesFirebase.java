package edu.fje.dam2.abel.swappuzzle;

import com.google.firebase.database.IgnoreExtraProperties;


public class ImatgesFirebase {
    private String valor;
    private String nom;

    public ImatgesFirebase(){
    }

    public ImatgesFirebase(String nom, String valor) {
        this.valor=valor;
    }



    public String getValor() {
        return valor;
    }
    public String getNom(){return nom;}



}