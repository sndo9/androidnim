package com.sndo9.robert.nim;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

import static com.sndo9.robert.nim.GameLogic.score;
import static com.sndo9.robert.nim.SinglePlayer.runningScore;

public class WinScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        HashSet<Tuple> highScore = new HashSet<>();

        int runningScore = 0;
        Boolean isOne = false;
        int turns = 0;
        Boolean isAI = false;

        String scores;
        String initals;

        Intent call = getIntent();

        Bundle extras = call.getExtras();

        if(extras != null){
            if(extras.containsKey("winner") && extras.containsKey("score") && extras.containsKey("turns")){
                runningScore = extras.getInt("score");
                isOne = extras.getBoolean("winner");
                turns = extras.getInt("turns");

                TextView winnerText = (TextView)findViewById(R.id.winnerText);

                if(isOne) winnerText.setText("Player One");
                else if(isAI && !isOne) winnerText.setText("Phill the AI");
                else if(!isAI && !isOne) winnerText.setText("Player Two");

                if(isOne && isAI) runningScore = runningScore + 1;

                //Check high scores
            }
        }

        //Check high scores


    }

}
