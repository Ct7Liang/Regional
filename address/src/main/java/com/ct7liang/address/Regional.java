package com.ct7liang.address;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2018-03-27.
 *
 */
public class Regional {

    public static String TAG = "Regional";
    public static String SelectColor = "#FF4081";
    public static String NormalColor = "#3F51B5";
    public static String TitleColor = "#444444";
    public static String TitleBackColor = "#FFFFFF";
    public static String BackColor = "#FFFFFF";

//    private static void setColor(String selectColor, String normalColor, String titleColor, String backColor){
//        SelectColor = selectColor==null?"#FF4081":selectColor;
//        NormalColor = normalColor==null?"#3F51B5":normalColor;
//        TitleColor = titleColor==null?"#444444":titleColor;
//        BackColor = backColor==null?"#FFFFFF":backColor;
//    }

//    public static void initTag(String tag){
//        TAG = tag;
//    }

    public static void init(Context context, String packageName, String selectColor, String normalColor, String titleColor, String titleBackColor, String backColor, String tag){

        SelectColor = selectColor==null?"#FF4081":selectColor;
        NormalColor = normalColor==null?"#3F51B5":normalColor;
        TitleColor = titleColor==null?"#444444":titleColor;
        BackColor = backColor==null?"#FFFFFF":backColor;
        TitleBackColor = titleBackColor==null?"#FFFFFF":titleBackColor;
        TAG = tag==null?"Regional":tag;

        String DB_PATH = "/data/data/" + packageName + "/databases";

        String outFileName = DB_PATH + "/address.db"; //导入databases目录下的数据库文件名称
        File file = new File(DB_PATH);
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        File dataFile = new File(outFileName);
        if (dataFile.exists()) {
            dataFile.delete();
        }
        final InputStream myInput; //资源文件中的被导入的数据库文件名称
        try {
            myInput = context.getApplicationContext().getAssets().open("address.db");
        } catch (IOException e) {
            Log.i(TAG, "省市县数据库导入失败");
            Log.e(TAG, "省市县数据库导入失败: " + e.toString());
            return;
        }
        final OutputStream myOutput;
        try {
            myOutput = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            Log.i(TAG, "省市县数据库导入失败");
            Log.e(TAG, "省市县数据库导入失败: " + e.toString());
            return;
        }
        new Thread(){
            @Override
            public void run() {
                byte[] buffer = new byte[1024];
                int length;
                try {
                    while ((length = myInput.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                    }
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                    Log.i(TAG, "省市县数据库导入完成");
                } catch (IOException e) {
                    Log.i(TAG, "省市县数据库导入失败");
                    Log.e(TAG, "省市县数据库导入失败: " + e.toString());
                }
            }
        }.start();
    }
}