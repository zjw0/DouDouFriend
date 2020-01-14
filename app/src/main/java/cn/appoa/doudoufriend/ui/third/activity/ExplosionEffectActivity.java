package cn.appoa.doudoufriend.ui.third.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import cn.appoa.aframework.titlebar.BaseTitlebar;
import cn.appoa.aframework.titlebar.DefaultTitlebar;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.base.BaseActivity;
import cn.appoa.doudoufriend.ui.third.animation.ExplosionField;

public class ExplosionEffectActivity extends BaseActivity {

    @Override
    public BaseTitlebar initTitlebar() {
        return new DefaultTitlebar.Builder(this)
                .setBackImage(R.drawable.back_white)
                .setTitle("爆炸效果")
                .setLineHeight(0.0f)
//                .setLineColor(ContextCompat.getColor(mActivity,R.color.colorBlack))
                .create();
    }

    /**
     * 加载布局文件，添加点击事件
     */

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_explosion_effect);
//        setContent(R.layout.activity_coordinator_layout);
    }

    @Override
    public void initData() {
        initViewsClick();
    }

    /**
     * 添加点击事件的实现
     */
    private void initViewsClick() {
        // 为单个View添加点击事件
        final View title = findViewById(R.id.title_tv);
        title.setOnClickListener(v ->
                new ExplosionField(ExplosionEffectActivity.this).explode(title, null));

        // 为中间3个View添加点击事件
        setSelfAndChildDisappearOnClick(findViewById(R.id.title_disappear_ll));
        // 为下面3个View添加点击事件
        setSelfAndChildDisappearAndAppearOnClick(findViewById(R.id.title_disappear_and_appear_ll));

        // 跳转到github网页的点击事件
        findViewById(R.id.github_tv).setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(getString(R.string.bai_du));
            intent.setData(content_url);
            startActivity(intent);
        });
    }

    /**
     * 为自己以及子View添加破碎动画，动画结束后，把View消失掉
     *
     * @param view 可能是ViewGroup的view
     */
    private void setSelfAndChildDisappearOnClick(final View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setSelfAndChildDisappearOnClick(viewGroup.getChildAt(i));
            }
        } else {
            view.setOnClickListener(v ->
                    new ExplosionField(ExplosionEffectActivity.this).explode(view,
                            new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    view.setVisibility(View.GONE);
                                }
                            }));
        }
    }

    /**
     * 为自己以及子View添加破碎动画，动画结束后，View自动出现
     *
     * @param view 可能是ViewGroup的view
     */
    private void setSelfAndChildDisappearAndAppearOnClick(final View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setSelfAndChildDisappearAndAppearOnClick(viewGroup.getChildAt(i));
            }
        } else {
            view.setOnClickListener(v ->
                    new ExplosionField(ExplosionEffectActivity.this).explode(view, null));
        }
    }
}
