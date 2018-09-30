package com.game.alv.planeplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.util.Objects;


//游戏主线程
public class MainActivity extends AppCompatActivity {

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


    //开始游戏按钮点击事件响应
    class btnStartOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //启动游戏界面
            Intent intentGame = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intentGame);
        }
    }

    //退出游戏按钮点击事件相应
    class btnExitOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            MainActivity.this.finish();
        }
    }

}
