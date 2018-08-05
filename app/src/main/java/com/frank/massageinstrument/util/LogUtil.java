package com.frank.massageinstrument.util;


import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huliuda on 17-4-21.
 */

public class LogUtil {
    private static final String defaultPath = "/sdcard/";
    private static String mPath;

    private static Writer mWriter;

    private static SimpleDateFormat df;

    private static boolean bDebug = true;

    public static void setDebug(boolean b) {
        bDebug = b;
    }

    public static void d(String info) {
        if (!bDebug) {
            return;
        }
        String[] infos = getAutoJumpLogInfos();
        Log.d(infos[0], "[" + infos[1] + "," + infos[2] + "] " + info);
    }

    public static void e(String info) {
        if (!bDebug) {
            return;
        }
        String[] infos = getAutoJumpLogInfos();
        Log.e(infos[0], "[" + infos[1] + "," + infos[2] + "] " + info);
    }

    public static void w(String info) {
        if (!bDebug) {
            return;
        }
        String[] infos = getAutoJumpLogInfos();
        Log.w(infos[0], "[" + infos[1] + "," + infos[2] + "] " + info);
    }

    public static void writeHexString(String tag, byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            buffer.append(hex.toUpperCase() + " ");
        }
        write(tag + ":" + buffer.toString());
    }

    public static void setLogPath(String file_path) {
        if (!new File(file_path).exists() && bDebug) {
            new File(file_path).mkdirs();
        }
        if (mWriter == null) {
            mPath = file_path + File.separator + new SimpleDateFormat("yyMMddHHmmss").format(new
                    Date());
            try {
                mWriter = new BufferedWriter(new FileWriter(mPath), 2048);
            } catch (Exception e) {
                e.printStackTrace();
            }
            df = new SimpleDateFormat("[yy-MM-dd HH:mm:ss]: ");
        }
    }


    public static void release() {
        if (mWriter != null) {
            try {
                mWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mWriter = null;
        }
    }

    public static void write(String log) {
        if (!bDebug)
            return;

        /** 判断如果log文件大于64m，则重新创建log文件，并删除前面的log文件 */
        File file = new File(mPath);
        if (file.length() > 64 * 1024 * 1024) {
            release();
            try {
                mWriter = new BufferedWriter(new FileWriter(mPath), 2048);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            mWriter.write(df.format(new Date()));
            mWriter.write(log);
            mWriter.write("\n");
            mWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void write(Class cls, String log) { //如果还想看是在哪个类里可以用这个方法
        if (!bDebug) {
            return;
        }

        /** 判断如果log文件大于64m，则重新创建log文件，并删除前面的log文件 */
        File file = new File(mPath);
        if (file.length() > 64 * 1024 * 1024) {
            release();
            try {
                mWriter = new BufferedWriter(new FileWriter(mPath), 2048);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            mWriter.write(df.format(new Date()));
            mWriter.write(cls.getSimpleName() + " ");
            mWriter.write(log);
            mWriter.write("\n");
            mWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取打印信息所在方法名，行号等信息
     *
     * @return
     */
    private static String[] getAutoJumpLogInfos() {
        String[] infos = new String[]{"", "", ""};
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 5) {
            Log.e("MyLogger", "Stack is too shallow!!!");
            return infos;
        } else {
            infos[0] = elements[4].getClassName().substring(
                    elements[4].getClassName().lastIndexOf(".") + 1);
            infos[1] = "Method " + elements[4].getMethodName();
            infos[2] = " at line " + elements[4].getLineNumber();
            return infos;
        }
    }
}