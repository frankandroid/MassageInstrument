package com.frank.massageinstrument;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        //配置
        final Button buttonSetup = (Button)findViewById(R.id.ButtonSetup);
        buttonSetup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, SerialPortPreferencesActivity.class));
            }
        });

        //发送接收数据
        final Button buttonConsole = (Button)findViewById(R.id.ButtonConsole);
        buttonConsole.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, ConsoleActivity.class));
            }
        });

        //批量发送，测试所用
        final Button buttonSending = (Button)findViewById(R.id.ButtonSending);
        buttonSending.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, SendingActivity.class));
            }
        });

        //退出
        final Button buttonQuit = (Button)findViewById(R.id.ButtonQuit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Main2Activity.this.finish();
            }
        });
    }
}
