package com.game.alv.planeplay;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.ArrayList;

//游戏运行线程,用来调控画面和数据类
public class GameThread implements Runnable {

    //保存传入的指针
    private GameActivity gameActivity;

    GameThread(GameActivity gameActivity1){
        gameActivity = gameActivity1;
    }

    @Override
    public void run() {
        //当玩家死亡时借书进程
        while(gameActivity.player.getHp()>0){

            //延时执行
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //子弹移动
            gameActivity.player.bulletMove();
            //释放已经没用的子弹
            gameActivity.player.releaseBullet();


            //TODO:测试,刷新界面,将来使用handler来代替
            gameActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //清空界面
                    DisplayMetrics dm = new DisplayMetrics();
                    gameActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                    gameActivity.bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
                    gameActivity.canvas = new Canvas(gameActivity.bitmap);
                    gameActivity.imgGameArea.setImageBitmap(gameActivity.bitmap);

                    //显示界面
                    gameActivity.canvas.drawCircle(gameActivity.player.getPosition().x, gameActivity.player.getPosition().y,
                            40, new Paint(Color.BLACK));
                    //显示子弹

                    ArrayList<Point> p = gameActivity.player.getBulletPos();
                    for(int i = 0; i<p.size(); i++){
                        gameActivity.canvas.drawCircle(p.get(i).x, p.get(i).y,
                                10, new Paint(Color.RED));
                    }

                }
            });

            //TODO:给主线程发送消息,让主线程刷新界面

        }

        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(gameActivity.getApplicationContext(), "Faild", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
