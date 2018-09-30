package com.game.alv.planeplay;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class GameActivity extends AppCompatActivity{

    //定义界面View变量
    public TextView txtStatus;
    public ImageView imgBackground;
    public ImageView imgGameArea;
    //定义画布
    public Canvas canvas;
    public Bitmap bitmap;
    //定义玩家变量
    public Plane player;
    //定义敌人变量
    public ArrayList<Enemy> enemies;

    @SuppressLint("ClickableViewAccessibility")//我也不懂为什么每次都要添加这个
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //隐藏标题栏
        Objects.requireNonNull(getSupportActionBar()).hide();
        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //获取界面View
        txtStatus = findViewById(R.id.game_txtTop);
        imgBackground = findViewById(R.id.game_img_background);
        imgGameArea = findViewById(R.id.game_imgGameArea);

        //初始化玩家
        player = new Plane();
        //初始化敌人数组,此时敌人数量为0
        enemies = new ArrayList<>();

        //给游戏区域添加触摸相应
        imgGameArea.setOnTouchListener(new gameAreaTouch());
        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        imgGameArea.setImageBitmap(bitmap);
        //TODO:刷新一次游戏界面

        //打开游戏线程
        new Thread(new GameThread(GameActivity.this)).start();

    }

    class gameAreaTouch implements View.OnTouchListener{

        private float oldX, oldY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            player.Shoot();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    oldX = event.getX();
                    oldY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    player.Move(event.getX()- oldX, event.getY()- oldY);
                    oldX = event.getX();
                    oldY = event.getY();

                    break;
            }

            return true;
        }
    }

}
