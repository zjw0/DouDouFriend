package cn.appoa.doudoufriend.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.bean.UserMenuList;

public class UserMenuListAdapter extends BaseQuickAdapter<UserMenuList, BaseViewHolder> {

    public UserMenuListAdapter(int layoutResId, @Nullable List<UserMenuList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserMenuList item) {
        TextView tv_menu_name = helper.getView(R.id.tv_menu_name);
        tv_menu_name.setText(item.MenuIntro);
        tv_menu_name.setCompoundDrawablesWithIntrinsicBounds(item.Icon, 0, R.drawable.next_step, 0);
    }
}
