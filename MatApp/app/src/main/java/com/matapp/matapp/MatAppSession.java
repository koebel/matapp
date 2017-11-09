package com.matapp.matapp;

/**
 * Created by rafael on 08.11.17.
 */

public class MatAppSession {
    private static MatAppSession instance = null;

    public String listKey;
    public String listName;
    public boolean listWriteable;

    protected MatAppSession() {
        //Exists only to defeat instantiation
    }

    public static MatAppSession getInstance() {
        if(instance == null) {
            instance = new MatAppSession();
        }
        return instance;
    }
}
