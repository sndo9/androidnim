package com.sndo9.robert.nim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.StringTokenizer;

import static android.R.id.edit;

public class WinScreen extends AppCompatActivity {

    protected int[] hScores = new int[10];
    protected String[] hInitals = new String[10];
    protected String ini;
    protected int index;
    protected SharedPreferences highscores;
    protected int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        int runningScore;
        Boolean isOne;
        int turns;
        final Boolean isAI;

        Button playAgain = (Button)findViewById(R.id.buttonPlayAgain);
        final Button mainMenu = (Button)findViewById(R.id.buttonMainMenu);

        SharedPreferences save = getSharedPreferences("save", 0);
        SharedPreferences.Editor editSave = save.edit();

        editSave.remove("rowOneSave");
        editSave.remove("rowTwoSave");
        editSave.remove("rowThreeSave");
        editSave.remove("numTurns");
        editSave.commit();


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


                //AI won
                if(!isOne && isAI) {
                    winnerText.setText("Phill the AI");
                    scoreText.setText("" + runningScore + " pts");
                    playAgain.setVisibility(View.INVISIBLE);
                    getHScore();
                    Boolean isHScore = checkHScore(runningScore);
                    if(isHScore) updateHScore(runningScore);
                    runningScore = 0;
                }
                //Player one won and is playing the AI
                else if(isOne && isAI) {
                    runningScore = runningScore + potentialPoints;
                    winnerText.setText("The Player");
                    scoreText.setText("" + runningScore + " pts");
                }
                //Player one won against player two
                else if(isOne && !isAI) {
                    winnerText.setText("Player One");
                    scoreText.setText("In " + turns + " turns");
                }
                //Player two won
                else if(!isAI && !isOne) {
                    winnerText.setText("Player Two");
                    scoreText.setText("In " + turns + " turns");
                }

                final int passingScore = runningScore;

                playAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent playAgainIntent = new Intent(view.getContext(), SinglePlayer.class);
                        playAgainIntent.putExtra("score", passingScore);
                        playAgainIntent.putExtra("PLAY_WITH_THE_AI_ON", isAI);
                        startActivity(playAgainIntent);
                    }
                });

                mainMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mainMenuIntent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(mainMenuIntent);
                    }
                });
                editSave.putString("points", "" + runningScore);
                editSave.commit();
            }
        }
    }

    public void getHScore(){
        //get and parse scores
        int i = 0;

        String scores;
        String initals;

        String defaultScore = "0010*0009*0008*0007*0006*0005*0004*0003*0002*00001";
        String defaultInitials = "JTK*JLP*CKJ*CJA*CBS*SCT*WEC*WCV*REG*DWP";

        highscores = getSharedPreferences("highscore", 0);

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
        boolean isHigher = false;

        for(int i = 0; i < hScores.length; i++){
            if(hScores[i] < points) {
                if(!isHigher) {
                    isHigher = true;
                    index = i;
                }

            }
        }
        return isHigher;
    }

    public void updateHScore(final int points){
        final int[] newIntArray = new int[11];
        final String[] newStringArray = new String[11];
        final EditText initals = (EditText)findViewById(R.id.enterName);
        initals.setVisibility(View.VISIBLE);

        Button submitName = (Button)findViewById(R.id.buttonSubmitName);
        submitName.setVisibility(View.VISIBLE);
        //Need to check for more then three characters
        submitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ini = initals.getText().toString();
                boolean notSet = true;

                for(int i = 0; i < 11; i++){
                    newIntArray[i] = hScores[j];
                    newStringArray[i] = hInitals[j];
                    if(points > hScores[j] && notSet){
                        newIntArray[j + 1] = points;
                        newStringArray[j + 1] = ini;
                        i++;
                        notSet = false;
                    }
                    j++;
                }

                for(int i = 0; i < 10; i++){
                    hScores[i] = newIntArray[i + 1];
                    hInitals[i] = newStringArray[i + 1];
                }

                saveHScores();
                showHScores();
            }
        });
    }

    public void saveHScores(){
        StringBuilder sBI = new StringBuilder();
        StringBuilder sBS = new StringBuilder();
        for(int i = 0; i < hScores.length; i++) {
            sBI.append("" + hScores);
            sBS.append("" + hInitals);
            sBI.append("*");
            sBS.append("*");
        }
        highscores.edit().putString("scores", sBI.toString());
        highscores.edit().putString("initials", sBS.toString());
    }

    public void showHScores(){
        int res;
        int j = 1;

        String ident;

        TextView person;

        RelativeLayout layoutOne = (RelativeLayout)findViewById(R.id.winScreenLayout);
        layoutOne.setVisibility(View.GONE);

        LinearLayout layoutTwo = (LinearLayout)findViewById(R.id.highscores);
        layoutTwo.setVisibility(View.VISIBLE);

        Button cont = (Button)findViewById(R.id.buttonContinue);
        cont.setVisibility(View.VISIBLE);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent menu = new Intent(view.getContext(), MainActivity.class);
                startActivity(menu);
            }
        });

        for(int i = 0; i < 10; i++){
            ident = "rank" + j;
            res = getResources().getIdentifier(ident, "id", getPackageName());
            person = (TextView)findViewById(res);
            person.setText("" + hScores[i] + ": " + hInitals[i]);
            j++;
        }
    }

}
