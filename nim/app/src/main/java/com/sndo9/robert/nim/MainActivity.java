package com.sndo9.robert.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This is the main screen for the app. It allows the user to choose whether to face another player or an AI
 */
public class MainActivity extends AppCompatActivity {


    /**
     * Button allowing user to pick AI
     */
    private Button single;
    /**
     * Button allowing user to pick play with a friend
     */
    private Button multi;
    /**
     * Intent for the single player
     */
    private Intent singlePlayer;
    /**
     * Intent for multiplayer
     */
    private Intent multiplayer;

    /**
     * Creates the screen and interface for the main screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set intents and bundle boolean to specify opponent
        singlePlayer = new Intent(this, SinglePlayer.class);
        singlePlayer.putExtra(SinglePlayer.WITH_AI, true);
        multiplayer = new Intent(this, SinglePlayer.class);
        multiplayer.putExtra(SinglePlayer.WITH_AI, false);

        //Attach UI to Button variables
        single = (Button)findViewById(R.id.singleplayer);
        multi = (Button)findViewById(R.id.multiplayer);

        //Launch game with AI playing the user
        single.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(singlePlayer);
            }
        });
        //Launch game with two players playing
        multi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(multiplayer);
            }
        });
    }

}
