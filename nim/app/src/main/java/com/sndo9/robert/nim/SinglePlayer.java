package com.sndo9.robert.nim;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.sndo9.robert.nim.GameLogic.pause;
import static com.sndo9.robert.nim.GameLogic.startGame;

public class SinglePlayer extends AppCompatActivity {

//    private ArrayList<stick> sticks = new ArrayList<stick>();
//    private int high = 11;
//    private int low = 0;
//    private int selection;
//
//    private Button uno;
//    private Button dos;
//    private Button tres;
//    private Button confirmB;
//    private Button cancel;

    private Button information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        information =(Button)findViewById(R.id.buttonInformation);

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
                Log.d("i Button", "Worked");
                FragmentManager f = getSupportFragmentManager();
                FragmentTransaction fT = f.beginTransaction();
                Instruction_Page iPage = new Instruction_Page();
                fT.add(R.id.container, iPage, "hi");
                fT.commit();
            }
        });

        View v = findViewById(R.id.activity_single_player);
        startGame(this, v);

        /*uno = (Button)findViewById(R.id.buttonUno);
        dos = (Button)findViewById(R.id.buttonDos);
        tres = (Button)findViewById(R.id.buttonTres);
        confirmB = (Button)findViewById(R.id.buttonConfirm);
        cancel = (Button)findViewById(R.id.buttonCancel);

        uno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selection = 1;
                select(1);
            }
        });

        dos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selection = 2;
                select(2);
            }
        });

        tres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selection = 3;
                select(3);
            }
        });

        confirmB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v ){
                confirm();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cancel();
            }
        });
        */
        

        //Will be moved to loop
        /*ImageView pic;
        int res = getResources().getIdentifier("stick1", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick one = new stick(pic);
        res = getResources().getIdentifier("stick2", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick two = new stick(pic);
        res = getResources().getIdentifier("stick3", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick three = new stick(pic);
        res = getResources().getIdentifier("stick4", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick four = new stick(pic);
        res = getResources().getIdentifier("stick5", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick five = new stick(pic);
        res = getResources().getIdentifier("stick6", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick six = new stick(pic);
        res = getResources().getIdentifier("stick7", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick seven = new stick(pic);
        res = getResources().getIdentifier("stick8", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick eight = new stick(pic);
        res = getResources().getIdentifier("stick9", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick nine = new stick(pic);
        res = getResources().getIdentifier("stick10", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick ten = new stick(pic);
        res = getResources().getIdentifier("stick11", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick eleven = new stick(pic);
        res = getResources().getIdentifier("stick12", "id", getPackageName());
        pic = (ImageView)findViewById(res);
        stick twelve = new stick(pic);
        sticks.add(one);
        sticks.add(two);
        sticks.add(three);
        sticks.add(four);
        sticks.add(five);
        sticks.add(six);
        sticks.add(seven);
        sticks.add(eight);
        sticks.add(nine);
        sticks.add(ten);
        sticks.add(eleven);
        sticks.add(twelve);
        */

    }

    //boolean hasEntered = false;

//    public void onClick(View v)
//    {
//        //This is here for testing. You may remove this code or use it elsewhere
//        Animation an = null;
//        if(!hasEntered) {
//            an = AnimationUtils.loadAnimation(this, R.anim.stick_selection);
//        }
//        else {
//            an = AnimationUtils.loadAnimation(this, R.anim.stick_removal);
//            v.setVisibility(View.GONE);
//        }
//        v.startAnimation(an);
//        hasEntered = !hasEntered;
//    }

//    public void select(int num) {
//        if(num == 1){
//            sticks.get(high).select();
//        }
//        if(num == 2){
//            sticks.get(high).select();
//            sticks.get(high - 1).select();
//        }
//        if(num == 3){
//            sticks.get(high).select();
//            sticks.get(high - 1).select();
//            sticks.get(high - 2).select();
//        }
//
//        uno.setVisibility(View.GONE);
//        dos.setVisibility(View.GONE);
//        tres.setVisibility(View.GONE);
//        confirmB.setVisibility(View.VISIBLE);
//        cancel.setVisibility(View.VISIBLE);
//    }
//
//    public void confirm(){
//        if(selection == 1){
//            sticks.get(high).makeInvisible();
//        }
//        if(selection == 2){
//            sticks.get(high).makeInvisible();
//            sticks.get(high - 1).makeInvisible();
//        }
//        if(selection == 3){
//            sticks.get(high).makeInvisible();
//            sticks.get(high - 1).makeInvisible();
//            sticks.get(high - 2).makeInvisible();
//        }
//        high = high - selection;
//        selection = 0;
//
//        uno.setVisibility(View.VISIBLE);
//        dos.setVisibility(View.VISIBLE);
//        tres.setVisibility(View.VISIBLE);
//        confirmB.setVisibility(View.GONE);
//        cancel.setVisibility(View.GONE);
//    }
//
//    public void cancel(){
//        if(selection == 1){
//            sticks.get(high).unSelect();
//        }
//        if(selection == 2){
//            sticks.get(high).unSelect();
//            sticks.get(high - 1).unSelect();
//        }
//        if(selection == 3){
//            sticks.get(high).unSelect();
//            sticks.get(high - 1).unSelect();
//            sticks.get(high - 2).unSelect();
//        }
//
//        uno.setVisibility(View.VISIBLE);
//        dos.setVisibility(View.VISIBLE);
//        tres.setVisibility(View.VISIBLE);
//        confirmB.setVisibility(View.GONE);
//        cancel.setVisibility(View.GONE);
//    }
//
//    public void win(){
//
//    }
}
