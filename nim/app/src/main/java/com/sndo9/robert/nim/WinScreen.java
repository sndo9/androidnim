package com.sndo9.robert.nim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import static com.sndo9.robert.nim.SinglePlayer.f;
import static com.sndo9.robert.nim.SinglePlayer.runningScore;

public class WinScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        int runningScore;
        Boolean isOne;
        int turns;
        Boolean isAI;

        Button playAgain = (Button)findViewById(R.id.buttonPlayAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent single
            }
        });

        Intent call = getIntent();

        Bundle extras = call.getExtras();

        Log.w("WinScreen", "Launched");

        if(extras != null){
            if(extras.containsKey("winner") && extras.containsKey("score") && extras.containsKey("numTurns")){
                runningScore = extras.getInt("score");
                isOne = extras.getBoolean("winner");
                turns = extras.getInt("numTurns");
                isAI = extras.getBoolean("AI");

                int potentialPoints;

                Log.w("--------WinScreen Turns", "" + turns);
                Log.w("--------WinScreen score", "" + runningScore);

                potentialPoints = (turns - 3);
                Log.w("-------WinScreen.points", "" + potentialPoints);
                potentialPoints = potentialPoints * 2;
                Log.w("-------WinScreen.points", "" + potentialPoints);
                potentialPoints = 10 - potentialPoints;

                Log.w("-------WinScreen.points", "" + potentialPoints);

                TextView winnerText = (TextView)findViewById(R.id.winnerText);
                TextView scoreText = (TextView)findViewById(R.id.textViewScore);

                //Player one won against player two
                if(isOne && !isAI) {
                    winnerText.setText("Player One");
                    scoreText.setText("" + turns);
                }
                //AI won
                else if(isAI && !isOne) {
                    winnerText.setText("Phill the AI");
                    scoreText.setText("" + runningScore);
                    getHScore();
                    Boolean isHScore = checkHScore(runningScore);
                    if(isHScore) updateHScore();
                    runningScore = 0;
                }
                //Player two won
                else if(!isAI && !isOne) {
                    winnerText.setText("Player Two");
                    scoreText.setText("" + turns);
                }
                //Player one won and is playing the AI
                if(isOne && isAI) {
                    runningScore = runningScore + potentialPoints;
                    winnerText.setText("The Player");
                    scoreText.setText("" + runningScore);
                }

                final int passingScore = runningScore;

                playAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent playAgainIntent = new Intent(view.getContext(), SinglePlayer.class);
                        playAgainIntent.putExtra("score", passingScore);
                        startActivity(playAgainIntent);
                    }
                });

            }
        }
    }

    public void getHScore(){
        //get and parse scores
        int i = 0;

        String scores;
        String initals;

        int[] hScores = new int[10];
        String[] hInitals = new String[10];

        String defaultScore = "0000*0000*0000*0000*0000*0000*0000*0000*0000*0000";
        String defaultInitials = "XXX*XXX*XXX*XXX*XXX*XXX*XXX*XXX*XXX*XXX";

        SharedPreferences highscores = getSharedPreferences("highscore", 0);

        scores = highscores.getString("scores", defaultScore);
        initals = highscores.getString("initials", defaultInitials);

        StringTokenizer sT = new StringTokenizer(scores, "*");
        while(sT.hasMoreTokens()){
            hScores[i] = Integer.parseInt(sT.nextToken());
            i++;
        }

        i = 0;

        sT = new StringTokenizer(initals, "*");
        while(sT.hasMoreTokens()){
            hInitals[i] = sT.nextToken();
            i++;
        }


    }

    public boolean checkHScore(int points){
        return false;
    }

    public void updateHScore(){

    }

}
