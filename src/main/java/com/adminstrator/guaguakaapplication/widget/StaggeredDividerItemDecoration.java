package com.adminstrator.guaguakaapplication.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private float interval;
    private int spanCount;

    /**
     * @param interval item的间距
     * @param spanCount 列数
     * */
    public StaggeredDividerItemDecoration(Context context, float interval, int spanCount) {
        this.context = context;
        this.interval = interval;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 获取item在span中的下标
        int spanIndex = params.getSpanIndex();
        int interval = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.interval, context.getResources().getDisplayMetrics());
        // 中间间隔

        /**
         * 这个判断适用于瀑布流只有两列的情况，如果有多列，那么再增加spanIndex % spanCount == 的判断并做处理就好了
         * 此处的left和right都为interval / 2的原因是为了让左边item和右边item同宽
         * */
        if (spanIndex % spanCount == 0) {
            outRect.right = interval / 2;
        } else {
            outRect.left = interval / 2;
        }

     /*   //目前这个设置只适用于只有两列的情况。设置左边一列的右间隔和右边一列的左间隔.出现数据刷新后有的item之间没有间隔的情况，所以把判断去掉了
        outRect.right = interval / 2;
        outRect.left = interval / 2;*/

        // 下方间隔
//        outRect.bottom = interval;
    }
}
