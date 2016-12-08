package com.sndo9.robert.nim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by sndo9 on 12/5/16.
 */

public class GameLogic extends AppCompatActivity {


    protected ArrayList<stick> arrayOne = new ArrayList<>();
    protected ArrayList<stick> arrayTwo = new ArrayList<>();
    protected ArrayList<stick> arrayThree = new ArrayList<>();

    protected AI computer;

    protected Button confirm;
    protected Button cancel;
    protected TextView infoTextField;

    protected View view;

    protected SinglePlayer context;

    protected boolean isPlayerOne;
    protected boolean hasSelected;

    protected int count;
    protected int ptsLeft;
    protected int score;
    protected int turns;
    private boolean useAI;
    private boolean isOver = false;

    protected SharedPreferences save;
    protected SharedPreferences.Editor editSave;

    public GameLogic(SinglePlayer c, View v) {
        count = ptsLeft = score = turns = 0;
        context = c;
        view = v;
        computer = new AI(false);

        isPlayerOne = true;
        hasSelected = false;
        useAI = true;

        //Find and hold buttons
        confirm = (Button)view.findViewById(R.id.buttonConfirm);
        cancel = (Button)view.findViewById(R.id.buttonCancel);
        infoTextField = (TextView) view.findViewById(R.id.helpText);
        infoTextField.setText("Player 1's turn");

        save = context.getSharedPreferences("save", 0);
        editSave = save.edit();
    }

