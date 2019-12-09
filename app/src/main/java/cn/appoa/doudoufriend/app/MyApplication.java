package cn.appoa.doudoufriend.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import org.litepal.LitePal;

import cn.appoa.aframework.app.AfApplication;
import cn.appoa.aframework.utils.AESUtils;
import cn.appoa.aframework.utils.AtyUtils;


public class MyApplication extends AfApplication {

    public static final String addData = "<style> img { max-width:100% ; height:auto;} </style>" + "<div style=\"margin:0 8px;\">";

    // 测试支付
    public static boolean isTestPay = false;

    @Override
    public void initApplication() {
        //AtyUtils.isDebug = false;
        // 分包
        MultiDex.install(this);
        //AES加密
        AESUtils.init("");
        //Json解析
        //JsonUtils.init(3, "status", "msg", "data");
        //初始化MobSDK
        //ShareSdkUtils.initShare(this);
        //初始化LitePal
        LitePal.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 分包
        MultiDex.install(base);
    }
}
