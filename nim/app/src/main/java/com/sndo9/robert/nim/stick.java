package com.sndo9.robert.nim;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.R.attr.visibility;

/**
 * Created by rober on 12/3/2016.
 */
public class stick
{
//    /**
//     * The Visibility.
//    */
//    protected boolean visibility;//Remove
    /**
     * Is the stick selected.
     */
    protected boolean isSelected;
    /**
     * The row the stick is in
     */
    protected int row;
    /**
     * The number of the stick in the row
     */
    protected int position;
    /**
     * The Image of the stick
     */
    protected ImageView image;

    /**
     * The view
     */
    protected View thisV;

    /**
     * The animation to rotate the image 45 degrees
     */
    protected Animation rotate;
    /**
     * The animation to rotate the image back 45 degrees
     */
    protected Animation rotateBack;
    /**
     * The animation to remove the image
     */
    protected Animation remove;
    /**
     * has the stick been removed
     */
    public boolean isRemoved = false;

    /**
     * Instantiates a new Stick.
     *
     * @param stickPic      the stick picture
     * @param givenRow      the given row of the stick
     * @param givenPosition the given position of the stick
     * @param c             the context of the caller
     */
    public stick(ImageView stickPic, int givenRow, int givenPosition, Context c) {
        setRotations(c);
        //visibility = true;
        image = stickPic;
        image.setVisibility(View.VISIBLE);
        row = givenRow;
        position = givenPosition;
        thisV = image;
    }

//    /**
//     * Make visible boolean.
//     *
//     * @return the boolean
//     */
////Undo selection
//    public boolean makeVisible() {
//        if (visibility == false) {
//            visibility = true;
//            image.setVisibility(View.VISIBLE);
//            return true;
//        } else
//            return false;
//    }
//
////    /**
////     * Make invisible boolean.
////     *
////     * @return the boolean
////     */
//////Stick is selected
////    public boolean makeInvisible() {
////        if (visibility == true) {
////            visibility = false;
////            image.setVisibility(View.INVISIBLE);
////            return true;
////        } else
////            return false;
////    }

//    /**
//     * Is visible boolean.
//     *
//     * @return the boolean
//     */
//    public boolean isVisible() {
//        return visibility;
//    }

    /**
     * Unselects the stick
     *
     * @return true if the stick was unselected, false otherwise
     */
    public boolean select() {
        if (!isSelected) {
            isSelected = true;
            thisV.startAnimation(rotate);
            return true;
        }
        return false;
    }

    /**
     * Unselects the stick
     *
     * @return true if the stick was unselected, false otherwise
     */
    public boolean unSelect() {
        if(isSelected) {
            isSelected = false;
            thisV.startAnimation(rotateBack);
            return true;
        }
        return false;
    }

    /**
     * Returns isSelected
     *
     * @return isSelected
     */
    public boolean isSelected(){
        return isSelected;
    }

    /**
     * Removes the stick
     */
    public void remove(){
        image.setOnClickListener(null);
        thisV.startAnimation(remove);
        image.setVisibility(View.GONE);
        isRemoved = true;
        isSelected = false;
    }

    /**
     * Disables the stick
     */
    public void disable(){
        image.setEnabled(false);
    }

    /**
     * Enables the stick
     */
    public void enable(){
        image.setEnabled(true);
    }

    /**
     * Sets the animation variables
     * @param context of the caller
     */
    private void setRotations(Context c){
        rotate = AnimationUtils.loadAnimation(c, R.anim.stick_selection);
        rotateBack = AnimationUtils.loadAnimation(c, R.anim.stick_unselect);
        remove = AnimationUtils.loadAnimation(c, R.anim.stick_removal);
    }

    /**
     * Converts string settings to a string
     * @return
     */
    public String toString(){
        String output = "";
        if(isRemoved) output = output + 1;
        else output = output + 0;

        return output;
    }

    /**
     * Takes string ad sets string settings
     *
     * @param input the input
     */
    public void fromString(int input){
        if(input == 1) remove();
    }

    /**
     * Gets position of the stick.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets row of the stick.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Change image of the stick.
     *
     * @param pic the pic
     */
    public void changeImage(Drawable pic){
        image.setImageDrawable(pic);
    }

}
