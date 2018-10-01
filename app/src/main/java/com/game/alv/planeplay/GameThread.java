package com.game.alv.planeplay;


import android.graphics.Point;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


//游戏运行线程,用来调控画面和数据类
public class GameThread implements Runnable {

    //保存传入的指针
    private GameActivity gameActivity;
    private long lastShowEnemyTime;
    //随机获取敌人出生地点
    private Random randomX = new Random();

    GameThread(GameActivity gameActivity1){
        gameActivity = gameActivity1;
        lastShowEnemyTime = new Date().getTime();
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

            if(new Date().getTime() - lastShowEnemyTime > MyConstants.showEnemyBreak){

                //显示敌人
                gameActivity.enemies.add(new Enemy(
                        randomX.nextInt(
                                gameActivity.dm.widthPixels - 2*MyConstants.PlaneR)
                                + MyConstants.PlaneR ));
                lastShowEnemyTime = new Date().getTime();

            }

            //自动射击
            gameActivity.player.Shoot();
            //敌人移动
            for(int i = 0; i<gameActivity.enemies.size(); i++){
                //随机撞击伤害
                if(gameActivity.player.wasHit(gameActivity.enemies.get(i).getPosition(), randomX.nextInt(10) + 5)){
                    gameActivity.enemies.remove(i);
                    continue;
                }

                if(gameActivity.enemies.get(i).Move(gameActivity.dm.heightPixels)){
                    gameActivity.enemies.remove(i);
                }
            }

            //子弹移动
            gameActivity.player.bulletMove();
            //判断击中敌人,且致死,将敌人从队列中移除
            ArrayList<Point> bullets = gameActivity.player.getBulletPos();
            ArrayList<Enemy> enemies = gameActivity.enemies;
            if(bullets.size() > 0 && enemies.size() >0) {
                for(int i = 0; i< enemies.size(); i++){
                    for(int j = 0; j<bullets.size(); j++){
                        //随机伤害值:80-100
                        if(enemies.get(i).wasHit(bullets.get(j), randomX.nextInt(21) + 80)){
                            enemies.remove(i);
                            bullets.remove(j);
                            gameActivity.player.addScore();
                            break;
                        }
                    }
                }
            }
            //释放已经没用的子弹
            gameActivity.player.releaseBullet();

            //TODO:给主线程发送消息,让主线程刷新界面
            //TODO:测试,刷新界面,将来使用handler来代替
            gameActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameActivity.flashView();
                }
            });

        }

        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(gameActivity.getApplicationContext(), "You Lose.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
