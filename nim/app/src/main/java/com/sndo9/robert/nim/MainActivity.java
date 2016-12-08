package com.sndo9.robert.nim;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button single;
    private Button multi;

    private Intent singlePlayer;
    private Intent multiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singlePlayer = new Intent(this, SinglePlayer.class);
        singlePlayer.putExtra(SinglePlayer.WITH_AI, true);
        multiplayer = new Intent(this, SinglePlayer.class);
        multiplayer.putExtra(SinglePlayer.WITH_AI, false);

        single = (Button)findViewById(R.id.singleplayer);
        multi = (Button)findViewById(R.id.multiplayer);

        single.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(singlePlayer);
            }
        });

        multi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(multiplayer);
            }
        });

    }

}
