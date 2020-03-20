package com.zbd.myapplication;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        test2();
    }

    private void test2() {
        setContentView(R.layout.activity_main);
        U_U9SkinHumView mHumView = findViewById(R.id.hum_view);
        mHumView.setYsAndYe(0f, 100f, 10f)
                .setSW(getWidth() * 21 / 375, getHeight() * 21 / 667, getWidth() * 22 / 375, getHeight() * 25 / 667)
                .setP(getWidth() * 24 / 375, getHeight() * 10 / 667, getWidth() * 20 / 375, getHeight() * 21 / 667)
                .setYScaleTexts("(%)", "100", "90", "80", "70", "60", "50", "40", "30", "20", "10", "0")
                .setIsAtuoXScaleTexts(false)
                .setAtuoXScaleTexts(true ? "(h)" : "(d)")
                .setXScaleTexts("0", "0", "0", "0", "0", "0", "0")
                .setBgGradientColorStart(Color.parseColor("#fb8394"));

        List<Float> data = new ArrayList<>();
        data.add(1f);
        data.add(10f);
        data.add(11f);
        data.add(51f);
        data.add(100f);
        mHumView.setData(data);
    }

    private float getWidth() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    private float getHeight() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }
}
