package cn.appoa.doudoufriend.base;

import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.wangzhen.statusbar.DarkStatusBar;

import cn.appoa.aframework.activity.AfActivity;
import cn.appoa.aframework.presenter.BasePresenter;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.aframework.utils.SpUtils;
import cn.appoa.aframework.view.ILoginView;
import cn.appoa.doudoufriend.R;

public abstract class BaseActivity<P extends BasePresenter> extends AfActivity<P>
        implements ILoginView {

    @Override
    public void initView() {
        DarkStatusBar.get().fitLight(this);//浅色模式
        if (titlebar != null) {
            titlebar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorTheme));
            AtyUtils.setPaddingTop(mActivity, titlebar);
        }
    }

    @Override
    public boolean enableSliding() {
        return true;
    }

    @Override
    public boolean isLogin() {
        //return API.isLogin();
        return false;
    }

    @Override
    public void toLoginActivity() {
//        startActivityForResult(new Intent(mActivity, LoginActivity.class), 999);
    }

    @Override
    public void toLoginSuccess(Intent data) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 999 && resultCode == -1 && data != null) {
//            toLoginSuccess(data);
//        }
//    }

    //退出登录
    public void exitApp() {
        // 本地数据删除
        SpUtils.clearData(mActivity);
        AtyUtils.showShort(mActivity, "退出成功", false);
//        BusProvider.getInstance().post(new LoginEvent(2));
        finish();
    }
}
