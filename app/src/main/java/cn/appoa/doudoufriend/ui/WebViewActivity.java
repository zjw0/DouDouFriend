package cn.appoa.doudoufriend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.app.MyApplication;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.net.API;


/**
 * 单页
 */
public class WebViewActivity extends BaseActivity {

    /**
     * 0我的详情 1关于我们
     */
    private int type;
    private String id;

    @Override
    public void initIntent(Intent intent) {
        type = intent.getIntExtra("type", 0);
    }

    @Override
    public BaseTitlebar initTitlebar() {
        DefaultTitlebar.Builder builder = new DefaultTitlebar.Builder(this)
                .setBackImage(R.drawable.back_white);
        switch (type) {
            case 0://我的
                builder.setTitle("详情");
                break;
            case 1://关于
                builder.setTitle("关于我们");
                break;
            default:
                break;
        }
        return builder.create();
    }

    private WebView webView;

    @Override
    public void initContent(Bundle savedInstanceState) {
        webView = new WebView(mActivity);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        setContent(webView);
    }

    @Override
    public void initView() {
        super.initView();
    }


    @Override
    public void initData() {
        String start;
        String image;
        String Contents;
        switch (type) {
            case 0:
                start = "<div><font color='#000000' size='5'>" + "data.Title"
                        + "</font></div><p></p><div><span><font color='#aaaaaa' size='2'>" + "data.AddTime"
                        + "</font></span></div><p></p>";
                Contents = start + "data.Contents";
                break;
            default:
//                Contents = "data.Contents";
                Contents = "关于我们";
                break;
        }
        webView.loadDataWithBaseURL(API.IMAGE_URL, MyApplication.addData + Contents,
                "text/html", "UTF-8", null);

    }




}
