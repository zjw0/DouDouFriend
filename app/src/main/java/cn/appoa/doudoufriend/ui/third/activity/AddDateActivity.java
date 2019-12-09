package cn.appoa.doudoufriend.ui.third.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.appoa.aframework.listener.OnCallbackListener;
import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.utils.DateUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.db.MonthDate;
import cn.appoa.doudoufriend.dialog.DatePickerDialog;
import cn.appoa.doudoufriend.utils.TimeDifferencesUtils;

public class AddDateActivity extends BaseActivity
        implements View.OnClickListener {

    private DatePickerDialog dialogDate, endDialogDate;
    private LinearLayout ll_start;
    private LinearLayout ll_end;
    private TextView tv_start;
    private TextView tv_end;
    private TextView tv_days;
    private TextView tv_confirm_add;
    private String nowDate;
    private String afterDate;
    private String startDate = "";
    private String endDate = "";
    private int days = 0;

    @Override
    public void initIntent(Intent intent) {

    }

    @Override
    public BaseTitlebar initTitlebar() {
        return new DefaultTitlebar.Builder(this)
                .setBackImage(R.drawable.back_white)
                .setTitle("添加月记")
                .create();
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_add_date);
    }

    @Override
    public void initView() {
        super.initView();
        ll_start = (LinearLayout) findViewById(R.id.ll_start);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_end = (TextView) findViewById(R.id.tv_end);
        tv_days = (TextView) findViewById(R.id.tv_days);
        tv_confirm_add = (TextView) findViewById(R.id.tv_confirm_add);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        tv_confirm_add.setOnClickListener(this);

    }

    @Override
    public void initData() {
        getMobSystemTime();
        //选择时间
        dialogDate = new DatePickerDialog(mActivity, new OnCallbackListener() {
            @Override
            public void onCallback(int type, Object... obj) {
                startDate = (String) obj[0];
                tv_start.setText(startDate);
                if(!TextUtils.isEmpty(AtyUtils.getText(tv_start)) && !TextUtils.isEmpty(AtyUtils.getText(tv_end))){
                    isSave(false);
                }
            }
        }, 1);

        //选择时间
        endDialogDate = new DatePickerDialog(mActivity, new OnCallbackListener() {
            @Override
            public void onCallback(int type, Object... obj) {
                endDate = (String) obj[0];
                tv_end.setText(endDate);
                isSave(false);
            }
        }, 2);
    }

    private void getMobSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        nowDate = simpleDateFormat.format(date);
        afterDate = simpleDateFormat.format(AtyUtils.getDateAfter(date,1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                dialogDate.initData(nowDate, "2100-01-01");
                dialogDate.showDatePickerDialog("开始日期", nowDate);
                break;
            case R.id.tv_end:
                if (TextUtils.isEmpty(AtyUtils.getText(tv_start))) {
                    AtyUtils.showShort(mActivity, "请先选择开始日期", false);
                }else {
                    endDialogDate.initData(afterDate, "2100-01-01");
                    endDialogDate.showDatePickerDialog("结束日期", afterDate);
                }
                break;
            case R.id.tv_confirm_add:
                //保存记录
                isSave(true);
                break;
            default:
                break;
        }
    }

    private void isSave(boolean bo) {
        if (TextUtils.isEmpty(AtyUtils.getText(tv_start))) {
            AtyUtils.showShort(mActivity, "请选择开始日期", false);
            return;
        }
        if (TextUtils.isEmpty(AtyUtils.getText(tv_end))) {
            AtyUtils.showShort(mActivity, "请选择结束日期", false);
            return;
        }
        if (!DateUtils.getTimeHistory(AtyUtils.getText(tv_start), AtyUtils.getText(tv_end))) {
            AtyUtils.showShort(mActivity, "结束日期必须大于开始日期", false);
            tv_days.setText("");
            return;
        }
        days = TimeDifferencesUtils.getTimeDifferences(AtyUtils.getText(tv_end) + " 00:00:00",
                AtyUtils.getText(tv_start) + " 00:00:00");
        if(days <= 0){
            AtyUtils.showShort(mActivity, "选择时间有误，请重新选择", false);
            tv_days.setText("");
        }else {
            tv_days.setText(days + "天");
        }
        if(bo){
            saveDate();
        }
    }

    private void saveDate() {
        SQLiteDatabase db = LitePal.getDatabase();
        MonthDate monthDate = new MonthDate();
        //monthDate.setId(1);
        monthDate.setStartDate(AtyUtils.getText(tv_start));
        monthDate.setEndDate(AtyUtils.getText(tv_end));
        monthDate.setDays(days);
        monthDate.save();
        AtyUtils.showShort(mActivity, "添加成功", false);
        finish();
    }

}
