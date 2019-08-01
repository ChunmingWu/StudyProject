package com.adminstrator.guaguakaapplication;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.adminstrator.guaguakaapplication.widget.StaggeredDividerItemDecoration;
import com.adminstrator.guaguakaapplication.widget.refreshView.EndlessRecyclerOnScrollListener;
import com.adminstrator.guaguakaapplication.widget.refreshView.LoadMoreWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WaterFallActivity extends AppCompatActivity {

    private RecyclerView rv_waterfall;
    private ArrayList<Integer> imageIds = new ArrayList<>();
    private int[] ids = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,};

    private DemoAdapter adapter;
    private LoadMoreWrapper wrapper;

    private SwipeRefreshLayout swiperefreshlayout_water_fall;

    private SmartRefreshLayout refreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_fall);

        refreshlayout = findViewById(R.id.refreshlayout);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                },2000);
            }
        });

        swiperefreshlayout_water_fall = findViewById(R.id.swiperefreshlayout_water_fall);
        swiperefreshlayout_water_fall.setColorSchemeColors(Color.BLUE);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swiperefreshlayout_water_fall.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        swiperefreshlayout_water_fall.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swiperefreshlayout_water_fall.setSize(SwipeRefreshLayout.LARGE);
        swiperefreshlayout_water_fall.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟加载网络数据，这里设置4秒
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //显示或隐藏刷新进度条
                        swiperefreshlayout_water_fall.setRefreshing(false);
                        adapter.replaceAll(getData());
                        swiperefreshlayout_water_fall.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        rv_waterfall = findViewById(R.id.rv_waterfall);
        rv_waterfall.setHasFixedSize(true);
        //垂直方向的三列
         final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        rv_waterfall.setLayoutManager(layoutManager);
        final int spanCount = 2;
        rv_waterfall.addItemDecoration(new StaggeredDividerItemDecoration(this,10,spanCount));

        //从底部滚动到顶部时，会发现顶部item上方偶尔会出现一大片间隔
        rv_waterfall.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[spanCount];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });

        for(int i = 0 ; i < ids.length;i++){
            imageIds.add(ids[i]);
        }

        adapter = new DemoAdapter();
        wrapper = new LoadMoreWrapper(adapter);
        rv_waterfall.setAdapter(wrapper);
        adapter.replaceAll(imageIds);
        rv_waterfall.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                wrapper.setLoadState(wrapper.LOADING);

                if (imageIds.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.addData(adapter.getItemCount() ,getData());
                                    wrapper.setLoadState(wrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    wrapper.setLoadState(wrapper.LOADING_END);
                }
            }


        });
    }

    private ArrayList<Integer>  getData() {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0 ; i < 6;i++){
            list.add(ids[i]);
        }
        return list;
    }

    private void getAllImageIds(){
        for(int i = 0 ; i < ids.length;i++){
            imageIds.add(ids[i]);
        }
    }
}
