package com.xcz1899.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xcz1899.logger.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.setEnableThread(false);
        Logger.e("init finish");
    }
}
