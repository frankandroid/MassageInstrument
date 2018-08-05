package com.frank.massageinstrument;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.frank.massageinstrument.db.DaoManager;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

/**
 * 创建者     frank
 * 创建时间   2018/7/14 21:46
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MassageApplication extends Application {

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            /* Read serial port parameters */
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            String path = sp.getString("DEVICE", "");
            int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));

            /* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

            /* Open the serial port */
            //	mSerialPort = new SerialPort(new File("/dev/ttySAC1"), 9600, 0);
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化greenDao数据库
        DaoManager.getInstance().init(this);
    }
}
