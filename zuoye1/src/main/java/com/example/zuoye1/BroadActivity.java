package com.example.zuoye1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BroadActivity extends AppCompatActivity {

    private TextView tv_broad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad);
        initView();
    }

    private void initView() {
        tv_broad = (TextView) findViewById(R.id.tv_broad);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tv_broad.setText(name);
    }
}
