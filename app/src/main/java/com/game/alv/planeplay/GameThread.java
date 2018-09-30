package com.game.alv.planeplay;


//游戏运行线程,用来调控画面和数据类
public class GameThread implements Runnable {

    //保存传入的指针
    private Plane player;
    MainActivity mainActivity;

    GameThread(MainActivity mainActivityi, Plane p){
        player = p;
        mainActivity = mainActivityi;
    }

    @Override
    public void run() {
        while(player.getHp()>0){
            //延时执行
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
