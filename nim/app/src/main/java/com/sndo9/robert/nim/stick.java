package com.sndo9.robert.nim;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by rober on 12/3/2016.
 */

public class stick extends AppCompatActivity {
    protected boolean visibility;
    protected boolean isSelected;
    //protected String id;
    protected ImageView image;

    public stick(ImageView stickPic) {
        visibility = true;
        image = stickPic;
        image.setVisibility(View.VISIBLE);
        image.setPivotX(image.getWidth() / 2);
        image.setPivotY(image.getHeight() / 2);

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
            image.setRotation(45);
        }
    }

    public void unSelect() {
        if(isSelected) {
            isSelected = false;
            image.setRotation(0);
        }
    }
}
