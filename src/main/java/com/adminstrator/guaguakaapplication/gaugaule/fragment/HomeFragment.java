package com.adminstrator.guaguakaapplication.gaugaule.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adminstrator.guaguakaapplication.DemoAdapter;
import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.Util;
import com.adminstrator.guaguakaapplication.gaugaule.ExStaggeredGridLayoutManager;
import com.adminstrator.guaguakaapplication.gaugaule.inter.OnFragmentInteractionListener;
import com.adminstrator.guaguakaapplication.gaugaule.widget.PopupWin_Scratch_More;
import com.adminstrator.guaguakaapplication.widget.StaggeredDividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * 刮刮乐首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView iv_grandma_home,iv_bg_banner_home;
    private TextView tv_banner_home,tv_exit_home,tv_wallet_amount_home,tv_username_home;
    private RelativeLayout rl_banner_home,rl_title_home,rl_more_home;
    private SmartRefreshLayout refreshlayout_home;
    private RecyclerView rv_data_list;
    private ArrayList<Integer> imageIds = new ArrayList<>();
    private int[] ids = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p6,};
    private DemoAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        iv_grandma_home = view.findViewById(R.id.iv_grandma_home);
        iv_grandma_home.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
//        matrix.setScale(0.8f,0.8f);
        matrix.setTranslate(10,0);
        iv_grandma_home.setImageMatrix(matrix);
        Animation rotate = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anim_grandma);
        iv_grandma_home.setAnimation(rotate);
        iv_grandma_home.startAnimation(rotate);

        iv_bg_banner_home = view.findViewById(R.id.iv_bg_banner_home);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anim);
        iv_bg_banner_home.setAnimation(animation);
        iv_bg_banner_home.startAnimation(animation);

        tv_banner_home = view.findViewById(R.id.tv_banner_home);
        rl_banner_home = view.findViewById(R.id.rl_banner_home);
        rl_title_home = view.findViewById(R.id.rl_title_home);
        rl_more_home = view.findViewById(R.id.rl_more_home);
        tv_exit_home = view.findViewById(R.id.tv_exit_home);
        tv_wallet_amount_home = view.findViewById(R.id.tv_wallet_amount_home);
        tv_username_home = view.findViewById(R.id.tv_username_home);

        refreshlayout_home = view.findViewById(R.id.refreshlayout_home);
        rv_data_list = view.findViewById(R.id.rv_data_list);
        rv_data_list.setNestedScrollingEnabled(false);
        rv_data_list.setHasFixedSize(true);
        rv_data_list.setItemAnimator(null);
        final int spanCount = 2;
        //垂直方向的2列
        final StaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        //防止Item切换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_data_list.setLayoutManager(layoutManager);
        rv_data_list.addItemDecoration(new StaggeredDividerItemDecoration(getContext(),10,spanCount));
        //解决底部滚动到顶部时，顶部item上方偶尔会出现一大片间隔的问题
        rv_data_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        rv_data_list.setAdapter(adapter);
        adapter.replaceAll(imageIds);
        adapter.setOnItemClickLitener(new DemoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"click position:" + position,Toast.LENGTH_SHORT).show();
            }
        });


        refreshlayout_home.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.replaceAll(getData());
                        refreshlayout_home.finishRefresh();
                        layoutManager.invalidateSpanAssignments();
                    }
                },2000);
            }
        });
        refreshlayout_home.setEnableLoadMore(false);
        refreshlayout_home.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(adapter.getItemCount() > 20){
                            refreshlayout_home.finishLoadMore();
                            return;
                        }
                        adapter.addData(adapter.getItemCount(),getData());
                        refreshlayout_home.finishLoadMore();
                        layoutManager.invalidateSpanAssignments();

                    }
                },2000);
            }
        });
        rl_banner_home.setOnClickListener(this);
        rl_more_home.setOnClickListener(this);
        tv_exit_home.setOnClickListener(this);
    }

    private ArrayList<Integer>  getData() {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0 ; i < 6;i++){
            list.add(ids[i]);
        }
        return list;
    }

    private Animation scaleAnimation;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_banner_home:
                startScaleAnimation();
                break;
            case R.id.rl_more_home:
                showMorePopup();
                    break;
            case R.id.tv_exit_home:
                mListener.onFragmentInteraction(TAG,0);
                break;

        }
    }

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private void showMorePopup() {
        PopupWin_Scratch_More popupWin_scratch_more = new PopupWin_Scratch_More(getContext(),rl_title_home);
        popupWin_scratch_more.setOnItemClickLitener(new PopupWin_Scratch_More.OnItemClickLitener() {
            @Override
            public void onItemClick(int flag, String message) {
                if(null == manager){
                    manager = getActivity().getSupportFragmentManager();
                }
                if(flag == 0){
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fl_scratch_home,MyRecord_ScratchFragment.getInstance(tv_wallet_amount_home.getText().toString(),tv_username_home.getText().toString()));
                }else if(flag == 1){

                }else if(flag == 2){

                }
                transaction.addToBackStack(TAG);
                transaction.commit();
            }
        });
    }

    private void startScaleAnimation() {
        if(null == scaleAnimation){
            scaleAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_anim);
            tv_banner_home.setAnimation(scaleAnimation);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    tv_banner_home.setText(Util.getFormatTime(System.currentTimeMillis(),"yyyy-MM-DD HH:mm:ss"));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
        tv_banner_home.startAnimation(scaleAnimation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
