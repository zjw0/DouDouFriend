package cn.appoa.doudoufriend.ui.first;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.MonthCalendar;
import com.necer.enumeration.MultipleNumModel;
import com.necer.enumeration.SelectedModel;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.necer.listener.OnCalendarStateChangedListener;

import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseFragment;
import cn.appoa.doudoufriend.db.MonthDate;


/**
 * 首页
 */
public class FirstFragment extends BaseFragment {

    //    @BindView(R.id.miui10Calendar)
//    Miui10Calendar miui10Calendar;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tv_sun_date)
    TextView tvSunDate;
    @BindView(R.id.tv_moon_date)
    TextView tvMoonDate;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.calendar)
    MonthCalendar calendar;
    @BindView(R.id.tv_confirm_add)
    TextView tvConfirmAdd;

    @Override
    public View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_first, null);
        return view;
    }


    @Override
    public void initView(View view) {
        AtyUtils.setPaddingTop(mActivity, llTop);
//        miui10Calendar.setSelectedMode(SelectedModel.MULTIPLE);
//        LigaturePainter painter = new LigaturePainter(mActivity);
//        miui10Calendar.setCalendarPainter(painter);
        calendar.setSelectedMode(SelectedModel.MULTIPLE);
        calendar.setMultipleNum(2, MultipleNumModel.FULL_REMOVE_FIRST);
        calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                tvSunDate.setText(year + "年" + month + "月");
                tvMoonDate.setText("" + baseCalendar.getCalendarPainter());
                AtyUtils.i("当前页面选中", "是" + localDate);
            }
        });
        calendar.setOnCalendarMultipleChangedListener(new OnCalendarMultipleChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currectSelectList, List<LocalDate> allSelectList) {
                tvStartDate.setText(year + "年" + month + "月" + " 当前页面选中 " + currectSelectList.size() + "个  总共选中" + allSelectList.size() + "个");
                if(currectSelectList.size()>=2){
                    String startYear = String.valueOf(currectSelectList.get(0).dayOfYear());
                    String startMonth = String.valueOf(currectSelectList.get(0).getYear());
                    String startDay = String.valueOf(currectSelectList.get(0).getDayOfYear());
                    String endDay = String.valueOf(currectSelectList.get(1).getDayOfYear());
                    tvStartDate.setText(startYear + "-" + startMonth + "-" + startDay);
                    tvEndDate.setText(endDay);
                    //int days = calendar.getTwoDateCount(currectSelectList.get(0),currectSelectList.get(1),2);
                    int days = baseCalendar.getChildCount();
                    tvDays.setText(days + "天");
                }
            }
        });
    }


    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_confirm_add, R.id.tv_sun_date, R.id.tv_moon_date, R.id.calendar, R.id.tv_today})
    public void onClicked(View v) {
//        Toast.makeText(mActivity, "is a click", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.tv_confirm_add:
                AtyUtils.showShort(mActivity, "添加成功", false);
                break;
            case R.id.tv_sun_date:
                //AtyUtils.showShort(mActivity, "添加成功1", false);
                break;
            case R.id.tv_moon_date:
                List<MonthDate> monthDates = LitePal.findAll(MonthDate.class);
                for (int i = 0; i < monthDates.size(); i++) {
                    tvMoonDate.setText(monthDates.get(i).getEndDate());
                }
                break;
            case R.id.tv_today:
                calendar.toToday();
                break;
            default:
                break;
        }
    }

}
