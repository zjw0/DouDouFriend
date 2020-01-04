package cn.appoa.doudoufriend.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.litepal.LitePal;

import java.util.List;

import cn.appoa.aframework.activity.AfActivity;
import cn.appoa.aframework.dialog.DefaultHintDialog;
import cn.appoa.aframework.listener.ConfirmHintDialogListener;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.bean.DateList;
import cn.appoa.doudoufriend.db.MonthDate;
import cn.appoa.doudoufriend.even.DateEvent;
import cn.appoa.doudoufriend.ui.second.SecondFragment;
import zm.bus.event.BusProvider;

public class MyDateAdapter extends BaseQuickAdapter<DateList, BaseViewHolder> {

    public MyDateAdapter(int layoutResId, @Nullable List<DateList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final DateList item) {
        helper.setText(R.id.tv_start_date, item.startDate);
        helper.setText(R.id.tv_days, item.days + "天");
        helper.setText(R.id.tv_end_date, item.endDate);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DefaultHintDialog(mContext).showHintDialog2("取消",
                        "确认", "删除记录", "是否删除选择日期？", new ConfirmHintDialogListener() {
                            @Override
                            public void clickConfirmButton() {
                                int deleteCount = LitePal.delete(MonthDate.class, item.id);
                                if(deleteCount > 0){
                                    BusProvider.getInstance().post(new DateEvent(1));
                                    AtyUtils.showShort(mContext,"删除成功！",false);
                                }
                                notifyDataSetChanged();
                            }
                        });
            }
        });
    }


}
