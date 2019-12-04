package cn.appoa.doudoufriend.presenter;

import cn.appoa.aframework.presenter.VersionPresenter;
import cn.appoa.aframework.view.IBaseView;
import cn.appoa.doudoufriend.view.MainView;


public class MainPresenter extends VersionPresenter {

    protected MainView mMainView;

    @Override
    public void onAttach(IBaseView view) {
        super.onAttach(view);
        if (view instanceof MainView) {
            mMainView = (MainView) view;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mMainView != null) {
            mMainView = null;
        }
    }

}
