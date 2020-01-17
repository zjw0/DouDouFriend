package cn.appoa.doudoufriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.even.DateEvent;
import cn.appoa.doudoufriend.presenter.MainPresenter;
import cn.appoa.doudoufriend.ui.first.FirstFragment;
import cn.appoa.doudoufriend.ui.second.SecondFragment;
import cn.appoa.doudoufriend.ui.third.ThirdFragment;
import cn.appoa.doudoufriend.utils.Bug;
import cn.appoa.doudoufriend.view.MainView;
import zm.bus.event.BusProvider;

public class MainActivity extends BaseActivity<MainPresenter>
        implements MainView, CompoundButton.OnCheckedChangeListener {


    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_main);
    }

    private RadioButton btn_main_tab1;
    private RadioButton btn_main_tab2;
    private RadioButton btn_main_tab3;

    @Override
    public void initView() {
        super.initView();
        btn_main_tab1 = (RadioButton) findViewById(R.id.btn_main_tab1);
        btn_main_tab2 = (RadioButton) findViewById(R.id.btn_main_tab2);
        btn_main_tab3 = (RadioButton) findViewById(R.id.btn_main_tab3);

        btn_main_tab1.setOnCheckedChangeListener(this);
        btn_main_tab2.setOnCheckedChangeListener(this);
        btn_main_tab3.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            return;
        }
        switch (buttonView.getId()) {
            case R.id.btn_main_tab1:
                setTabSelection(1);
                break;
            case R.id.btn_main_tab2:
                setTabSelection(2);
                break;
            case R.id.btn_main_tab3:
                setTabSelection(3);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra("index", 0);
        switch (index) {
            case 1:
                btn_main_tab1.setChecked(true);
                break;
            case 2:
                btn_main_tab2.setChecked(true);
                break;
            case 3:
                btn_main_tab3.setChecked(true);
                break;
        }
    }

    @SuppressWarnings("unused")
    private int index;
    private FirstFragment fragment1;
    private SecondFragment fragment2;
    private ThirdFragment fragment3;

    private void setTabSelection(int index) {
        this.index = index;
        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case 1:
                if (fragment1 == null) {
                    fragment1 = new FirstFragment();
                    beginTransaction.add(R.id.fl_main_fragment, fragment1);
                } else {
                    beginTransaction.show(fragment1);
                    fragment1.notifyData();
                }
                break;
            case 2:
                if (fragment2 == null) {
                    fragment2 = new SecondFragment();
                    beginTransaction.add(R.id.fl_main_fragment, fragment2);
                } else {
                    BusProvider.getInstance().post(new DateEvent(1));
                    beginTransaction.show(fragment2);
                    fragment2.notifyData();
                }
                break;
            case 3:
                if (fragment3 == null) {
                    fragment3 = new ThirdFragment();
                    beginTransaction.add(R.id.fl_main_fragment, fragment3);
                } else {
                    beginTransaction.show(fragment3);
                    fragment3.notifyData();
                }
                break;
        }
        beginTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏全部Fragment
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (transaction == null)
            return;
        if (fragment1 != null)
            transaction.hide(fragment1);
        if (fragment2 != null)
            transaction.hide(fragment2);
        if (fragment3 != null)
            transaction.hide(fragment3);
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onAttachView() {
        mPresenter.onAttach(this);
    }

    @Override
    public void initData() {
        Bug.bug();
//        String[] permissions = {
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        requestPermissions(permissions, new PermissionsResultAction() {
//            @Override
//            public void onGranted() {
//                btn_main_tab1.setChecked(true);
//            }
//
//            @Override
//            public void onDenied(String s) {
//                AtyUtils.showShort(mActivity,"请开启权限",false);
//            }
//        });

        btn_main_tab1.setChecked(true);
    }


    @Override
    public boolean enableSliding() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && //
                event.getAction() == KeyEvent.ACTION_DOWN) {
            doubleClickExit(2000, "再按一次返回键退出程序");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
