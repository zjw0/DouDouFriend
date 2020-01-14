package cn.appoa.doudoufriend;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.appoa.doudoufriend.base.BaseActivity;


/**
 * 启动页
 */
public class StartActivity extends BaseActivity
        implements Animation.AnimationListener {

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_start);
    }

    private ImageView iv_start,iv_start_gif;

    @Override
    public void initView() {
        super.initView();
        iv_start = findViewById(R.id.iv_start);
        iv_start_gif = findViewById(R.id.iv_start_gif);
        Glide.with(mActivity).load(R.drawable.icon_start).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_start_gif);
    }

    @Override
    public void initData() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
        };
        requestPermissions(permissions, null);
        startAnim();
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
