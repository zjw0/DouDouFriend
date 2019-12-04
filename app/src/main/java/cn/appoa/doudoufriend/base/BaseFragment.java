package cn.appoa.doudoufriend.base;

import android.content.Intent;

import cn.appoa.aframework.fragment.AfFragment;
import cn.appoa.aframework.presenter.BasePresenter;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.aframework.utils.SpUtils;
import cn.appoa.aframework.view.ILoginView;
import zm.bus.event.BusProvider;


public abstract class BaseFragment<P extends BasePresenter> extends AfFragment<P>
        implements ILoginView {

    @Override
    public boolean isLogin() {
        //return API.isLogin();
        return false;
    }

    @Override
    public void toLoginActivity() {
//        startActivityForResult(new Intent(mActivity, LoginActivity.class), 998);
    }

    @Override
    public void toLoginSuccess(Intent data) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == -1 && data != null) {
            toLoginSuccess(data);
        }
    }

    //退出登录
    public void exitApp() {
        // 本地数据删除
        SpUtils.clearData(mActivity);
        AtyUtils.showShort(mActivity, "退出成功", false);
//        BusProvider.getInstance().post(new LoginEvent(2));
    }
}
