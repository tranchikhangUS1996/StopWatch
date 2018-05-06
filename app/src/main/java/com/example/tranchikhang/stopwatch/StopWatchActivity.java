package com.example.tranchikhang.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopWatchActivity extends Activity {
    private int Second = 0;
    private boolean Running = false;
    private boolean wasRunning = false;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            Second = savedInstanceState.getInt("second");
            Running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        setContentView(R.layout.activity_stop_watch);
        tv = (TextView) findViewById(R.id.display);
        runTimer();
    }
    public void onClickStart(View view) {
        Running = true;
        wasRunning = true;
    }
    public void onClickStop(View view) {
        Running = false;
        wasRunning = false;
    }
    public void onClickReset(View view){
        Second = 0;
        Running = false;
        wasRunning = false;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int s = Second%86400;
                int h = s/3600;
                int m = (s%3600)/60;
                s = s%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",h,m,s);
                tv.setText(time);
                if(Running) {
                    Second++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("second",Second);
        savedInstanceState.putBoolean("running",Running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(wasRunning) {
            Running = true;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = Running;
        Running = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            Running = true;
        }
    }
}
