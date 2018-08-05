package com.frank.massageinstrument.db;

import android.content.Context;


import com.frank.massageinstrument.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于从apk中拷贝数据库文件
 * Created by rocky on 2018/7/25.
 */

public class DbCopyHelper {

    public  static MyOpenHelper getOpenHelper(Context context, String pathStr, String filename){
//        MyOpenHelper mDevOpenHelper = new MyOpenHelper(context, filename, null);
//        return mDevOpenHelper;
        File jhPath=new File(pathStr,filename);
        if(jhPath.exists()){
            MyOpenHelper mDevOpenHelper = new MyOpenHelper(context, filename, null);
            return mDevOpenHelper;
        }else{
            File path=new File(pathStr);
            if(!path.exists()) {
                path.mkdirs();
            }
            try {
//                InputStream is=context.getClass().getClassLoader().getResourceAsStream("assets/"+"tongchuanghaiyang1234.db");
                InputStream is=context.getResources().openRawResource(R.raw.web);
                FileOutputStream fos=new FileOutputStream(jhPath);
                byte[] buffer=new byte[1024];
                int count = 0;
                while((count = is.read(buffer))>0){
                    fos.write(buffer,0,count);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return getOpenHelper(context,pathStr,filename);
        }
    }
}
