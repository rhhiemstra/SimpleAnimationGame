package com.example.robert.simpleanimationgame;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    ImageView beeImg;
    AnimationDrawable beeAnimation;

    TextView scoreTv, levelTv, messageTv;

    int scoreInt, levelInt =0;

    ImageView flowerImg;

    ImageButton startBtn, playAgianBtn;

    View touchView;
    String msg="Hey I guess you got it or whatever.";

    int screenWidth, screenHeight, flrInx, up, beeHeight, beeWidth =0;
    int speed = 10;
    float beeX, beeY;

    int [] flowerArr = {R.drawable.flower_blue, R.drawable.flower_green, R.drawable.flower_orange,
                    R.drawable.flower_pink, R.drawable.flower_purple, R.drawable.flower_red};

    Random flowerPos = new Random();

    boolean start=true;
    boolean hitFlower = false;

    Rect beeRect, flowerRect;

    int beeDown = 20;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beeRect = new Rect();
        flowerRect = new Rect();

        beeImg = findViewById(R.id.beeImg);
        beeImg.setBackgroundResource(R.drawable.bee);
        this.beeAnimation = (AnimationDrawable)beeImg.getBackground();
        beeAnimation.start();


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        messageTv = findViewById(R.id.messageTv);
        scoreTv = findViewById(R.id.score);
        levelTv = findViewById(R.id.level);
        flowerImg = findViewById(R.id.flower);
        startBtn = findViewById(R.id.startImg);
        playAgianBtn = findViewById(R.id.playAgian);
        touchView = findViewById(R.id.touchView);

        beeImg.setY(screenHeight/3);
        beeImg.setX(screenWidth/3+300);

        beeX = beeImg.getX();
        beeY = beeImg.getY();
        startBtn.setVisibility(View.VISIBLE);
        messageTv.setText("");

        setFlower(flowerImg);



        scoreTv.setText(Integer.toString(scoreInt));
        levelTv.setText(Integer.toString(levelInt));

        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (start){
                    up = 150;
                }
                return true;
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start=true;
                startBtn.setVisibility(View.GONE);
                move();
            }
        });

        playAgianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start=true;
                beeDown = 20;
                setFlower(flowerImg);
                beeImg.setY(screenHeight/3);
                beeImg.setX(screenWidth/3+300);
                messageTv.setText("");
                playAgianBtn.setVisibility(View.GONE);
                move();
            }
        });


    }

    class Mover implements Runnable{

        @Override
        public void run() {
            move();

        }
    }


    public void move(){
        flowerImg.setX(flowerImg.getX()-speed);
        flowerImg.getHitRect(flowerRect);
        beeImg.getHitRect(beeRect);


        beeY = beeImg.getY();

        if(Rect.intersects(beeRect, flowerRect)){
            flowerImg.setVisibility(View.GONE);
            hitFlower = true;
            scoreInt+=10;
            msg = "Hey I guess you got it or whatever.";
            scoreTv.setText(Integer.toString(scoreInt));

        }
        if (scoreInt>=levelInt*100){
            levelInt+=1;
            levelTv.setText(Integer.toString(levelInt));
        }
        if (hitFlower || flowerImg.getX()<0){
            if(flowerImg.getX()<0){
                msg="Ya Fucked up son!";
            }
            messageTv.setText(msg);
            hitFlower = false;
            setFlower(flowerImg);
        }

        if (beeY > beeHeight && beeY < screenHeight - beeHeight)
            beeImg.setY(beeY+beeDown-up);
        else{
            playAgianBtn.setVisibility(View.VISIBLE);
            msg = "Wow how'd you fuck that up?";
            messageTv.setText(msg);
            beeDown = 0;
            start = false;
        }
        if (up > 0)
            up = 0;
        if(start)
            touchView.postDelayed(new Mover(), 30);

    }
    public void setFlower(ImageView flowerImg) {
        flowerImg.setVisibility(View.VISIBLE);
        if(flrInx < 6){
            flowerImg.setImageResource(flowerArr[flrInx]);
            flrInx++;
        }else flrInx = 0;

        int y = flowerPos.nextInt(screenHeight-300);
        flowerImg.setX(screenWidth);
        if(y < 300)
            y+=300;
        flowerImg.setY(y);
    }




}
//if(beeImg.getY() <= screenHeight)
//           beeImg.setY(beeImg.getY()+speed);

