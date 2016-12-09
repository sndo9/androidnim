package com.sndo9.robert.nim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.id.content;
import static android.R.id.edit;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.sndo9.robert.nim.SinglePlayer.f;

/**
 * Created by sndo9 on 12/5/16.
 */

public class GameLogic extends AppCompatActivity {


    protected ArrayList<stick> arrayOne = new ArrayList<>();
    protected ArrayList<stick> arrayTwo = new ArrayList<>();
    protected ArrayList<stick> arrayThree = new ArrayList<>();

    protected AI computer;

    protected ImageButton confirm;
    protected ImageButton cancel;
    protected ImageButton icons;
    protected TextView infoTextField;
    protected TextView numOfTurns;

    protected View view;

    protected SinglePlayer context;

    protected boolean isPlayerOne;
    protected boolean hasSelected;

    protected ImageView newIcon;

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

        //Icon drawer listener
        View.OnClickListener getClickDrawable = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIcon = (ImageView)view;
                changeIcon(newIcon.getDrawable());
                context.findViewById(R.id.gameLayout).setVisibility(View.VISIBLE);
                context.findViewById(R.id.iconDrawer).setVisibility(View.GONE);
                context.findViewById(R.id.buttonChangIcon).setVisibility(View.VISIBLE);
                context.findViewById(R.id.buttonInformation).setVisibility(View.VISIBLE);
                context.findViewById(R.id.textNumOfTurns).setVisibility(View.VISIBLE);
            }
        };

        //Icon Drawer
        String word = "icon";
        //Favorite line of code in this project
        for(int i = 0; i < 8; i++) view.findViewById(context.getResources().getIdentifier(word + i, "id", context.getPackageName())).setOnClickListener(getClickDrawable);

        //Find and hold buttons
        confirm = (ImageButton)view.findViewById(R.id.buttonConfirm);
        cancel = (ImageButton)view.findViewById(R.id.buttonCancel);
        icons = (ImageButton)view.findViewById(R.id.buttonChangIcon);
        icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.findViewById(R.id.gameLayout).setVisibility(View.GONE);
                context.findViewById(R.id.iconDrawer).setVisibility(View.VISIBLE);
                context.findViewById(R.id.buttonChangIcon).setVisibility(View.GONE);
                context.findViewById(R.id.buttonInformation).setVisibility(View.GONE);
                context.findViewById(R.id.textNumOfTurns).setVisibility(View.GONE);
            }
        });
        infoTextField = (TextView) view.findViewById(R.id.helpText);
        infoTextField.setText("Player 1's turn");
        numOfTurns = (TextView)view.findViewById(R.id.textNumOfTurns);

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
        confirm.setImageDrawable(context.getDrawable(R.drawable.circlethin));
        cancel.setEnabled(false);
        cancel.setImageDrawable(context.getDrawable(R.drawable.circlethinblack));
    }

    public void enableButtons(){
        confirm.setEnabled(true);
        confirm.setImageDrawable(context.getDrawable(R.drawable.checkedblue));
        cancel.setEnabled(true);
        cancel.setImageDrawable(context.getDrawable(R.drawable.cancel));
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
        if(useAI && !isPlayerOne) {
            saveState();
            numOfTurns.setText("Number of Turns: " + turns);
        }
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

        editSave.putString("numTurns", "" + turns);
        editSave.commit();

        if(isOver){
            editSave.remove("rowOneSave");
            editSave.remove("rowTwoSave");
            editSave.remove("rowThreeSave");
            editSave.remove("numTurns");
            editSave.commit();
        }
    }

    public void resumeState(){
        //Fixes close on win screen
        if(save.getString("rowOneSave", "000").equals("111") && save.getString("rowTwoSave", "00000").equals("11111") && save.getString("rowThreeSave", "1111111").equals("1111111")) return;
        String rowString = save.getString("rowOneSave", "000");
        for(int i = 0; i < arrayOne.size(); i++){
            arrayOne.get(i).fromString(Character.getNumericValue(rowString.charAt(i)));
        }
        Log.w("-----------resuming", rowString);
        rowString = save.getString("rowTwoSave", "00000");
        for(int i = 0; i < arrayTwo.size(); i++){
            arrayTwo.get(i).fromString(Character.getNumericValue(rowString.charAt(i)));
        }
        Log.w("-----------resuming", rowString);
        rowString = save.getString("rowThreeSave", "0000000");
        for(int i = 0; i < arrayThree.size(); i++){
            arrayThree.get(i).fromString(Character.getNumericValue(rowString.charAt(i)));
        }
        Log.w("-----------resuming", rowString);

        turns = Integer.parseInt(save.getString("numTurns", "1"));
    }

    public void changeIcon(Drawable newPic){
        for(int i = 0; i < arrayOne.size(); i++){
            arrayOne.get(i).changeImage(newPic);
        }
        for(int i = 0; i < arrayTwo.size(); i++){
            arrayTwo.get(i).changeImage(newPic);
        }
        for(int i = 0; i < arrayThree.size(); i++){
            arrayThree.get(i).changeImage(newPic);
        }
    }
}
