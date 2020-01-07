package cn.appoa.doudoufriend.ui.third.speech;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.MemoryFile;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.msc.util.FileUtil;
import com.iflytek.cloud.msc.util.log.DebugLog;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;
import butterknife.OnClick;
import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;

public class SpeechSynthesisActivity extends BaseActivity {

    private static String TAG = SpeechSynthesisActivity.class.getSimpleName();

    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_pause)
    TextView tvPause;
    @BindView(R.id.tv_resume)
    TextView tvResume;

    // 默认发音人
    private String voicer = "xiaoyan";
    // 语音合成对象
    private SpeechSynthesizer mTts;
    String texts = "";
    private SharedPreferences mSharedPreferences;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    MemoryFile memFile;
    public volatile long mTotalSize = 0;

    private Vector<byte[]> container = new Vector<>();

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
        etIntro.setText(R.string.text_tts_source);
    }

    @Override
    public void initData() {
        DebugLog.setShowLog(true);
        mEngineType = SpeechConstant.TYPE_CLOUD;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(SpeechSynthesisActivity.this, mTtsInitListener);
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);

    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码：" + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            Log.e("MscSpeechLog_", "percent =" + percent);
            mPercentForBuffering = percent;
            showTip(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            Log.e("MscSpeechLog_", "percent =" + percent);
            mPercentForPlaying = percent;
            showTip(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));

            SpannableStringBuilder style = new SpannableStringBuilder(texts);
            Log.e(TAG, "beginPos = " + beginPos + "  endPos = " + endPos);
            style.setSpan(new BackgroundColorSpan(Color.RED), beginPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((EditText) findViewById(R.id.et_intro)).setText(style);
        }

        @Override
        public void onCompleted(SpeechError error) {
            System.out.println("oncompleted");
            if (error == null) {
                //	showTip("播放完成");
                DebugLog.LogD("播放完成," + container.size());
                try {
                    for (int i = 0; i < container.size(); i++) {
                        writeToFile(container.get(i));
                    }
                } catch (IOException e) {

                }
                FileUtil.saveFile(memFile, mTotalSize, Environment.getExternalStorageDirectory() + "/1.pcm");


            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            //	 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }

            //当设置SpeechConstant.TTS_DATA_NOTIFY为1时，抛出buf数据
            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
                Log.e("MscSpeechLog_", "bufis =" + buf.length);
                container.add(buf);
            }


        }
    };

    private void showTip(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT);
//        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //支持实时音频返回，仅在synthesizeToUri条件下支持
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            //	mTts.setParameter(SpeechConstant.TTS_BUFFER_TIME,"1");

            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");

        }

        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    private void writeToFile(byte[] data) throws IOException {
        if (data == null || data.length == 0)
            return;
        try {
            if (memFile == null) {
                Log.e("MscSpeechLog_", "ffffffffff");
                String mFilepath = Environment.getExternalStorageDirectory() + "/1.pcm";
                memFile = new MemoryFile(mFilepath, 1920000);
                memFile.allowPurging(false);
            }
            memFile.writeBytes(data, 0, (int) mTotalSize, data.length);
            mTotalSize += data.length;
        } finally {
        }
    }

    @OnClick({R.id.et_intro, R.id.tv_ok, R.id.tv_cancel, R.id.tv_pause, R.id.tv_resume})
    public void onClicked(View v) {
        if (null == mTts) {
            AtyUtils.showShort(mActivity, "创建对象失败", false);
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        switch (v.getId()) {
            case R.id.tv_ok:
                if (TextUtils.isEmpty(AtyUtils.getText(etIntro))) {
                    AtyUtils.showShort(mActivity, "请输入合成内容！", false);
                    return;
                }
                // 移动数据分析，收集开始合成事件
                /*FlowerCollector.onEvent(TtsDemo.this, "tts_play");*/

                //texts = ((EditText) findViewById(R.id.tts_text)).getText().toString();
                texts = etIntro.getText().toString();
                // 设置参数
                setParam();
                int code = mTts.startSpeaking(texts, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
                String path = Environment.getExternalStorageDirectory() + "/tts.pcm";
                //	int code = mTts.synthesizeToUri(texts, path, mTtsListener);

                if (code != ErrorCode.SUCCESS) {
                    showTip("语音合成失败,错误码: " + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
//                    AtyUtils.showShort(mActivity,"语音合成失败,错误码: " + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案",false);
                } else {
                    AtyUtils.showShort(mActivity, "合成成功", false);
                }
                break;
            // 取消合成
            case R.id.tv_cancel:
                mTts.stopSpeaking();
                break;
            // 暂停播放
            case R.id.tv_pause:
                mTts.pauseSpeaking();
                break;
            // 继续播放
            case R.id.tv_resume:
                mTts.resumeSpeaking();
                break;
        }
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
		/*FlowerCollector.onResume(TtsDemo.this);
		FlowerCollector.onPageStart(TAG);*/
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
		/*FlowerCollector.onPageEnd(TAG);
		FlowerCollector.onPause(TtsDemo.this);*/
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }
}
