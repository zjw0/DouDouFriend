package cn.appoa.doudoufriend.ui.first;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.MonthCalendar;
import com.necer.entity.CalendarDate;
import com.necer.enumeration.MultipleNumModel;
import com.necer.enumeration.SelectedModel;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.necer.utils.CalendarUtil;

import org.joda.time.LocalDate;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.appoa.aframework.listener.OnCallbackListener;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseFragment;
import cn.appoa.doudoufriend.db.MonthDate;
import cn.appoa.doudoufriend.dialog.DatePickerDialog;
import cn.appoa.doudoufriend.utils.TimeDifferencesUtils;


/**
 * 首页
 */
public class FirstFragment extends BaseFragment {

//    @BindView(R.id.miui10Calendar)
//    Miui10Calendar miui10Calendar;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_two_choose)
    LinearLayout llTwoChoose;
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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private boolean isSave = false;
    private String startDate = "";
    private String endDate = "";
    private int daysDate = 0;
    private String nowDate = "2020-01-01";
    private DatePickerDialog dialogDate;

    @Override
    public View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_first, null);
        return view;
    }


    @Override
    public void initView(View view) {
        AtyUtils.setPaddingTop(mActivity, llTop);
        calendarOneSelected();
//        miui10Calendar.setSelectedMode(SelectedModel.MULTIPLE);
//        LigaturePainter painter = new LigaturePainter(mActivity);
//        miui10Calendar.setCalendarPainter(painter);
    }


    @Override
    public void initData() {
//        calendar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPointRed));
//        calendar.setBackgroundResource(R.drawable.icon_animal);

        //获取手机系统当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        nowDate =simpleDateFormat.format(date);

        //选择时间
        dialogDate = new DatePickerDialog(mActivity, new OnCallbackListener() {
            @Override
            public void onCallback(int type, Object... obj) {
                String jumpDate = (String) obj[0];
                calendar.jumpDate(jumpDate);
            }
        }, 1);
    }

    private void setAnimals(String animals) {
        switch (animals) {
            case "鼠":
                calendar.setBackgroundResource(R.drawable.icon_shu);
                break;
            case "牛":
                calendar.setBackgroundResource(R.drawable.icon_niu);
                break;
            case "虎":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "兔":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "龙":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "蛇":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "马":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "羊":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "猴":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "鸡":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "狗":
                calendar.setBackgroundResource(R.drawable.icon_animal);
                break;
            case "猪":
                calendar.setBackgroundResource(R.drawable.icon_zhu);
                break;
            default:
                calendar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorWhite));
                break;
        }
    }

    private void calendarOneSelected() {
        calendar.setSelectedMode(SelectedModel.SINGLE_SELECTED);
        calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                tvSunDate.setText(year + "年" + month + "月");
                CalendarDate calendarDate = CalendarUtil.getCalendarDate(localDate);
                tvMoonDate.setText(calendarDate.lunar.lunarYearStr + "年" + calendarDate.lunar.lunarMonthStr + calendarDate.lunar.lunarDayStr);
                tvStartDate.setText(null);
                tvEndDate.setText(null);
                tvDays.setText(null);
                AtyUtils.i("当前页面选中", "是" + localDate);
                setAnimals(calendarDate.lunar.animals);
                AtyUtils.i("当前页面选中", "生肖是" + calendarDate.lunar.animals);

            }
        });
    }

    private void calendarMoreSelected() {
        calendar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorWhite));
        calendar.setSelectedMode(SelectedModel.MULTIPLE);
        calendar.setMultipleNum(2, MultipleNumModel.FULL_REMOVE_FIRST);
        calendar.setOnCalendarMultipleChangedListener(new OnCalendarMultipleChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currectSelectList, List<LocalDate> allSelectList) {
                int isBack = -1;
                String startDay;
                String endDay;
                tvSunDate.setText(year + "年" + month + "月");
//                CalendarDate calendarDate = CalendarUtil.getCalendarDate(currectSelectList.get(0));
//                tvMoonDate.setText(calendarDate.lunar.lunarYearStr + calendarDate.lunar.lunarMonthStr + calendarDate.lunar.lunarDayStr);
                if (currectSelectList.size() <= 0) {
                    tvStartDate.setText("开始时间：");
                    tvEndDate.setText("结束时间：");
                    tvDays.setText("");
                    isBack = 0;
                }
                if (currectSelectList.size() == 1) {
                    startDay = String.valueOf(currectSelectList.get(0));
                    tvStartDate.setText("开始时间：" + startDay);
                    tvEndDate.setText("结束时间：");
                    tvDays.setText("");
                    isBack = 1;
                }
                if (currectSelectList.size() >= 2) {
                    startDay = String.valueOf(currectSelectList.get(0));
                    endDay = String.valueOf(currectSelectList.get(1));
                    int days = TimeDifferencesUtils.getTimeDifferences(startDay + " 00:00:00",
                            endDay + " 00:00:00");
                    if (days <= 0) {
//                        AtyUtils.showShort(mActivity, "结束日期必须大于开始日期", false);
                        tvStartDate.setText("开始时间：" + endDay);
                        tvEndDate.setText("结束时间：" + startDay);
                        tvDays.setText(Math.abs(days) + 1 + "天");
                        isBack = 2;
                    } else {
                        tvStartDate.setText("开始时间：" + startDay);
                        tvEndDate.setText("结束时间：" + endDay);
                        tvDays.setText(days + 1 + "天");
                        isBack = 2;
                    }
                    startDate = startDay;
                    endDate = endDay;
                    daysDate = Math.abs(days) + 1;
                }
                switch (isBack) {
                    case -1:
                        AtyUtils.showShort(mActivity, "请选择开始和结束时间", false);
                        isSave = false;
                        break;
                    case 0:
                        AtyUtils.showShort(mActivity, "请选择开始和结束时间", false);
                        isSave = false;
                        break;
                    case 1:
                        AtyUtils.showShort(mActivity, "请选择结束时间", false);
                        isSave = false;
                        break;
                    case 2:
                        isSave = true;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void saveDate(String startDate, String endDate, int days) {
        SQLiteDatabase db = LitePal.getDatabase();
        MonthDate monthDate = new MonthDate();
        monthDate.setStartDate(startDate);
        monthDate.setEndDate(endDate);
        monthDate.setDays(days);
        monthDate.save();
        AtyUtils.showShort(mActivity, "保存成功", false);
    }

    private void toOneSelected() {
        isSave = false;
        tvMoonDate.setVisibility(View.VISIBLE);
        llTwoChoose.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);
        calendar.notifyCalendar();
        calendar.toToday();
        calendarOneSelected();
        tvConfirmAdd.setText("添加");
        tvConfirmAdd.setBackgroundResource(R.drawable.shape_solid_theme_50dp);
    }


    @OnClick({R.id.tv_confirm_add, R.id.tv_sun_date, R.id.tv_moon_date, R.id.calendar, R.id.tv_today, R.id.iv_back})
    public void onClicked(View v) {
//        Toast.makeText(mActivity, "is a click", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.tv_confirm_add:
                if (isSave) {
                    saveDate(startDate, endDate, daysDate);
                    toOneSelected();
                } else {
                    isSave = true;
                    tvMoonDate.setVisibility(View.GONE);
                    llTwoChoose.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.VISIBLE);
                    calendar.notifyCalendar();
                    calendar.toToday();
                    calendarMoreSelected();
                    tvConfirmAdd.setText("保存");
                    tvConfirmAdd.setBackgroundResource(R.drawable.shape_solid_green_50dp);
                }
                break;
            case R.id.tv_sun_date:
                dialogDate.initData("1901-01-01", "2099-01-01");
                dialogDate.showDatePickerDialog("跳转日期", nowDate);
                break;
            case R.id.tv_moon_date:
                List<MonthDate> monthDates = LitePal.findAll(MonthDate.class);
                if (monthDates.size() > 0) {
                    tvMoonDate.setText("结束日期：" + monthDates.get(monthDates.size() - 1).getEndDate());
//                    for (int i = 0; i < monthDates.size(); i++) {
//                        tvMoonDate.setText( "结束日期：" + monthDates.get(i).getEndDate());
//                    }
                }
                break;
            case R.id.tv_today:
                calendar.toToday();
                break;
            case R.id.iv_back:
                toOneSelected();
                break;
            default:
                break;
        }
    }

}
