package cn.appoa.doudoufriend.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.litepal.LitePal;

import cn.appoa.aframework.app.AfApplication;
import cn.appoa.aframework.utils.AESUtils;


public class MyApplication extends AfApplication {

    public static final String addData = "<style> img { max-width:100% ; height:auto;} </style>" + "<div style=\"margin:0 8px;\">";

    // 测试支付
    public static boolean isTestPay = false;

    @Override
    public void initApplication() {
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(this, "6d8625ee9c", true);
//        Bugly.init(this, "6d8625ee9c", false);

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
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5e05d0ca");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 分包
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }
}
