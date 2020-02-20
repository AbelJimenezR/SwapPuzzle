package edu.fje.dam2.abel.swappuzzle;

import com.google.firebase.database.IgnoreExtraProperties;


public class ImatgesFirebase {
    private String imatge;

    public ImatgesFirebase(){
    }

    public ImatgesFirebase(String imatge) {
        this.imatge = imatge;
    }


    public String getImatge() {
        return imatge;
    }

}