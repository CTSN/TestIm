package com.m520it.testim.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.m520it.testim.R;

public class ChatActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);
        tv = (TextView)findViewById(R.id.tv_title);
        String name = getIntent().getData().getQueryParameter("title");
        String id = getIntent().getData().getQueryParameter("targetId");

        if (!TextUtils.isEmpty(name)){
            tv.setText(name);
        }else{
            if (id.equals("0001")){
                tv.setText("SN");
            }else{
                tv.setText("CT");
            }
        }
    }
}
