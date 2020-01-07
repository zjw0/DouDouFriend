package cn.appoa.doudoufriend.ui.third.speech;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.ui.third.speech.util.SettingTextWatcher;


/**
 * 合成设置界面
 */
public class TtsSettings extends PreferenceActivity implements OnPreferenceChangeListener {
	
//	public static final String PREFER_NAME = "com.iflytek.setting";
	public static final String PREFER_NAME = "cn.appoa.doudoufriend.ui.third.speech";
	private EditTextPreference mSpeedPreference;
	private EditTextPreference mPitchPreference;
	private EditTextPreference mVolumePreference;
	private ListView listView;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//使用布局文件设置与顶部间距
		setContentView(R.layout.activity_settings_preference_title);
		//添加ListView底部分割线
		listView = (ListView) findViewById(android.R.id.list);
		listView.addFooterView(new View(this));
		// 指定保存文件名字
		getPreferenceManager().setSharedPreferencesName(PREFER_NAME);
		addPreferencesFromResource(R.xml.tts_setting);
		mSpeedPreference = (EditTextPreference)findPreference("speed_preference");
		mSpeedPreference.getEditText().addTextChangedListener(new SettingTextWatcher(TtsSettings.this,mSpeedPreference,0,200));
		
		mPitchPreference = (EditTextPreference)findPreference("pitch_preference");
		mPitchPreference.getEditText().addTextChangedListener(new SettingTextWatcher(TtsSettings.this,mPitchPreference,0,100));
		
		mVolumePreference = (EditTextPreference)findPreference("volume_preference");
		mVolumePreference.getEditText().addTextChangedListener(new SettingTextWatcher(TtsSettings.this,mVolumePreference,0,100));
		
	}
	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		return true;
	}		
	
	
}