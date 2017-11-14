package com.matapp.matapp;

/**
 * Created by rafael on 08.11.17.
 */

public class MatAppSession {
    private static MatAppSession instance = null;

    private String listKey = null;
    private String listName = null;
    private boolean listWriteable = false;

    protected MatAppSession() {
        //Exists only to defeat instantiation
    }

    public static MatAppSession getInstance() {
        if(instance == null) {
            instance = new MatAppSession();
        }
        return instance;
    }

    /* Getter & Setter */
    public String getListKey() {
        return listKey;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public boolean isListWriteable() {
        return listWriteable;
    }

    public void setListWriteable(boolean listWriteable) {
        this.listWriteable = listWriteable;
    }
}
