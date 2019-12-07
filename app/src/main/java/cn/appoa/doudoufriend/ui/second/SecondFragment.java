package cn.appoa.doudoufriend.ui.second;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.appoa.aframework.listener.VolleyDatasListener;
import cn.appoa.aframework.listener.VolleyErrorListener;
import cn.appoa.aframework.utils.AtyUtils;
import cn.appoa.doudoufriend.R;
import cn.appoa.doudoufriend.adapter.MyDateAdapter;
import cn.appoa.doudoufriend.base.BaseFragment;
import cn.appoa.doudoufriend.bean.DateList;
import zm.http.volley.ZmVolley;
import zm.http.volley.request.ZmStringRequest;

/**
 * 月记
 */
public class SecondFragment extends BaseFragment {

    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_second_date)
    RecyclerView rvSecondDate;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int page_index = 1;
    private List<DateList> dataList;
    private boolean first = true;

    @Override
    public View initFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_second, null);
        return view;
    }


    @Override
    public void initView(View view) {
        AtyUtils.setPaddingTop(mActivity, llTop);
        dataList = new ArrayList<>();
        rvSecondDate.setLayoutManager(new LinearLayoutManager(mActivity));
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page_index = 1;
                dataList.clear();
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page_index++;
                getDateList(page_index);
            }
        });
    }


    @Override
    public void initData() {
        getDateList(page_index);
    }

    //获取月记列表
    private void getDateList(final int page_index) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", page_index + "");
        params.put("pageSize", "30");
        ZmVolley.request(new ZmStringRequest(null, params,
                new VolleyDatasListener<DateList>(this, "月记列表", DateList.class) {
                    @Override
                    public void onDatasResponse(List<DateList> datas) {
                        refreshLayout.finishRefresh();//结束刷新
                        refreshLayout.finishLoadMore();//结束加载
                        if (datas != null && datas.size() > 0) {
                            dataList.addAll(datas);
                            rvSecondDate.setAdapter(new MyDateAdapter(R.layout.item_date_list, dataList));
                        }
                    }
                },
                new VolleyErrorListener(this, "月记列表") {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);
                        //TODO 模拟数据
                        List<DateList> datas = new ArrayList<>();
                        for (int i = 0; i < 30; i++) {
                            DateList data = new DateList();
                            data.id = i + 1 + "";
                            data.startDate = "2019-01-" + i+1;
                            data.days = (i + 1) +"天";
                            data.endDate = "2019-01-" + i+1;
                            datas.add(data);
                        }
                        dataList.addAll(datas);
                        rvSecondDate.setAdapter(new MyDateAdapter(R.layout.item_date_list, dataList));
                        if(first){
                            first = false;
                        }else {
                            // 这两个方法是在加载失败时调用的
                            refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                            refreshLayout.finishLoadMore(false);//结束加载（加载失败）
                        }
                    }
                }), REQUEST_TAG);
    }



}
