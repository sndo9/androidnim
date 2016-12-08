package com.sndo9.robert.nim;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Squiggs on 12/6/2016.
 */

public class AI {

    public boolean isPlayerOne;

    public boolean isOver = false;

    public AI(boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
    }

    public int doTurn(ArrayList<stick>... rows) {
        int row = getRand(rows.length - 1);
        Log.w("AI", "I'm taking my turn");

        int totalSticks = 3 + (row * 2);
        int remainingSticks = totalSticks;
        for (stick s : rows[row]) {
            if (s.isRemoved)
                remainingSticks--;
        }

        if (remainingSticks == 0) {
            if (GameLogic.checkAllSticksRemoved(rows)) {
                Log.w("AI", "No more moves");
                return 0;
            }
            Log.w("AI", "restarting doTurn");

            return doTurn(rows);
        }

        Log.w("AI", "Remaining Sticks " + remainingSticks);
        selectSticksToRemove(rows[row], remainingSticks, totalSticks);
        Log.w("AI", "Done Selecting sticks");

        return row;
    }


    private void selectSticksToRemove(ArrayList<stick> arrayOne, int sticksToRemove, int totalNumSticks) {
        int numSticks = getRand(sticksToRemove - 1) + 1;
        //if(numSticks == 0)
        //    numSticks = 1;

        Log.w("AI", "Removing " + numSticks + "/" + sticksToRemove + "/" + totalNumSticks + " sticks\n-----------------------");
        for (int x = 0; x < numSticks; x++) {
            int stick = -1;
            do {
                stick = getRand(totalNumSticks - 1);
                Log.w("AI", "Trying to remove " + stick);
            } while (arrayOne.get(stick).isRemoved || arrayOne.get(stick).isSelected);

            Log.w("AI", "Removing stick " + stick);

            arrayOne.get(stick).select();
        }


    }

    private int getRand(int seed) {
        return (int) Math.round(Math.random() * seed);
    }


}

