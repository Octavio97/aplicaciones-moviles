package com.example.p2mvc;

public class Student {
    //MODELO
    // Propiedades o variables miembro
    private int mNoControl;
    private String mName;
    private int mScore;

    //Constructor
    public Student(int mNoControl, String mName, int mScore) {
        this.mNoControl = mNoControl;
        this.mName = mName;
        this.mScore = mScore;
    }

    public int getNoControl() {
        return mNoControl;
    }

    public void setNoControl(int noControl) {
        mNoControl = noControl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}