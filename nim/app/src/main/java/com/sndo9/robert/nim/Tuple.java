package com.sndo9.robert.nim;

/**
 * Created by sndo9 on 12/7/16.
 */

public class Tuple {

    protected int score;
    protected String name;

    public Tuple(int s, String n){
        score = s;
        name = n;
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }
}
