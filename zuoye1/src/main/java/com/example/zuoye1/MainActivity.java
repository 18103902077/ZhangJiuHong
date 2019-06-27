package com.example.zuoye1;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//张九红  1811a
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_main;
    /**
     * 5
     */
    private TextView tv_main;
    /**
     * 跳过
     */
    private Button bt_mian;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        time();
        animation();
    }
    private void animation() {
        AnimationSet set = new AnimationSet(this, null);
        AlphaAnimation a = new AlphaAnimation(0,1);
        TranslateAnimation t = new TranslateAnimation(0, 100, 0, 100);
        ScaleAnimation s = new ScaleAnimation(0, 1,0, 1);
        RotateAnimation r = new RotateAnimation(0, 360);

        set.addAnimation(a);
        set.addAnimation(s);
        set.addAnimation(t);
        set.addAnimation(r);

        set.setDuration(5000);
        img_main.startAnimation(set);
    }

    int a=5;
    private void time() {
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_main.setText(""+a);
                a--;
            }
            @Override
            public void onFinish() {
                tv_main.setText(""+1);
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        }.start();

    }

    private void initView() {
        img_main = (ImageView) findViewById(R.id.img_main);
        tv_main = (TextView) findViewById(R.id.tv_main);
        bt_mian = (Button) findViewById(R.id.bt_mian);
        bt_mian.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_mian:

                timer.cancel();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

                break;
        }
    }
}