    public void startGame(){
        String identifier;
        int res;

        ImageView newImageView;

        //Row one
        for(int i = 3; i < 6; i++){
            identifier = "stick1" + i;
            res = context.getResources().getIdentifier(identifier, "id", context.getPackageName());
            newImageView = (ImageView)view.findViewById(res);
            final stick newStick = new stick(newImageView, 1, i, context);
            newImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(!newStick.isSelected()){
                        selectStick(newStick);
                    }
                    else {
                        unSelectStick(newStick);

                    }
                }
            });
            arrayOne.add(newStick);
        }
        //Row two
        for(int i = 2; i < 7; i++){
            identifier = "stick2" + i;
            res = context.getResources().getIdentifier(identifier, "id", context.getPackageName());
            newImageView = (ImageView)view.findViewById(res);
            final stick newStick = new stick(newImageView, 2, i, context);
            newImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(!newStick.isSelected()){
                        selectStick(newStick);
                    }
                    else {
                        unSelectStick(newStick);

                    }
                }
            });
            arrayTwo.add(newStick);
        }
        //Row three
        for(int i = 1; i < 8; i++){
            identifier = "stick3" + i;
            res = context.getResources().getIdentifier(identifier, "id", context.getPackageName());
            newImageView = (ImageView)view.findViewById(res);
            final stick newStick = new stick(newImageView, 3, i, context);
            newImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(!newStick.isSelected()){
                        selectStick(newStick);
                    }
                    else {
                        unSelectStick(newStick);

                    }
                }
            });
            arrayThree.add(newStick);
        }

        if(useAI) resumeState();

        //Cancel and confirm touch listeners
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Next turn
                endTurn(arrayOne, arrayTwo, arrayThree);
                isPlayerOne = !isPlayerOne;

                if(useAI) {
                    if (computer.isPlayerOne == isPlayerOne) {
                        infoTextField.setText("Computer's Turn");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                int rowSelected = computer.doTurn(arrayOne, arrayTwo, arrayThree);
                                endTurn(arrayOne, arrayTwo, arrayThree);
                                isPlayerOne = !isPlayerOne;
                                infoTextField.setText("Your turn");
                                turns++;
                                Log.w("GameLogic.turns", "" + turns);
                            }
                        }, 1000);

                    }
                } else {
                    if(isPlayerOne) {
                        infoTextField.setText("Player 1's turn");
                        turns++;
                        Log.w("GameLogic.turns", "" + turns);
                    } else {
                        infoTextField.setText("Player 2's turn");
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reset current turn
                resetSelect();
            }
        });
    }

    public void registerTouch(int row, int position){
        count = count + 1;
        enableButtons();

        if(row == 1){
            for(int i = 0; i < arrayTwo.size(); i++){
                if(arrayTwo.get(i).isSelected()) {
                    unSelectStick(arrayTwo.get(i));
                }
            }
            for(int i = 0; i < arrayThree.size(); i++){
                if(arrayThree.get(i).isSelected()) {
                    unSelectStick(arrayThree.get(i));
                }
            }
        }
        if(row == 2){
            for(int i = 0; i < arrayOne.size(); i++){
                if(arrayOne.get(i).isSelected()){
                    unSelectStick(arrayOne.get(i));
                }
            }
            for(int i = 0; i < arrayThree.size(); i++){
                if(arrayThree.get(i).isSelected()){
                    unSelectStick(arrayThree.get(i));
                }
            }
        }
        if(row == 3) {
            for(int i = 0; i < arrayOne.size(); i++){
                if(arrayOne.get(i).isSelected()){
                    unSelectStick(arrayOne.get(i));
                }
            }
            for(int i = 0; i < arrayTwo.size(); i++){
                if(arrayTwo.get(i).isSelected()){
                    unSelectStick(arrayTwo.get(i));
                }
            }
        }
    }

    private void unSelectStick(stick selection) {
        if(selection.unSelect())
            if(count != 0) count = count - 1;
    }


    private void selectStick(stick selection) {
        if(selection.select()) {
            registerTouch(selection.getRow(), selection.getPosition());
        }
    }

    public void unTouch(){
        if(count > 0) count = count - 1;
        if(count == 0) disableButtons();
    }

    public void disableButtons(){
        confirm.setEnabled(false);
        cancel.setEnabled(false);
    }

    public void enableButtons(){
        confirm.setEnabled(true);
        cancel.setEnabled(true);
    }

    public  void resetSelect(){
        resetSelectRow(arrayOne);
        resetSelectRow(arrayTwo);
        resetSelectRow(arrayThree);

        disableButtons();
    }

    public void resetSelectRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++)
            unSelectStick(list.get(i));
    }

    public void endTurn(ArrayList<stick>... stacksToCheck){
        for(ArrayList<stick> stack: stacksToCheck) {
            for(stick s: stack) {
                if(s.isSelected) {
                    s.remove();
                    ptsLeft = ptsLeft - 1;
                }
            }
        }
        disableButtons();
        Log.w("saveState", "Near");
        if(useAI && !isPlayerOne) saveState();
        checkWin();
    }

    public void checkWin(){
        if(checkAllSticksRemoved(arrayOne, arrayTwo, arrayThree)) {
            if(!isOver) {
                itsOverGoHome();
                context.endGame(isPlayerOne, context, turns, useAI);
            }
        }
    }

    public static boolean checkAllSticksRemoved(ArrayList<stick>... rows) {
        for(ArrayList<stick> r: rows) {
            for(stick s: r) {
                if (!s.isRemoved)
                    return false;
            }
        }
        return true;
    }

    public void pause(){
        pauseRow(arrayOne);
        pauseRow(arrayTwo);
        pauseRow(arrayThree);
    }

    public static void pauseRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++) list.get(i).disable();
    }

    public void unPause(){
        unPauseRow(arrayOne);
        unPauseRow(arrayTwo);
        unPauseRow(arrayThree);
    }

    public static void unPauseRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++) list.get(i).enable();
    }

    public void turnAiOn(boolean playWithAI) {
        this.useAI = playWithAI;
    }

    public void itsOverGoHome() {
        isOver = true;
    }

    public void saveState(){
        Log.w("saveState", "Reached");
        String rowString = "";
        for(int i = 0; i < arrayOne.size(); i++){
            rowString = rowString + arrayOne.get(i).toString();
        }
        editSave.putString("rowOneSave", rowString);
        rowString = "";
        for(int i = 0; i < arrayTwo.size(); i++){
            rowString = rowString + arrayTwo.get(i).toString();
        }
        editSave.putString("rowTwoSave", rowString);
        rowString = "";
        for(int i = 0; i < arrayThree.size(); i++){
            rowString = rowString + arrayThree.get(i).toString();
        }
        Log.w("-----------saving", rowString);
        editSave.putString("rowThreeSave", rowString);
        editSave.commit();

        if(isOver){
            editSave.remove("rowOneSave");
            editSave.remove("rowTwoSave");
            editSave.remove("rowThreeSave");
            editSave.commit();
        }
    }

    public void resumeState(){
        Log.w("Resume", "In Resume");
        String rowString = save.getString("rowOneSave", "000");
        for(int i = 0; i < arrayOne.size(); i++){
            arrayOne.get(i).fromString(Character.getNumericValue(rowString.charAt(i)));
        }
        rowString = save.getString("rowTwoSave", "00000");
        for(int i = 0; i < arrayTwo.size(); i++){
            arrayTwo.get(i).fromString(Character.getNumericValue(rowString.charAt(i)));
        }
        rowString = save.getString("rowThreeSave", "0000000");
        for(int i = 0; i < arrayThree.size(); i++){
            arrayThree.get(i).fromString(Character.getNumericValue(rowString.charAt(i)));
        }
        Log.w("-----------resuming", rowString);
    }
}
