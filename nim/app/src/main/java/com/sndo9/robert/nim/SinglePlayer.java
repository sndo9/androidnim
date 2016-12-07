package com.sndo9.robert.nim;

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
import static com.sndo9.robert.nim.GameLogic.pause;
import static com.sndo9.robert.nim.GameLogic.startGame;
import static com.sndo9.robert.nim.GameLogic.unPause;
import static com.sndo9.robert.nim.GameLogic.view;
import static com.sndo9.robert.nim.R.id.instructionPage;

public class SinglePlayer extends AppCompatActivity implements Instruction_Page.OnFragmentInteractionListener {

    private static Button information;

    protected static FragmentManager f;
    protected static Instruction_Page iPage = new Instruction_Page();

    protected static ArrayList<Tuple> highscore;

    protected int score;

    public static Button close;

    protected boolean pageOpen = false;

    protected View iPageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        //if not saved
        highscore = new ArrayList<>();

        score = 0;
        f = getSupportFragmentManager();

        information =(Button)findViewById(R.id.buttonInformation);



        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pageOpen) {
                    pageOpen = true;
                    pause();
                    FragmentTransaction fT = f.beginTransaction();
                    fT.add(R.id.container, iPage, "hi");

                    fT.commit();
                }
                else{
                    pageOpen = false;
                    unPause();
                    FragmentManager f = getSupportFragmentManager();
                    FragmentTransaction fT = f.beginTransaction();
                    fT.remove(iPage);
                    fT.commit();
                }

            }
        });



        View v = findViewById(R.id.activity_single_player);

        //Loop start game to track pts
        startGame(this, v);

        //Record high score

    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
