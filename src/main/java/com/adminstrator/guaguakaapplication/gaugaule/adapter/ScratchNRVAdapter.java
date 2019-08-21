package com.adminstrator.guaguakaapplication.gaugaule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.Util;

import java.util.List;

/**
 * Created by Administrator on 2019/8/16.
 */

public class ScratchNRVAdapter extends RecyclerView.Adapter<ScratchNRVAdapter.MyHolder> {
    private Context mContext;
    private List<String> datas;

    public ScratchNRVAdapter(Context mContext, List<String> datas ) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(parent.getContext(), R.layout.layout_item_scratch_n_rv_scratch,null));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2px(mContext,49));
        params.topMargin = Util.dp2px(mContext,8);
        holder.itemView.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_amount_scratch_n_rv,tv_name_scratch_n_rv,tv_number_scratch_n_rv;
        public MyHolder(View itemView) {
            super(itemView);
            tv_amount_scratch_n_rv = itemView.findViewById(R.id.tv_amount_scratch_n_rv);
            tv_name_scratch_n_rv = itemView.findViewById(R.id.tv_name_scratch_n_rv);
            tv_number_scratch_n_rv = itemView.findViewById(R.id.tv_number_scratch_n_rv);
        }
    }

}
