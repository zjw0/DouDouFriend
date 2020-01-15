package cn.appoa.doudoufriend.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import cn.appoa.doudoufriend.MainActivity;
import cn.appoa.doudoufriend.R;

/**
 * 启动页倒计时
 */

public class JumpThread implements Runnable {
    private TextView textView;
    private int numberTime ;
    private Activity activity;

    public JumpThread(TextView textView, int numberTime, Activity activity) {
        this.textView = textView;
        this.numberTime = numberTime;
        this.activity = activity;
    }

//    private Handler handler =new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            textView.setText("跳转   "+ String.valueOf(numberTime));
//            if (numberTime==0) {
//                jumpActivity();
//            }
//        }
//    };

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            //收到消息后执行
            textView.setText("跳过 "+ String.valueOf(numberTime));
            if (numberTime==0) {
                jumpActivity();
            }
            return false;
        }
    });

    private void jumpActivity(){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        //activity切换动画
        activity.overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        activity.finish();
    }
    @Override
    public void run() {
        try {
            while (numberTime>0) {
                Thread.sleep(1000);
                numberTime--;
                handler.sendMessage(new Message());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
