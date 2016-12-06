package com.sndo9.robert.nim;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static com.sndo9.robert.nim.GameLogic.count;
import static com.sndo9.robert.nim.GameLogic.registerTouch;
import static com.sndo9.robert.nim.GameLogic.unTouch;

/**
 * Created by rober on 12/3/2016.
 */

public class stick extends AppCompatActivity {
    protected boolean visibility;
    protected boolean isSelected;
    protected int row;
    protected int position;
    protected ImageView image;

    protected View thisV;

    protected Animation rotate;
    protected Animation rotateBack;
    protected Animation remove;

    public stick(ImageView stickPic, int givenRow, int givenPosition, Context c) {

        rotate = AnimationUtils.loadAnimation(c, R.anim.stick_selection);
        rotateBack = AnimationUtils.loadAnimation(c, R.anim.stick_unselect);
        remove = AnimationUtils.loadAnimation(c, R.anim.stick_removal);

        visibility = true;
        image = stickPic;
        image.setVisibility(View.VISIBLE);

        row = givenRow;
        position = givenPosition;

        thisV = image;

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isSelected){
                    select();
                }
                else {
                    unSelect();
                }
            }
        });

    }

    //Undo selection
    public boolean makeVisible() {
        if (visibility == false) {
            visibility = true;
            image.setVisibility(View.VISIBLE);
            return true;
        } else
            return false;
    }

    //Stick is selected
    public boolean makeInvisible() {
        if (visibility == true) {
            visibility = false;
            image.setVisibility(View.INVISIBLE);
            return true;
        } else
            return false;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void select() {
        if (!isSelected) {
            isSelected = true;
            thisV.startAnimation(rotate);
            registerTouch(row, position);
        }
    }

    public void unSelect() {
        if(isSelected) {
            isSelected = false;
            thisV.startAnimation(rotateBack);
            if(count != 0) count = count - 1;
        }
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void remove(){
        thisV.startAnimation(remove);
        image.setVisibility(View.GONE);
        isSelected = false;
    }

    public void disable(){
        image.setEnabled(false);
    }

    public void enable(){
        image.setEnabled(true);
    }
}
