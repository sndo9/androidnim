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


    /**
     * ArrayList holding first row of sticks
     */
    protected ArrayList<stick> arrayOne = new ArrayList<>();
    /**
     * ArrayList holding second row of sticks
     */
    protected ArrayList<stick> arrayTwo = new ArrayList<>();
    /**
     * ArrayList holding third row of sticks
     */
    protected ArrayList<stick> arrayThree = new ArrayList<>();

    /**
     * Instance of the computer AI
     */
    protected AI computer;

    /**
     * Imagebutton for confirm
     */
    protected ImageButton confirm;
    /**
     * Imagebutton for cancel
     */
    protected ImageButton cancel;
    /**
     * Imagebutton for icons
     */
    protected ImageButton icons;
    /**
     * Textview for telling the user whos turn it is
     */
    protected TextView infoTextField;
    /**
     * Textview for number of turns
     */
    protected TextView numOfTurns;

    /**
     * Holds the view of the Activity that calls GameLogic
     */
    protected View view;

    /**
     * Holds activity that calls this function
     */
    protected SinglePlayer context;

    /**
     * Boolean keeping track of who's turn it is
     */
    protected boolean isPlayerOne;
    /**
     * variable tracking if a stick has been selected
     */
    protected boolean hasSelected;

    /**
     * Imageview holding the new icon
     */
    protected ImageView newIcon;

    /**
     * Count of the number of existing sticks
     */
    protected int count;
    /**
     * The Points left.
     */
    protected int ptsLeft;
    /**
     * The Score
     */
    protected int score;
    /**
     * The number of turns.
     */
    protected int turns;
    /**
     * Boolean of if the ai is on
     */
    private boolean useAI;
    /**
     * Tracks if the game is over
     */
    private boolean isOver = false;

    /**
     * shared prefrences for the save state of the game
     */
    protected SharedPreferences save;
    /**
     * The save state prefrences
     */
    protected SharedPreferences.Editor editSave;

    /**
     * Instantiates a new Game logic.
     *
     * @param context of prevous activity
     * @param view of previous activity
     */
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

    /**
     * Method to start the game
     */
    public void startGame(){
        String identifier;
        int res;

        ImageView newImageView;

        //Creates Row one
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
        //creates Row two
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
        //creates Row three
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
        //If the AI is on resume the previous game state if there is one
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
        //Resets the sticks
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                resetSelect();
            }
        });
    }

    /**
     * Register touch of a stick
     *
     * @param row      the row of the stick
     * @param position the position of the stick
     */
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

    /**
     * Unselects given stick
     * @param selection stick to be unselected
     */
    private void unSelectStick(stick selection) {
        if(selection.unSelect())
            if(count != 0) count = count - 1;
    }

    /**
     * Selects given stick
     * @param selection stick to be selected
     */
    private void selectStick(stick selection) {
        if(selection.select()) {
            registerTouch(selection.getRow(), selection.getPosition());
        }
    }

    /**
     * Disables confirm and cancel buttons
     */
    public void unTouch(){
        if(count > 0) count = count - 1;
        if(count == 0) disableButtons();
    }

    /**
     * Disable buttons.
     */
    public void disableButtons(){
        confirm.setEnabled(false);
        confirm.setImageDrawable(context.getDrawable(R.drawable.circlethin));
        cancel.setEnabled(false);
        cancel.setImageDrawable(context.getDrawable(R.drawable.circlethinblack));
    }

    /**
     * Enable buttons.
     */
    public void enableButtons(){
        confirm.setEnabled(true);
        confirm.setImageDrawable(context.getDrawable(R.drawable.checkedblue));
        cancel.setEnabled(true);
        cancel.setImageDrawable(context.getDrawable(R.drawable.cancel));
    }

    /**
     * Reset selection
     */
    public  void resetSelect(){
        resetSelectRow(arrayOne);
        resetSelectRow(arrayTwo);
        resetSelectRow(arrayThree);

        disableButtons();
    }

    /**
     * Reset selected list
     *
     * @param list the list to be reset
     */
    public void resetSelectRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++)
            unSelectStick(list.get(i));
    }

    /**
     * End turn.
     *
     * @param stacksToCheck the stacks to check
     */
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

    /**
     * Check win.
     */
    public void checkWin(){
        if(checkAllSticksRemoved(arrayOne, arrayTwo, arrayThree)) {
            if(!isOver) {
                itsOverGoHome();
                context.endGame(isPlayerOne, context, turns, useAI);
            }
        }
    }

    /**
     * Check all sticks removed boolean.
     *
     * @param rows the rows
     * @return the true if all sticks are removed
     */
    public static boolean checkAllSticksRemoved(ArrayList<stick>... rows) {
        for(ArrayList<stick> r: rows) {
            for(stick s: r) {
                if (!s.isRemoved)
                    return false;
            }
        }
        return true;
    }

    /**
     * pauses all icons
     */
    public void pause(){
        pauseRow(arrayOne);
        pauseRow(arrayTwo);
        pauseRow(arrayThree);
    }

    /**
     * Pauses icons in a row.
     *
     * @param list the list
     */
    public static void pauseRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++) list.get(i).disable();
    }

    /**
     * unpauses all icons
     */
    public void unPause(){
        unPauseRow(arrayOne);
        unPauseRow(arrayTwo);
        unPauseRow(arrayThree);
    }

    /**
     * unpauses all icons in a row
     *
     * @param list the list
     */
    public static void unPauseRow(ArrayList<stick> list){
        for(int i = 0; i < list.size(); i++) list.get(i).enable();
    }

    /**
     * Turns on te Ai
     *
     * @param playWithAI the play with ai
     */
    public void turnAiOn(boolean playWithAI) {
        this.useAI = playWithAI;
    }

    /**
     * Sets isOver
     * Its over go home.
     */
    public void itsOverGoHome() {
        isOver = true;
    }

    /**
     * Saves game state to shared prefrences
     */
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

    /**
     * Resume state from shared prefrences
     */
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

    /**
     * Changes icon.
     *
     * @param newPic the pic to be changed to
     */
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
