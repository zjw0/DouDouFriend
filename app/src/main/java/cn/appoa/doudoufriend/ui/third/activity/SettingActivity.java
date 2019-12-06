package cn.appoa.doudoufriend.ui.third.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.ui.WebViewActivity;

public class SettingActivity extends BaseActivity
        implements View.OnClickListener {

    @Override
    public void initIntent(Intent intent) {
//        phone = intent.getStringExtra("phone");

    }

    @Override
    public BaseTitlebar initTitlebar() {
        return new DefaultTitlebar.Builder(this)
                .setBackImage(R.drawable.back_white)
                .setTitle("更多设置")
                .create();
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_setting);
    }


    private TextView tv_cache_size;
    private RelativeLayout rl_clear_cache;
    private TextView tv_about;

    @Override
    public void initView() {
        super.initView();

        tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
        rl_clear_cache = (RelativeLayout) findViewById(R.id.rl_clear_cache);
        tv_about = (TextView) findViewById(R.id.tv_about);
        rl_clear_cache.setOnClickListener(this);
        tv_about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_clear_cache:
                clearAppCache();
                break;
            case R.id.tv_about:
                startActivity(new Intent(mActivity, WebViewActivity.class)
                        .putExtra("type", 1));
                break;

            default:
                break;
        }
    }


    @Override
    public void initData() {

    }

    /**
     * 清除缓存
     */
    protected void clearAppCache() {
        try {
            String cache = AtyUtils.getTotalCacheSize(mActivity);
            if (TextUtils.equals(cache, "0.0Byte")) {
                AtyUtils.showShort(mActivity, "已清除全部缓存!", true);
            } else {
                AtyUtils.clearAllCache(mActivity);
                if ((TextUtils.equals(AtyUtils.getTotalCacheSize(mActivity), "0.0Byte"))) {
                    tv_cache_size.setText(AtyUtils.getTotalCacheSize(mActivity));
                    Toast toast = Toast.makeText(mActivity, "清除成功啦！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    ImageView imageCodeProject = new ImageView(mActivity);
                    imageCodeProject.setImageResource(R.drawable.delete_cache);
                    toast.setView(imageCodeProject);
                    toast.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
