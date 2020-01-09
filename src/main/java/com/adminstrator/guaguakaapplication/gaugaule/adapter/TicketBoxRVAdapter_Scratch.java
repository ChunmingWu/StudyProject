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

public class TicketBoxRVAdapter_Scratch extends RecyclerView.Adapter<TicketBoxRVAdapter_Scratch.MyHolder> {
    private Context mContext;
    private List<String> datas;

    public TicketBoxRVAdapter_Scratch(Context mContext, List<String> datas ) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(parent.getContext(), R.layout.layout_item_ticket_box_rv_scratch,null));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2px(mContext,85));
        params.topMargin = Util.dp2px(mContext,10);
        holder.itemView.setLayoutParams(params);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnItemClickListener){
                    mOnItemClickListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView iv_ticket_box_rv_scratch;
        TextView tv_goods_name_ticket_box_rv_scratch,tv_order_id_ticket_box_rv_scratch,tv_progress_ticket_box_rv_scratch,tv_time_ticket_box_rv_scratch;
        public MyHolder(View itemView) {
            super(itemView);
            iv_ticket_box_rv_scratch = itemView.findViewById(R.id.iv_ticket_box_rv_scratch);
            tv_goods_name_ticket_box_rv_scratch = itemView.findViewById(R.id.tv_goods_name_ticket_box_rv_scratch);
            tv_order_id_ticket_box_rv_scratch = itemView.findViewById(R.id.tv_order_id_ticket_box_rv_scratch);
            tv_progress_ticket_box_rv_scratch = itemView.findViewById(R.id.tv_progress_ticket_box_rv_scratch);
            tv_time_ticket_box_rv_scratch = itemView.findViewById(R.id.tv_time_ticket_box_rv_scratch);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
