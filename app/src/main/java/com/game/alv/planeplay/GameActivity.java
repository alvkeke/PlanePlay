package com.game.alv.planeplay;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

    //定义获取界面大小的变量
    public DisplayMetrics dm;
    //定义界面View变量
    public TextView txtStatus;
    public ImageView imgBackground;
    public ImageView imgGameArea;
    //定义画布
    public Canvas canvas;
    public Bitmap bitmap;
    //定义画笔
    private Paint paintBullet;
    private Paint paintPlayer;
    private Paint paintEnemy;
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

        //获取窗口界面大小
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //获取界面View
        txtStatus = findViewById(R.id.game_txtTop);
        imgBackground = findViewById(R.id.game_img_background);
        imgGameArea = findViewById(R.id.game_imgGameArea);

        //给游戏区域添加触摸相应
        imgGameArea.setOnTouchListener(new gameAreaTouch());
        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //新建画布
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        imgGameArea.setImageBitmap(bitmap);
        paintBullet = new Paint();
        paintBullet.setColor(Color.GREEN);
        paintEnemy = new Paint();
        paintEnemy.setColor(Color.RED);
        paintPlayer = new Paint();
        paintPlayer.setColor(Color.BLUE);

        //初始化玩家
        player = new Plane();
        player.MoveTo(dm.widthPixels/2,dm.heightPixels - MyConstants.PlaneR * 4);
        //初始化敌人数组,此时敌人数量为0
        enemies = new ArrayList<>();

        //TODO:刷新一次游戏界面
        flashView();
        //打开游戏线程
        new Thread(new GameThread(GameActivity.this)).start();

    }

    class gameAreaTouch implements View.OnTouchListener{

        private boolean canMove = false;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //玩家射击
            //player.Shoot();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(event.getX() <= player.getPosition().x + MyConstants.PlaneR * 2){
                        if(event.getX() >= player.getPosition().x - MyConstants.PlaneR * 2){
                            if(event.getY() >= player.getPosition().y - MyConstants.PlaneR * 2){
                                if(event.getY() <= player.getPosition().y + MyConstants.PlaneR * 2){
                                    //如果点击位置在飞机附近,则可以移动
                                    canMove = true;
                                }
                            }
                        }
                    }

                    break;
                case MotionEvent.ACTION_MOVE:
                    if(canMove) {
                        player.MoveTo(event.getX(), event.getY());
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    canMove =false;
                    break;
            }

            return true;
        }
    }

    void flashView(){

        //清空界面
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        imgGameArea.setImageBitmap(bitmap);

        //显示玩家
        canvas.drawCircle(player.getPosition().x, player.getPosition().y,
                MyConstants.PlaneR, paintPlayer);

        //显示敌人
        for(int i = 0; i<enemies.size(); i++){
            canvas.drawCircle(enemies.get(i).getPosition().x, enemies.get(i).getPosition().y,
                    MyConstants.PlaneR, paintEnemy);
        }

        //显示玩家子弹
        ArrayList<Point> p = player.getBulletPos();
        for(int i = 0; i<p.size(); i++){
            canvas.drawCircle(p.get(i).x, p.get(i).y,
                    MyConstants.BulletR, paintBullet);
        }
        //TODO:显示敌人子弹

        //更新状态栏
        CharSequence strStatus = "HP:" + player.getHp() + "  Score:" + player.getScore();
        txtStatus.setText(strStatus);

    }

}
