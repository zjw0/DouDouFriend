package cn.appoa.doudoufriend.ui.third.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;

/**
 * 大图预览
 */
public class ShowBigImageListActivity extends BaseActivity {

    @BindView(R.id.vi_top)
    View viTop;
    @BindView(R.id.iv_big_image)
    ImageView ivBigImage;
    @BindView(R.id.vi_bottom)
    View viBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContent(R.layout.activity_show_big_image_list);
    }

    @Override
    public void initData() {
        ivBigImage.setImageResource(R.drawable.icon_pic);
    }

    @OnClick({R.id.vi_top, R.id.vi_bottom, R.id.iv_big_image})
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.vi_top:
                finish();
                break;
            case R.id.vi_bottom:
                finish();
                break;
            case R.id.iv_big_image:
                AtyUtils.showShort(this,"你最美！",false);
                break;
            default:
                break;
        }
    }

}
