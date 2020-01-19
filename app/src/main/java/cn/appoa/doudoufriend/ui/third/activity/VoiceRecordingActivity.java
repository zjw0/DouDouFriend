package cn.appoa.doudoufriend.ui.third.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.utils.Bug;
import cn.appoa.wxvoiceselector.EaseVoicePlayerView;
import cn.appoa.wxvoiceselector.EaseVoiceRecorderView;

public class VoiceRecordingActivity extends BaseActivity implements View.OnClickListener,
        EaseVoiceRecorderView.EaseVoiceRecorderCallback {

    @Override
    public BaseTitlebar initTitlebar() {
        return new DefaultTitlebar.Builder(this).setTitle("语音上传")
                .setBackImage(R.drawable.back_white).create();
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_voice_recording);
    }

    private String msgId = "1";
    private EaseVoiceRecorderView voiceRecorderView;
    private TextView tv_push_to_talk;
    private LinearLayout ll_voice;
    private RelativeLayout rl_voice_play;
    private EaseVoicePlayerView playerView;
    private RelativeLayout rl_voice_delete;

    @Override
    public void initView() {
        super.initView();
        voiceRecorderView = (EaseVoiceRecorderView) findViewById(R.id.voiceRecorderView);
        tv_push_to_talk = (TextView) findViewById(R.id.tv_push_to_talk);
        ll_voice = (LinearLayout) findViewById(R.id.ll_voice);
        rl_voice_play = (RelativeLayout) findViewById(R.id.rl_voice_play);
        playerView = (EaseVoicePlayerView) findViewById(R.id.playerView);
        rl_voice_delete = (RelativeLayout) findViewById(R.id.rl_voice_delete);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {
        Bug.bug();

        tv_push_to_talk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (voiceRecorderView != null) {
                    return voiceRecorderView.onPressToSpeakBtnTouch(v, event, VoiceRecordingActivity.this);
                }
                return false;
            }
        });
        rl_voice_play.setOnClickListener(this);
        rl_voice_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_voice_play) {
            playerView.play();
        } else if (id == R.id.rl_voice_delete) {
            playerView.clearDataSource();
            tv_push_to_talk.setVisibility(View.VISIBLE);
            ll_voice.setVisibility(View.GONE);
        }
    }

    private String base64Voice = "";// 音频base64

    @Override
    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
        playerView.setDataSource(msgId, voiceFilePath, voiceTimeLength);
        tv_push_to_talk.setVisibility(View.GONE);
        ll_voice.setVisibility(View.VISIBLE);
        //音频base64
        try {
            File file = new File(voiceFilePath);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            base64Voice = Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
