package com.sndo9.robert.nim;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Wesley on 12/6/2016.
 *
 * AI is a class that deals with what the AI does in the app. The AI currently implemented is one that
 * has random chance as its choosing mechanism, but by extending this class and overriding the doTurn() method,
 * other AI classes can be formed.
 */

public class AI {

    /**
     * Determines if this AI is player 1. true means it is, and false means it isn't
     */
    public boolean isPlayerOne;

    public AI(boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
    }

    /**
     * This method will select all the sticks that this AI wants to select
     * @param rows a list of all the rows the AI can choose from
     * @return the row that was chosen and had sticks selected from
     */
    public int doTurn(ArrayList<stick>... rows) {
        int row = getRand(rows.length - 1);
        Log.w("AI", "I'm taking my turn");

        //Count how many sticks are left in this row
        int totalSticks = 3 + (row * 2);
        int remainingSticks = totalSticks;
        for (stick s : rows[row]) {
            if (s.isRemoved)
                remainingSticks--;
        }

        if (remainingSticks == 0) {
            //Check to see if all sticks are removed
            if (GameLogic.checkAllSticksRemoved(rows)) {
                Log.w("AI", "No more moves");
                return 0;
            }
            Log.w("AI", "restarting doTurn");

            //Try randomly selecting another row that hopefully has sticks in it. One ahs to exist
            return doTurn(rows);
        }

        Log.w("AI", "Remaining Sticks " + remainingSticks);
        selectSticksToRemove(rows[row], remainingSticks, totalSticks);
        Log.w("AI", "Done Selecting sticks");

        return row;
    }

    /**
     * This method will use the stick's select method to randomly select a stick from a given row
     * @param arrayOne the row that a stick will be selected from
     * @param sticksToRemove how many sticks there are possible to remove
     * @param totalNumSticks the maximum amount of sticks in the row
     */
    private void selectSticksToRemove(ArrayList<stick> arrayOne, int sticksToRemove, int totalNumSticks) {
        //Get how many sticks the AI wants to remove
        int numSticks = getRand(sticksToRemove - 1) + 1;

        Log.w("AI", "Removing " + numSticks + "/" + sticksToRemove + "/" + totalNumSticks + " sticks\n-----------------------");
        for (int x = 0; x < numSticks; x++) {
            int stick;
            do {
                stick = getRand(totalNumSticks - 1);
                Log.w("AI", "Trying to remove " + stick);
                //Loop back is choice is invalid
            } while (arrayOne.get(stick).isRemoved || arrayOne.get(stick).isSelected);

            Log.w("AI", "Removing stick " + stick);

            arrayOne.get(stick).select();
        }


    }

    /**
     * Helper method to deal with getting a random number.
     * @param seed the seed to multiply the random result by
     * @return a random value between 0-seed
     */
    private int getRand(int seed) {
        return (int) Math.round(Math.random() * seed);
    }


}

