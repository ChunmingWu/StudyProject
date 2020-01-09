package com.adminstrator.guaguakaapplication.gaugaule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.util.Util;
import com.adminstrator.guaguakaapplication.gaugaule.bean.ScratchPrizesDialogPackageBean;

import java.util.List;

/**
 * Created by Administrator on 2019/8/16.
 */

public class PackageRVAdapter_Scratch_Prizes_Dialog extends RecyclerView.Adapter<PackageRVAdapter_Scratch_Prizes_Dialog.MyHolder> {
    private Context mContext;
    private List<ScratchPrizesDialogPackageBean> datas;

    public PackageRVAdapter_Scratch_Prizes_Dialog(Context mContext, List<ScratchPrizesDialogPackageBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(parent.getContext(), R.layout.layout_item_package_rv_scratch_prizes_dialog,null));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(Util.dp2px(mContext,110), Util.dp2px(mContext,28));
        params.topMargin = Util.dp2px(mContext,6);
        params.leftMargin = Util.dp2px(mContext,3);
        params.rightMargin = Util.dp2px(mContext,3);
        holder.itemView.setLayoutParams(params);
        ScratchPrizesDialogPackageBean bean = datas.get(position);
        holder.tv_number_rv_prizes_dialog.setText("buy " + bean.getBuyNumber() + " free " + bean.getFreeNumber());
        holder.tv_amount_rv_prizes_dialog.setText("$ " + Util.qianweifenge(bean.getAmount()));
        if(bean.isSelected()){
            holder.rl_package_scratch_dialog.setBackgroundResource(R.drawable.bg_selected_package_scratch_dialog);
            holder.tv_number_rv_prizes_dialog.setTextColor(mContext.getResources().getColor(R.color.colorRed_900));
            holder.tv_amount_rv_prizes_dialog.setTextColor(mContext.getResources().getColor(R.color.colorRed_900));
        }else{
            holder.rl_package_scratch_dialog.setBackgroundResource(R.drawable.bg_normal_package_scratch_dialog);
            holder.tv_number_rv_prizes_dialog.setTextColor(mContext.getResources().getColor(R.color.colorBlack_33));
            holder.tv_amount_rv_prizes_dialog.setTextColor(mContext.getResources().getColor(R.color.colorBlack_33));
        }
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
        RelativeLayout rl_package_scratch_dialog;
        TextView tv_number_rv_prizes_dialog,tv_amount_rv_prizes_dialog;
        public MyHolder(View itemView) {
            super(itemView);
            tv_number_rv_prizes_dialog = itemView.findViewById(R.id.tv_number_rv_prizes_dialog);
            tv_amount_rv_prizes_dialog = itemView.findViewById(R.id.tv_amount_rv_prizes_dialog);
            rl_package_scratch_dialog = itemView.findViewById(R.id.rl_package_scratch_dialog);
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
