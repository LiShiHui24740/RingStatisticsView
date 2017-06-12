package com.ebanswers.ringstaticsview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ebanswers.lsh.RingStatisticsView;

public class MainActivity extends AppCompatActivity {

    private RingStatisticsView ringStatisticsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ringStatisticsView = (RingStatisticsView) findViewById(R.id.id_rsv);
        ringStatisticsView.setPercentAndColors(new float[]{0.2f,0.2f,0.3f,0.3f},new int[]{Color.parseColor("#F9AA28"), Color.parseColor("#009752"), Color.parseColor("#2EC1FB"), Color.parseColor("#FA6723")});
        ringStatisticsView.refresh();
    }
}
