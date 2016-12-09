package com.sndo9.robert.nim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.R.attr.fragment;
import static com.sndo9.robert.nim.R.id.instructionPage;

/**
 * This activity creates the game and decides if the AI is to be used
 */
public class SinglePlayer extends AppCompatActivity implements Instruction_Page.OnFragmentInteractionListener {

    /**
     * The constant WITH_AI. This is stores the string tag for the extra specifying AI usage
     */
    public static final String WITH_AI = "PLAY_WITH_THE_AI_ON";
    /**
     * Holds the information button
     */
    private static Button information;

    /**
     * Holds the fragment manager used for the information page
     */
    protected static FragmentManager f;
    /**
     * Holds the fragment for the information page
     */
    protected static Instruction_Page iPage = new Instruction_Page();
    /**
     * The Running score, gets passed on to win screen to record score.
     */
    protected int runningScore;
    /**
     * Boolean recording if the information page is open
     */
    protected boolean pageOpen = false;
    /**
     * Instance of the game logic
     */
    protected GameLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        //Getting extras from intent call. If extra exists set playWithAI
        Intent call = getIntent();
        Bundle extras = call.getExtras();
        boolean playWithAI = true; // Default to true
        if(extras != null){
            if(extras.containsKey(WITH_AI)) playWithAI = extras.getBoolean(WITH_AI);
        }
        //Get prefrences from save file and set running score
        SharedPreferences save = getSharedPreferences("save", 0);
        runningScore = Integer.parseInt(save.getString("points", "0"));
        //Create game logic instance for user to play
        logic = new GameLogic(this, findViewById(R.id.activity_single_player));
        logic.turnAiOn(playWithAI);

        //Set fragment manager, button, and click listener to open and close the instruction page
        f = getSupportFragmentManager();
        information =(Button)findViewById(R.id.buttonInformation);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pageOpen) {
                    pageOpen = true;
                    logic.pause();
                    FragmentTransaction fT = f.beginTransaction();
                    fT.add(R.id.container, iPage, "hi");
                    fT.commit();
                }
                else{
                    pageOpen = false;
                    logic.unPause();
                    FragmentManager f = getSupportFragmentManager();
                    FragmentTransaction fT = f.beginTransaction();
                    fT.remove(iPage);
                    fT.commit();
                }
            }
        });

        logic.startGame();

    }

    /**
     * End game.
     *
     * @param playerOne Boolean telling endGame if it was player one's turn when called
     * @param c         Context of activity calling endgame
     * @param turns     The number of turns that it took someone to win
     * @param isAI      Boolean telling endGame if the AI was playing
     */
    public void endGame(Boolean playerOne, Context c, int turns, Boolean isAI){

        int passingTurns = turns++ + 1;
        //Create intent for the win screen and bundle information needed for the score screen
        Intent goToWin = new Intent(c, WinScreen.class);
        Bundle extra = new Bundle();
        extra.putInt("score", runningScore);
        extra.putBoolean("winner", playerOne);
        extra.putInt("numTurns", passingTurns);
        extra.putBoolean("AI", isAI);
        goToWin.putExtras(extra);
        c.startActivity(goToWin);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
