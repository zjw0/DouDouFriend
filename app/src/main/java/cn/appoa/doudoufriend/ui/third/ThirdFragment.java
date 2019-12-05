package cn.appoa.doudoufriend.ui.third;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangzhen.statusbar.DarkStatusBar;

import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseFragment;

/**
 * 我的
 */
public class ThirdFragment extends BaseFragment {

    @Override
    public View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_third, null);
        return view;
    }


    @Override
    public void initView(View view) {
        DarkStatusBar.get().fitLight(mActivity);//亮色模式
    }


    @Override
    public void initData() {

    }

}
