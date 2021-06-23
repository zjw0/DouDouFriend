package cn.appoa.doudoufriend.app;

import android.content.Context;

import androidx.multidex.MultiDex;

import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

import org.litepal.LitePal;

import java.util.Locale;

import cn.appoa.aframework.app.AfApplication;
import cn.appoa.aframework.utils.AESUtils;
import cn.appoa.aframework.utils.AtyUtils;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;


public class MyApplication extends AfApplication {

    public static final String addData = "<style> img { max-width:100% ; height:auto;} </style>" + "<div style=\"margin:0 8px;\">";

    // 测试支付
    public static boolean isTestPay = false;

    @Override
    public void initApplication() {
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
//        Bugly.init(this, "542d78b688", true);
//        Bugly.init(this, "9197d1db35", true);


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


        //bugly

        //设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        //设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        //设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        //设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;
        //补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                AtyUtils.i("补丁下载地址：","是"+patchFile);
                Toast.makeText(getApplication(), "补丁下载地址：" + patchFile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Toast.makeText(getApplication(), String.format(Locale.getDefault(), "%s %d%%",
                        Beta.strNotificationDownloading, (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Toast.makeText(getApplication(), "补丁下载成功："+msg, Toast.LENGTH_SHORT).show();
                AtyUtils.i("补丁下载成功：","是"+msg);
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplication(), "补丁下载失败："+msg, Toast.LENGTH_SHORT).show();
                AtyUtils.i("补丁下载失败：","是"+msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplication(), "补丁应用成功："+msg, Toast.LENGTH_SHORT).show();
                AtyUtils.i("补丁应用成功：","是"+msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplication(), "补丁应用失败："+msg, Toast.LENGTH_SHORT).show();
                AtyUtils.i("补丁应用失败：","是"+msg);
            }

            @Override
            public void onPatchRollback() {
                AtyUtils.i("==============onPatchRollback======","");
            }
        };
        //设置开发设备，默认为false，上传补丁如果下发范围指定为"开发设备"，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(this, true);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
        Bugly.init(this, "021f4493a7", true);





//        setStrictMode();
//        // 设置是否开启热更新能力，默认为true
//        Beta.enableHotfix = true;
//        // 设置是否自动下载补丁
//        Beta.canAutoDownloadPatch = true;
//        // 设置是否提示用户重启
//        Beta.canNotifyUserRestart = true;
//        // 设置是否自动合成补丁
//        Beta.canAutoPatch = true;
//
//        /**
//         *  全量升级状态回调
//         */
//        Beta.upgradeStateListener = new UpgradeStateListener() {
//            @Override
//            public void onUpgradeFailed(boolean b) {
//
//            }
//
//            @Override
//            public void onUpgradeSuccess(boolean b) {
//
//            }
//
//            @Override
//            public void onUpgradeNoVersion(boolean b) {
//                Toast.makeText(getApplicationContext(), "最新版本", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUpgrading(boolean b) {
//                Toast.makeText(getApplicationContext(), "onUpgrading", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadCompleted(boolean b) {
//
//            }
//        };
//
//        /**
//         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
//         */
//        Beta.betaPatchListener = new BetaPatchListener() {
//            @Override
//            public void onPatchReceived(String patchFileUrl) {
//                Toast.makeText(getApplicationContext(), patchFileUrl, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadReceived(long savedLength, long totalLength) {
//                Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(),
//                        "%s %d%%",
//                        Beta.strNotificationDownloading,
//                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadSuccess(String patchFilePath) {
//                Toast.makeText(getApplicationContext(), patchFilePath, Toast.LENGTH_SHORT).show();
////                Beta.applyDownloadedPatch();
//            }
//
//            @Override
//            public void onDownloadFailure(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplySuccess(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplyFailure(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPatchRollback() {
//                Toast.makeText(getApplicationContext(), "onPatchRollback", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        long start = System.currentTimeMillis();
//        Bugly.setUserId(this, "falue");
//        Bugly.setUserTag(this, 123456);
//        Bugly.putUserData(this, "key1", "123");
//        Bugly.setAppChannel(this, "bugly");
//
//        long end = System.currentTimeMillis();
//        Log.e("init time--->", end - start + "ms");


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 分包
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }

//    @TargetApi(9)
//    protected void setStrictMode() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//    }

}
