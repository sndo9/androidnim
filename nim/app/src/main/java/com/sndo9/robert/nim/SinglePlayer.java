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

    private static Button information;

    protected static FragmentManager f;
    protected static Instruction_Page iPage = new Instruction_Page();

    protected static ArrayList<Tuple> highscore;

    protected int score;

    protected static int runningScore;

    public static Button close;

    protected boolean pageOpen = false;
    protected GameLogic logic;

    protected View iPageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        Intent call = getIntent();

        Bundle extras = call.getExtras();

        if(extras != null){
            if(extras.containsKey("score")) runningScore = extras.getInt("score");
        }
        else runningScore = 0;

        //if not saved
        highscore = new ArrayList<>();
        logic = new GameLogic(this, findViewById(R.id.activity_single_player));
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

        //Loop start game to track pts
        logic.startGame();

    }

    public void endGame(Boolean playerOne, int turns){
        Intent goToWin = new Intent(this, WinScreen.class);
        Bundle extra = new Bundle();
        extra.putInt("score", runningScore);
        extra.putBoolean("winner", playerOne);
        extra.putInt("numTurns", turns);
        goToWin.putExtras(extra);
        startActivity(goToWin);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
