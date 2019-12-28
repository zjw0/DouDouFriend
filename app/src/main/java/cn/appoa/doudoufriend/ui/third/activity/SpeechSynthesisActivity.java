package cn.appoa.doudoufriend.ui.third.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;

public class SpeechSynthesisActivity extends BaseActivity {

    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    @Override
    public BaseTitlebar initTitlebar() {
        return new DefaultTitlebar.Builder(this)
                .setBackImage(R.drawable.back_white)
                .setTitle("语音合成")
                .create();
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_speech_synthesis);

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.et_intro, R.id.tv_ok})
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                AtyUtils.showShort(mActivity, "合成成功", false);
                break;
        }
    }
}
