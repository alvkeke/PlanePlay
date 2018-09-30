package com.game.alv.planeplay;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.Date;

//飞机类,玩家控制
public class Plane {

    private int hp;
    private Point position;
    private float speed;
    private ArrayList<Point> bulletPos;
    private int shootBreakTime;
    private long lastShootTime;

    Plane(){
        //设置初始血量
        hp = 100;
        //设置速度
        speed = 10.0f;
        //设置位置
        position = new Point(0, 0);
        //设置射击时间间隔
        shootBreakTime = 500;
        //获取当前时间
        lastShootTime = new Date().getTime();
    }

    public int getHp() {
        return hp;
    }

    public Point getPosition() {
        return position;
    }

    public void MoveTo(int x, int y){
        //TODO:根据向量的方法移动玩家的飞机

    }

    public boolean wasHit(int hpDec){
        //传入收到的伤害
        hp-=hpDec;
        //判断血量是否为0或者小于0,死亡返回真
        return hp <= 0;
    }

    public void Shoot(){

        //判断间隔是否够大
        if(lastShootTime - new Date().getTime() > shootBreakTime) {
            //发射子弹
            bulletPos.add(new Point(position.x, position.y));
            //将当前时间记录下来
            lastShootTime = new Date().getTime();
        }
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

    //返回当前的子弹位置,让主线程刷新子弹位置
    public ArrayList<Point> getBulletPos() {
        return bulletPos;
    }
}
