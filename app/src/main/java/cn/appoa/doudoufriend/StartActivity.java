package cn.appoa.doudoufriend;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.utils.JumpThread;


/**
 * 启动页
 */
public class StartActivity extends BaseActivity
        implements Animation.AnimationListener {

    @BindView(R.id.tv_jump)
    TextView tv_jump;

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_start);
    }

    private ImageView iv_start, iv_start_gif;
//    private TextView tv_jump;

    @Override
    public void initView() {
        super.initView();
        iv_start = findViewById(R.id.iv_start);
        iv_start_gif = findViewById(R.id.iv_start_gif);
        Glide.with(mActivity).load(R.drawable.icon_start).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_start_gif);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_jump.setVisibility(View.VISIBLE);
                tv_jump.setText("跳过 6");
                Thread t = new Thread(new JumpThread(tv_jump,6,mActivity));
                t.start();
            }
        }, 300);
    }

    @Override
    public void initData() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
//        requestPermissions(permissions, null);
//        startAnim();
        requestPermissions(permissions, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                startAnim();
            }

            @Override
            public void onDenied(String s) {
                //有未允许权限时提示，避免使用AtyUtils.showShort()无法显示提示
//                Toast.makeText(mActivity, "请开启所有权限，确保正常使用", Toast.LENGTH_SHORT).show();
                AtyUtils.showShort(mActivity, "请开启所有权限，确保正常使用", false);
                startAnim();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // 因为权限管理类无法监听系统，所以需要重写onRequestPermissionResult方法，更新权限管理类，并回调结果。这个是必须要有的
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
        boolean start = false;
        //任意一项禁止则提示
        for (int grantResult : grantResults) {
            if (!(grantResult < 0)) {
                start = true;
            } else {
                start = false;
                break;
            }
        }
        if (start) {
            startAnim();
        } else {
            AtyUtils.showShort(mActivity, "请开启所有权限，确保正常使用", false);
            startAnim();
        }
    }

    /**
     * 渐变展示启动屏
     */
    protected void startAnim() {
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(6000);
        iv_start.startAnimation(aa);
        aa.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        iv_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.start_alpha, R.anim.end_alpha);
    }

    @OnClick({R.id.tv_jump})
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_jump:
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.start_alpha, R.anim.end_alpha);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //JPushInterface.onPause(this);
    }

    @Override
    public boolean enableSliding() {
        return false;
    }

}
