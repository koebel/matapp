package com.matapp.matapp;

/**
 * Created by rafael on 08.11.17.
 */

public class MatAppSession {
    private static MatAppSession instance = null;

    public String listKey = null;
    public String listName = null;
    public boolean listWriteable = false;

    protected MatAppSession() {
        //Exists only to defeat instantiation
    }

    public static MatAppSession getInstance() {
        if(instance == null) {
            instance = new MatAppSession();
        }
        return instance;
    }

    public String getListName() {
        return listName;
    }
}
