package com.sndo9.robert.nim;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.R.attr.x;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static java.lang.Compiler.disable;
import static java.lang.Compiler.enable;

/**
 * Created by sndo9 on 12/5/16.
 */

public class GameLogic extends AppCompatActivity {

    protected static ArrayList<stick> arrayOne = new ArrayList<>();
    protected static ArrayList<stick> arrayTwo = new ArrayList<>();
    protected static ArrayList<stick> arrayThree = new ArrayList<>();
    protected static ArrayList<stick> last;

    protected static Button confirm;
    protected static Button cancel;

    protected static View view;

    protected static Context context;

    protected static Intent winScreen;

    protected static boolean isPlayerOne;
    protected static boolean hasSelected;

    protected static int count;
    protected static int ptsLeft;

    public static void startGame(Context c, View v){

        view = v;
        context = c;

        String identifier;
        int res;
        count = 0;

        ImageView newImageView;
        stick newStick;

        isPlayerOne = true;
        hasSelected = false;

        //Find and hold buttons
        confirm = (Button)v.findViewById(R.id.buttonConfirm);
        cancel = (Button)v.findViewById(R.id.buttonCancel);

        winScreen = new Intent(c, WinScreen.class);

        //Row one
        for(int i = 3; i < 6; i++){
            identifier = "stick1" + i;
            res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
            newImageView = (ImageView)v.findViewById(res);
            newStick = new stick(newImageView, 1, i, c);
            arrayOne.add(newStick);
        }
        //Row two
        for(int i = 2; i < 7; i++){
            identifier = "stick2" + i;
            res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
            newImageView = (ImageView)v.findViewById(res);
            newStick = new stick(newImageView, 2, i, c);
            arrayTwo.add(newStick);
        }
        //Row three
        for(int i = 1; i < 8; i++){
            identifier = "stick3" + i;
            res = c.getResources().getIdentifier(identifier, "id", c.getPackageName());
            newImageView = (ImageView)v.findViewById(res);
            newStick = new stick(newImageView, 3, i, c);
            arrayThree.add(newStick);
        }

        //Cancel and confirm touch listeners
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Next turn
                endTurn();
                isPlayerOne = !isPlayerOne;

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reset current turn
                reset();
            }
        });
    }

    public static void registerTouch(int row, int position){
        count = count + 1;
        enableButtons();

        if(row == 1){
            last = arrayOne;
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
            last = arrayTwo;
            for(int i = 0; i < arrayOne.size(); i++){
                if(arrayOne.get(i).isSelected()){
                    arrayOne.get(i).unSelect();
                }
            }
            for(int i = 0; i < arrayThree.size(); i++){
                if(arrayThree.get(i).isSelected()){
                    arrayThree.get(i).unSelect();
                }
            }
        }
        if(row == 3) {
            last = arrayThree;
            for(int i = 0; i < arrayOne.size(); i++){
                if(arrayOne.get(i).isSelected()){
                    arrayOne.get(i).unSelect();
                }
            }
            for(int i = 0; i < arrayTwo.size(); i++){
                if(arrayTwo.get(i).isSelected()){
                    arrayTwo.get(i).unSelect();
                }
            }
        }
    }

    public static void unTouch(){
        if(count > 0) count = count - 1;
        if(count == 0) disableButtons();
    }

    public static void disableButtons(){
        confirm.setEnabled(false);
        cancel.setEnabled(false);
    }

    public static void enableButtons(){
        confirm.setEnabled(true);
        cancel.setEnabled(true);
    }

    public static void reset(){
        resetRow(arrayOne);
        resetRow(arrayTwo);
        resetRow(arrayThree);

        last = null;
        disableButtons();
    }

    public static void resetRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++) list.get(i).unSelect();
    }

    public static void endTurn(){
        for(int i = 0; i < last.size(); i++){
            if(last.get(i).isSelected()) {
                last.get(i).remove();
                ptsLeft = ptsLeft - 1;
            }
        }
        disableButtons();
        checkWin();
    }

    public static void checkWin(){
        if(ptsLeft == 0) {
            winScreen.putExtra("winner", isPlayerOne);
            context.startActivity(winScreen);
        }
    }

    public static void pause(){
        pauseRow(arrayOne);
        pauseRow(arrayTwo);
        pauseRow(arrayThree);
    }

    public static void pauseRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++) list.get(i).disable();
    }
}
