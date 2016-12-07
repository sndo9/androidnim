package com.sndo9.robert.nim;

import android.util.Log;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Created by Squiggs on 12/6/2016.
 */

public class AI {

    public boolean isPlayerOne;

    public AI(boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
    }

    public void doTurn(ArrayList<stick>... rows) {
        int x = getRand(rows.length-1) ;
        Log.w("AI", "I'm taking my turn");

        int totalSticks = 3 + (x*2);
        int remainingSticks = totalSticks;
        for(stick s: rows[x]) {
            if(s.isRemoved)
                remainingSticks--;
        }

        if(remainingSticks == 0) {
            Log.w("AI", "restarting doTurn");
            doTurn(rows);
        }

        selectSticksToRemove(rows[x], remainingSticks, totalSticks);
        Log.w("AI", "Done Selecting sticks");
    }


    private void selectSticksToRemove(ArrayList<stick> arrayOne, int sticksToRemove, int totalNumSticks) {
        int numSticks = getRand(sticksToRemove);
        if(numSticks == 0)
            numSticks = 1;

        Log.w("AI", "Removing " + numSticks + "/" + totalNumSticks + " sticks\n-----------------------");
        for(int x = 0; x < numSticks; x++) {
            int stickToRemove = -1;
            do {
                stickToRemove = getRand(totalNumSticks-1);
                Log.w("AI", "Trying to remove " +stickToRemove);
            } while(arrayOne.get(stickToRemove).isRemoved || arrayOne.get(stickToRemove).isSelected);

            Log.w("AI", "Removing stick " + sticksToRemove);
            arrayOne.get(stickToRemove).select();
        }



    }

    private int getRand(int seed) {
        return (int) Math.round(Math.random() * seed);
    }
}

