package com.game.alv.planeplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    public Plane player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //隐藏标题栏
        Objects.requireNonNull(getSupportActionBar()).hide();
        //隐藏系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        player = new Plane();


    }



}
