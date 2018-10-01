package com.game.alv.planeplay;


import android.graphics.Point;
/*
import java.util.ArrayList;
import java.util.Date;
*/

//敌人类,游戏线程控制
public class Enemy {

    private int hp;
    private Point position;
    //private ArrayList<Point> bulletPos;
    private float speed;
    /*
    private long lastShootTime;
    private int shootBreakTime;*/

    Enemy(int ShowPosX){
        //初始化hp
        hp = 100;
        //初始化出生位置,在屏幕顶端,
        position = new Point(ShowPosX, 0);
        //初始化射击间隔
        //shootBreakTime = 500;
        //初始化速度
        speed = 5.0f;
        //获取出生时间,为了记录射击时间间隔
        //lastShootTime = new Date().getTime();
    }
/*
    public int getHp() {
        return hp;
    }

    public ArrayList<Point> getBulletPos() {
        return bulletPos;
    }
    */

    boolean Move(int ScreenHeight){
        //战机向前移动
        position.y+=speed;
        //判断是否超出屏幕,超出返回真
        return position.y > ScreenHeight;
    }
/*
    public void shoot(){
        //判断间隔是否够大
        if(lastShootTime - new Date().getTime() > shootBreakTime) {
            //发射子弹
            bulletPos.add(new Point(position.x, position.y));
            //将当前时间记录下来
            lastShootTime = new Date().getTime();
        }
    }
*/
    boolean wasHit(Point p, int hpDec){
        //判断是否击中
        if(p.x > (position.x - MyConstants.PlaneR) && p.x < (position.x + MyConstants.PlaneR) &&
                p.y > position.y - MyConstants.PlaneR && p.y < position.y + MyConstants.PlaneR){
            //传入收到的伤害
            hp-=hpDec;
        }

        //判断血量是否为0或者小于0,死亡返回真
        return hp <= 0;
    }

/*
    public void bulletMove(){

        for(int i = 0; i<bulletPos.size(); i++){
            //移动子弹
            bulletPos.get(i).y-=MyConstants.bulletSpeed;
            //判断子弹是否超出范围
            if(bulletPos.get(i).y<0){
                //如果子弹超出屏幕位置,则从链表中去除该子弹的位置
                bulletPos.remove(i);
                i--;
            }
        }
    }
*/

    public Point getPosition() {
        return position;
    }
}
