package com.frank.massageinstrument;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

/**
 * 创建者     陈国柱
 * 创建时间   2018/7/15 12:41
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private String port = "/dev/ttyS1";
    private String channel = "CH00";
    private String frequency = "50";
    private String intensity = "50";
    private String mode = "5";
    private String minute = "01";

    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static final String START_FLAG = "#";
    public static final String END_FLAG = "*";
    private String isON = ON;

    private EditText port_et;
    private EditText channel_et;
    private EditText frequency_et;
    private EditText intensity_et;
    private EditText mode_et;
    private EditText minute_et;
    private EditText showMsg;
    private Button open;
    private Button sendMsg;
    private Button close;
    private Button clear;
    private RadioGroup radioGroup;

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private StringBuilder sb = new StringBuilder();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
    }

    private void initView() {
        open = findViewById(R.id.open_port);
        open.setOnClickListener(this);
        close = findViewById(R.id.close_port);
        close.setOnClickListener(this);
        sendMsg = findViewById(R.id.send_msg);
        sendMsg.setOnClickListener(this);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);
        port_et = findViewById(R.id.port);
        channel_et = findViewById(R.id.channel);
        frequency_et = findViewById(R.id.frequency);
        intensity_et = findViewById(R.id.intensity);
        mode_et = findViewById(R.id.mode);
        minute_et = findViewById(R.id.minute);
        showMsg = findViewById(R.id.receive_content);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.on_bt:
                        isON = ON;
                        break;
                    case R.id.off_bt:
                        isON = OFF;
                        break;
                }

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_port:
                try {
                    openPort();
                    showToast("打开" + port + "端口成功");
                } catch (Exception e) {
                    showToast("打开" + port + "端口失败");
                    e.printStackTrace();
                }
                break;
            case R.id.send_msg:
                try {
                    updateValues();
                    sb.append(START_FLAG).append(channel).append(frequency).append(intensity)
                            .append(mode).append(minute).append(isON).append(END_FLAG);
                    sendMsg(sb.toString());
                    sb.delete(0, sb.length());
                } catch (Exception e) {
                    showToast("打开输出流失败");
                    e.printStackTrace();
                }
                break;
            case R.id.close_port:
                closePort();
                break;
            case R.id.clear:
                showMsg.setText("");
                break;
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void updateValues() {
        if (!checkEditIsEmpty(channel_et)) {
            channel = channel_et.getText().toString();
        }
        if (!checkEditIsEmpty(frequency_et)) {
            frequency = frequency_et.getText().toString();
        }
        if (!checkEditIsEmpty(intensity_et)) {
            intensity = intensity_et.getText().toString();
        }
        if (!checkEditIsEmpty(mode_et)) {
            mode = mode_et.getText().toString();
        }
        if (!checkEditIsEmpty(minute_et)) {
            minute = minute_et.getText().toString();
        }
    }

    private boolean checkEditIsEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }

    private void openPort() throws IOException {
        if (mSerialPort == null) {
            if (!checkEditIsEmpty(port_et)) {
                port = port_et.getText().toString();
            }
            mSerialPort = new SerialPort(new File(port), 115200, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        }
    }

    private void sendMsg(String msg) throws IOException {
        mOutputStream.write(msg.getBytes());
        mOutputStream.write('\n');
    }

    private void closePort() {
        try {
            sendMsg("#CH20xxxxxxxOFF*");
        } catch (Exception e) {
            showToast("发送关闭消息失败");
            e.printStackTrace();
        }
        if (mInputStream != null) {
            mInputStream = null;
        }
        if (mOutputStream != null) {
            mOutputStream = null;
        }
        if (mReadThread != null)
            mReadThread.interrupt();
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
        showToast("关闭成功");
    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) {
                        return;
                    }
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                //if (mReception != null) {
                showMsg.append(new String(buffer, 0, size));
                showMsg.append("\r");
                //}
            }
        });
    }
}




