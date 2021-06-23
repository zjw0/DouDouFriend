package cn.appoa.doudoufriend.base;

import androidx.core.content.ContextCompat;
import cn.appoa.aframework.activity.AfImageActivity;
import cn.appoa.aframework.presenter.BasePresenter;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;

public abstract class BaseImageActivity<P extends BasePresenter> extends AfImageActivity<P> {

    @Override
    public boolean enableSliding() {
        //开启侧滑返回
        return true;
    }

    @Override
    public void initView() {
        //沉浸式
        if (titlebar != null) {
            titlebar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorDefaultTitlebarBg));
            AtyUtils.setPaddingTop(mActivity, titlebar);
        }
    }
}
