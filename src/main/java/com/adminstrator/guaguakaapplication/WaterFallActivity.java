package com.adminstrator.guaguakaapplication;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.adminstrator.guaguakaapplication.widget.StaggeredDividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class WaterFallActivity extends AppCompatActivity {

    private ArrayList<Integer> imageIds = new ArrayList<>();
    private int[] ids = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,};

    private RecyclerView rv_waterfall;
    private DemoAdapter adapter;

    private SmartRefreshLayout refreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_fall);

        refreshlayout = findViewById(R.id.refreshlayout);

        rv_waterfall = findViewById(R.id.rv_waterfall);
        rv_waterfall.setHasFixedSize(true);
        rv_waterfall.setItemAnimator(null);
        //垂直方向的2列
         final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //防止Item切换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_waterfall.setLayoutManager(layoutManager);
        final int spanCount = 2;
        rv_waterfall.addItemDecoration(new StaggeredDividerItemDecoration(this,10,spanCount));

        //解决底部滚动到顶部时，顶部item上方偶尔会出现一大片间隔的问题
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

        //设置Adapter
        for(int i = 0 ; i < ids.length;i++){
            imageIds.add(ids[i]);
        }
        adapter = new DemoAdapter();
        rv_waterfall.setAdapter(adapter);
        adapter.replaceAll(imageIds);

        //设置下拉刷新和上拉加载监听
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.replaceAll(getData());
                        refreshLayout.finishRefresh();
                    }
                },2000);
            }
        });

        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addData(adapter.getItemCount(),getData());
                        refreshLayout.finishLoadMore();
                    }
                },2000);
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


}
