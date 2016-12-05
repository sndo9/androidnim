package com.sndo9.robert.nim;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.R.attr.x;

/**
 * Created by sndo9 on 12/5/16.
 */

public class GameLogic extends AppCompatActivity {

    protected int[] rowOne = new int[3];
    protected int[] rowTwo = new int[5];
    protected int[] rowThree = new int[7];

    protected static ArrayList<stick> arrayOne = new ArrayList<>();
    protected static ArrayList<stick> arrayTwo = new ArrayList<>();
    protected static ArrayList<stick> arrayThree = new ArrayList<>();

    public static void startGame(Context c, View v){

        //Upward limit on position number per row,
        //row 1 = 6, 2 = 7, 3 = 8
        int k = 6;
        //Starting position for items in current row
        //row 1 = 3, 2 = 2, 3 = 1;
        int l = 3;

        String identifier;

        int m;
        int res;

        ImageView newImageView;
        stick newStick;

        //Iterate through each row
//        for(int row = 1; row < 4; row++){
//            m = l;
//            //Create items for each row
//            //Runs 3 times for row one, 5 times for row two, and 7 times for row three
//            for(int position = l; position < k; position++){
//                identifier = "string" + row + position;
//
//                //Debug code
//                Log.d("String identifier", identifier);
//
//                //Find ImageViews and create stick objects
//                res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
//                newImageView = (ImageView)v.findViewById(res);
//                newStick = new stick(newImageView,row,position);
//            }
//
//            //Update iteration values
//            k = k + 1;
//            l = l - 1;
//        }
        //Row one
        for(int i = 3; i < 6; i++){
            identifier = "stick1" + i;
            res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
            newImageView = (ImageView)v.findViewById(res);
            newStick = new stick(newImageView, 1, i);
            arrayOne.add(newStick);
        }
        //Row two
        for(int i = 2; i < 7; i++){
            identifier = "stick2" + i;
            res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
            newImageView = (ImageView)v.findViewById(res);
            newStick = new stick(newImageView, 2, i);
            arrayTwo.add(newStick);
        }
        //Row three
        for(int i = 1; i < 8; i++){
            identifier = "stick3" + i;
            res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
            newImageView = (ImageView)v.findViewById(res);
            newStick = new stick(newImageView, 3, i);
            arrayThree.add(newStick);
        }
    }

    public static void registerTouch(int row, int position){
        if(row == 1){
            for(int i = 0; i < arrayTwo.size(); i++){
                if(arrayTwo.get(i).isSelected()) {
                    arrayTwo.get(i).unSelect();
                }
            }
            for(int i = 0; i < arrayThree.size(); i++){
                if(arrayThree.get(i).isSelected()) {
                    arrayThree.get(i).unSelect();
                }
            }

        }
        if(row == 2){
            
        }
    }

    public void checkOtherRows(int givenRow){
        if(givenRow == 1){

        }
    }
}