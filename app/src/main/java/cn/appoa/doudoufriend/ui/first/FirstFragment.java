package cn.appoa.doudoufriend.ui.first;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseFragment;

/**
 * 首页
 */
public class FirstFragment extends BaseFragment {

    @Override
    public View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_first, null);
        return view;
    }


    @Override
    public void initView(View view) {

    }


    @Override
    public void initData() {

    }

}
