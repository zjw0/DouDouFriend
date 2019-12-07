package cn.appoa.doudoufriend.ui.third.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.appoa.aframework.listener.OnCallbackListener;
import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.dialog.DatePickerDialog;
import cn.appoa.doudoufriend.ui.WebViewActivity;

public class AddDateActivity extends BaseActivity
        implements View.OnClickListener {

    private DatePickerDialog dialogDate, endDialogDate;
    private LinearLayout ll_start;
    private LinearLayout ll_end;
    private TextView tv_start;
    private TextView tv_end;
    private TextView tv_confirm_add;
    private String nowDate;
    private String startDate = "";
    private String endDate = "";

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
            }
        }, 1);

        //选择时间
        endDialogDate = new DatePickerDialog(mActivity, new OnCallbackListener() {
            @Override
            public void onCallback(int type, Object... obj) {
                endDate = (String) obj[0];
                tv_end.setText(endDate);
            }
        }, 2);
    }

    private void getMobSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        nowDate = simpleDateFormat.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                dialogDate.initData(nowDate, "2100-01-01");
                dialogDate.showDatePickerDialog("开始日期", nowDate);
                break;
            case R.id.tv_end:
                endDialogDate.initData(nowDate, "2100-01-01");
                endDialogDate.showDatePickerDialog("结束日期", nowDate);
                break;
            case R.id.tv_confirm_add:
                //保存记录
                saveDate();
                break;
            default:
                break;
        }
    }

    private void saveDate() {
        if (TextUtils.isEmpty(AtyUtils.getText(tv_start))) {
            AtyUtils.showShort(mActivity, "请选择开始日期", false);
            return;
        }
        if (TextUtils.isEmpty(AtyUtils.getText(tv_end))) {
            AtyUtils.showShort(mActivity, "请选择结束日期", false);
            return;
        }
        AtyUtils.showShort(mActivity, "添加成功", false);
        finish();
    }

}
