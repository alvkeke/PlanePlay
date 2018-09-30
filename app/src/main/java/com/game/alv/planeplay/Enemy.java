package com.game.alv.planeplay;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.Date;

//敌人类,游戏线程控制
public class Enemy {

    private int hp;
    private Point position;
    private ArrayList<Point> bulletPos;
    private float speed;
    private long lastShootTime;
    private int shootBreakTime;

    Enemy(int ShowPosx){
        //初始化出生位置,在屏幕顶端,
        position = new Point(ShowPosx, 0);
        //初始化射击间隔
        shootBreakTime = 500;
        //初始化速度
        speed = 10.0f;
        //获取出生时间,为了记录射击时间间隔
        lastShootTime = new Date().getTime();
    }

    public int getHp() {
        return hp;
    }

    public ArrayList<Point> getBulletPos() {
        return bulletPos;
    }

    public boolean Move(int ScreenHeight){
        //战机向前移动
        position.y+=speed;
        //判断是否超出屏幕,超出返回真
        return position.y > ScreenHeight;
    }

    public void shoot(){
        //判断间隔是否够大
        if(lastShootTime - new Date().getTime() > shootBreakTime) {
            //发射子弹
            bulletPos.add(new Point(position.x, position.y));
            //将当前时间记录下来
            lastShootTime = new Date().getTime();
        }
    }

    public boolean wasHit(int hpDec){
        //传入收到的伤害
        hp-=hpDec;
        //判断血量是否为0或者小于0,死亡返回真
        return hp <= 0;
    }

    public void bulletMove(){
        for (Point p: bulletPos) {
            //子弹向前移动
            p.y-=MyConstants.bulletSpeed;
            if(p.y<0){
                //如果子弹超出屏幕位置,则从链表中去除该子弹的位置
                bulletPos.remove(p);
            }
        }
    }

}
