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
import android.widget.ImageView;

import java.util.ArrayList;

import static android.R.attr.fragment;
import static com.sndo9.robert.nim.R.id.instructionPage;

public class SinglePlayer extends AppCompatActivity implements Instruction_Page.OnFragmentInteractionListener {

    public static final String WITH_AI = "PLAY_WITH_THE_AI_ON";
    private static Button information;

    protected static FragmentManager f;
    protected static Instruction_Page iPage = new Instruction_Page();

    protected int score;

    protected int runningScore;

    public static Button close;

    protected boolean pageOpen = false;
    protected GameLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        Intent call = getIntent();

        Bundle extras = call.getExtras();
        boolean playWithAI = true; // Default to true

        if(extras != null){
            //if(extras.containsKey("score")) runningScore = extras.getInt("score");
            if(extras.containsKey(WITH_AI)) playWithAI = extras.getBoolean(WITH_AI);
        }
        //else runningScore = 0;

        SharedPreferences save = getSharedPreferences("save", 0);

        runningScore = Integer.parseInt(save.getString("points", "0"));

        logic = new GameLogic(this, findViewById(R.id.activity_single_player));
        logic.turnAiOn(playWithAI);

        //if not saved
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

    public void endGame(Boolean playerOne, Context c, int turns, Boolean isAI){

        int passingTurns = turns++ + 1;

        Log.w("-----------SinglePlayer", "Launching win screen");
        Log.w("-----SinglePlayer.turns", "" + turns);
        Log.w("-----SinglePlayer.score", "" + runningScore);
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
