package com.adminstrator.guaguakaapplication.widget.refreshView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by Administrator on 2019/7/25.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    // 用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    // 最后几个完全可见项的位置（瀑布式布局会出现这种情况）
    private int[] lastCompletelyVisiblePositions;
    // 最后一个完全可见项的位置
    private int lastCompletelyVisibleItemPosition;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // 通过比对 最后完全可见项位置 和 总条目数，来判断是否滑动到底部
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                onScrollToBottom();
            }
        }
    }

    public void onScrollToBottom() {
        onLoadMore();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // 找到最后一个完全可见项的位置
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            if (lastCompletelyVisiblePositions == null) {
                lastCompletelyVisiblePositions = new int[manager.getSpanCount()];
            }
            manager.findLastCompletelyVisibleItemPositions(lastCompletelyVisiblePositions);
            lastCompletelyVisibleItemPosition = getMaxPosition(lastCompletelyVisiblePositions);
        } else if (layoutManager instanceof GridLayoutManager) {
            lastCompletelyVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        } else {
            throw new RuntimeException("Unsupported LayoutManager.");
        }
    }

    private int getMaxPosition(int[] positions) {
        int max = positions[0];
        for (int i = 1; i < positions.length; i++) {
            if (positions[i] > max) {
                max = positions[i];
            }
        }
        return max;
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}
