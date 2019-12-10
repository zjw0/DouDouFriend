package cn.appoa.doudoufriend.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.bean.DateList;

public class MyDateAdapter extends BaseQuickAdapter<DateList, BaseViewHolder> {

    public MyDateAdapter(int layoutResId, @Nullable List<DateList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateList item) {
        helper.setText(R.id.tv_start_date, item.startDate);
        helper.setText(R.id.tv_days, item.days + "天");
        helper.setText(R.id.tv_end_date, item.endDate);
    }

}
