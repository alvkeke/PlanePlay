package com.game.alv.planeplay;


import android.graphics.Point;

import java.util.ArrayList;
import java.util.Date;

//飞机类,玩家控制
public class Plane {

    private int hp;
    private Point position;
    private ArrayList<Point> bulletPos;
    private int shootBreakTime;
    private long lastShootTime;
    private long score;


    Plane(){
        //设置初始血量
        hp = 100;
        //设置位置
        position = new Point(0, 0);
        //初始化分数
        score = 0;
        //初始化子弹数列
        bulletPos = new ArrayList<>();
        //设置射击时间间隔
        shootBreakTime = 500;
        //获取当前时间
        lastShootTime = new Date().getTime();
    }

    int getHp() {
        return hp;
    }

    public Point getPosition() {
        return position;
    }

    void MoveTo(float x, float y){
        //根据传入的数据,移动
        position.x = (int)x;
        position.y = (int)y;
    }

    boolean wasHit(Point p, int hpDec){
        //判断距离
        double distance = Math.sqrt(Math.pow(position.x - p.x, 2) + Math.pow(position.y - p.y, 2));
        if(distance < 2*MyConstants.PlaneR){
            //传入收到的伤害
            hp-=hpDec;
            //撞到了则返回真
            return true;
        }

        //没撞到则返回假
        return false;
    }

    void Shoot(){
        //判断间隔是否够大
        if(new Date().getTime() - lastShootTime > shootBreakTime) {
            //发射子弹
            bulletPos.add(new Point(position.x, position.y - MyConstants.PlaneR));
            //将当前时间记录下来
            lastShootTime = new Date().getTime();
        }
    }

    void bulletMove(){
        for (Point p: bulletPos) {
            //子弹向前移动
            p.y-=MyConstants.bulletSpeed;

        }
    }

    void releaseBullet(){
        for(int i = 0; i<bulletPos.size(); i++){
            if(bulletPos.get(i).y<0){
                bulletPos.remove(i);
            }
        }
    }

    //返回当前的子弹位置,让主线程刷新子弹位置
    ArrayList<Point> getBulletPos() {
        return bulletPos;
    }

    void addScore(){
        score+=10;
    }

    long getScore() {
        return score;
    }
}
