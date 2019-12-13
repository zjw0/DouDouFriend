package cn.appoa.doudoufriend.ui.third;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangzhen.statusbar.DarkStatusBar;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.adapter.UserMenuListAdapter;
import cn.appoa.doudoufriend.base.BaseFragment;
import cn.appoa.doudoufriend.bean.UserMenuList;
import cn.appoa.doudoufriend.ui.third.activity.AddDateActivity;
import cn.appoa.doudoufriend.ui.third.activity.SettingActivity;
import cn.appoa.doudoufriend.ui.third.activity.ShowBigImageListActivity;

/**
 * 我的
 */
public class ThirdFragment extends BaseFragment implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener {

    private RecyclerView rv_user_info;
    private UserMenuListAdapter menuListAdapter;
    private List<UserMenuList> menuLists;
    private ImageView iv_user_avatar;

    @Override
    public View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_third, null);
        return view;
    }


    @Override
    public void initView(View view) {
        DarkStatusBar.get().fitLight(mActivity);//亮色模式
        rv_user_info = view.findViewById(R.id.rv_user_info);
        rv_user_info.setLayoutManager(new LinearLayoutManager(mActivity));
        menuLists = new ArrayList<>();
        menuListAdapter = new UserMenuListAdapter(R.layout.item_user_menu_list, menuLists);
        rv_user_info.setAdapter(menuListAdapter);
        iv_user_avatar = view.findViewById(R.id.iv_user_avatar);
        iv_user_avatar.setOnClickListener(this);

    }


    @Override
    public void initData() {
        menuLists.clear();
        menuLists.add(new UserMenuList(1, R.drawable.menu1, "添加月记", AddDateActivity.class));
        menuLists.add(new UserMenuList(2, R.drawable.menu1, "更多设置", SettingActivity.class));
        menuListAdapter.setNewData(menuLists);
        menuListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_avatar:
                startActivity(new Intent(mActivity, ShowBigImageListActivity.class));
                break;

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        UserMenuList item = menuLists.get(position);
        if (item.clazz == null) return;
        Intent intent = null;
        switch (item.MenuIntro) {
            case "添加月记":
                intent = new Intent(mActivity, item.clazz);
                break;
            case "更多设置":
                intent = new Intent(mActivity, item.clazz);
                break;
            default:
                intent = new Intent(mActivity, item.clazz);
                break;
        }
        startActivity(intent);
    }
}
