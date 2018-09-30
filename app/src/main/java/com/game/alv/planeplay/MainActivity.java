package com.game.alv.planeplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;


//游戏主线程
public class MainActivity extends AppCompatActivity {

    //游戏线程,用来控制正在运行的线程
    private Thread threadGame;
    private Plane player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏标题栏
        Objects.requireNonNull(getSupportActionBar()).hide();
        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //游戏启动界面的两个按钮
        Button btnStart = findViewById(R.id.main_btnStart);
        Button btnExit = findViewById(R.id.main_btnExit);
        //给两个按钮添加事件监听
        btnStart.setOnClickListener(new btnStartOnClick());
        btnExit.setOnClickListener(new btnExitOnClick());



    }


    //启动界面响应事件
    class btnStartOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //加载游戏视图
            MainActivity.this.setContentView(R.layout.view_gaming);
            //启动游戏线程
            player = new Plane();
            threadGame = new Thread(new GameThread(player));
            threadGame.start();
        }
    }

    class btnExitOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            MainActivity.this.finish();
        }
    }

    //游戏界面事件相应

}
