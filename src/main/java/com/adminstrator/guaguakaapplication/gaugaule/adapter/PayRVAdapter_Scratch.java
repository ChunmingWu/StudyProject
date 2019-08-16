package com.adminstrator.guaguakaapplication.gaugaule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.Util;

import java.util.List;

/**
 * Created by Administrator on 2019/8/16.
 */

public class PayRVAdapter_Scratch extends RecyclerView.Adapter<PayRVAdapter_Scratch.MyHolder> {
    private Context mContext;
    private List<String> datas;

    public PayRVAdapter_Scratch(Context mContext, List<String> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(parent.getContext(), R.layout.layout_item_pay_rv_scratch,null));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2px(mContext,85)));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
