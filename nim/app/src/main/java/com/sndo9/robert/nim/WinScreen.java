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

import static com.sndo9.robert.nim.GameLogic.score;
import static com.sndo9.robert.nim.SinglePlayer.f;
import static com.sndo9.robert.nim.SinglePlayer.runningScore;

public class WinScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        int runningScore = 0;
        Boolean isOne = false;
        int turns = 0;
        Boolean isAI = false;

        Button playAgain = (Button)findViewById(R.id.buttonPlayAgain);
        Log.d("Processing", "Win");

        Intent call = getIntent();

        Bundle extras = call.getExtras();

        if(extras != null){
            if(extras.containsKey("winner") && extras.containsKey("score") && extras.containsKey("turns")){
                runningScore = extras.getInt("score");
                isOne = extras.getBoolean("winner");
                turns = extras.getInt("turns");
                isAI = extras.getBoolean("AI");

                Log.d("Processing", "Bundle");
                TextView winnerText = (TextView)findViewById(R.id.winnerText);

                //Player one won
                if(isOne) winnerText.setText("Player One");
                //AI won
                else if(isAI && !isOne) winnerText.setText("Phill the AI");
                //Player two won
                else if(!isAI && !isOne) winnerText.setText("Player Two");
                //Player one won and is playing the AI
                if(isOne && isAI) runningScore = runningScore + 1;
                //Player one lost and is playing the AI
                if(!isOne && isAI) getHScore();
            }
        }
    }

        //Check high scores

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

}
