package com.adminstrator.guaguakaapplication.gaugaule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2019/8/16.
 */

public class PayRVAdapter_Scratch extends RecyclerView.Adapter<PayRVAdapter_Scratch.MyHolder> {
    private Context mContext;
    private List<String> datas;
    private int flag;
    public static final int FLAG_PAY = 0,FLAG_WIN = 1;

    public PayRVAdapter_Scratch(Context mContext, List<String> datas,int flag) {
        this.mContext = mContext;
        this.datas = datas;
        this.flag = flag;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(parent.getContext(), R.layout.layout_item_pay_rv_scratch,null));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2px(mContext,85)));
        if(flag == FLAG_PAY){
            holder.tv_status_pay_rv_scratch.setVisibility(View.GONE);
            holder.tv_amount_pay_rv_scratch.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }else {
            holder.tv_status_pay_rv_scratch.setVisibility(View.VISIBLE);
            holder.tv_amount_pay_rv_scratch.setTextColor(mContext.getResources().getColor(R.color.colorRed_c65));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView iv_pay_rv_scratch;
        TextView tv_goods_name_pay_rv_scratch,tv_status_pay_rv_scratch,tv_order_id_pay_rv_scratch,tv_time_pay_rv_scratch,tv_amount_pay_rv_scratch;
        public MyHolder(View itemView) {
            super(itemView);
            iv_pay_rv_scratch = itemView.findViewById(R.id.iv_pay_rv_scratch);
            tv_goods_name_pay_rv_scratch = itemView.findViewById(R.id.tv_goods_name_pay_rv_scratch);
            tv_status_pay_rv_scratch = itemView.findViewById(R.id.tv_status_pay_rv_scratch);
            tv_order_id_pay_rv_scratch = itemView.findViewById(R.id.tv_order_id_pay_rv_scratch);
            tv_time_pay_rv_scratch = itemView.findViewById(R.id.tv_time_pay_rv_scratch);
            tv_amount_pay_rv_scratch = itemView.findViewById(R.id.tv_amount_pay_rv_scratch);
        }
    }
}
