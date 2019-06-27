package com.example.zuoye1;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_main2;
    /**
     * 5
     */
    private TextView tv_main2;
    /**
     * 跳过
     */
    private Button bt_mian2;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        time();
        animation();
    }
    private void animation() {
        ObjectAnimator a = ObjectAnimator.ofFloat(img_main2, "alpha", 0, 1);
        ObjectAnimator t = ObjectAnimator.ofFloat(img_main2, "translationY", 0, 100);
        ObjectAnimator r = ObjectAnimator.ofFloat(img_main2, "rotation", 0, 360);
        ObjectAnimator s = ObjectAnimator.ofFloat(img_main2, "scaleX", 0, 1);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(5000);
        set.play(a).with(t).with(r).with(s);
        set.start();
    }

    int a=5;
    private void time() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_main2.setText(""+a);
                        a--;

                        if (a==0){
                            timer.cancel();
                            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        },100,1000);
    }

    private void initView() {
        img_main2 = (ImageView) findViewById(R.id.img_main2);
        tv_main2 = (TextView) findViewById(R.id.tv_main2);
        bt_mian2 = (Button) findViewById(R.id.bt_mian2);
        bt_mian2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_mian2:
                timer.cancel();
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
                break;
        }
    }
}
