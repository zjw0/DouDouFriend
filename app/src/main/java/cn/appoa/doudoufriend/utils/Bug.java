package cn.appoa.doudoufriend.utils;

import android.util.Log;

import cn.appoa.aframework.utils.AtyUtils;

public class Bug {

    public static String bug(){
        String str1 = "$";
        AtyUtils.i("一个字符length","是" + str1.length());
//        String str2 = null;
//        Log.e("str2","是" + str2.length());
//        return "This is bug";
        return "This no bug";
    }
}
