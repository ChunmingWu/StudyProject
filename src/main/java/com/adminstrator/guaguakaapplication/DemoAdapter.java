package com.adminstrator.guaguakaapplication;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2019/7/31.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.BaseViewHolder> {
    private ArrayList<Integer> dataList = new ArrayList<>();
    private Random random = new Random();

    public void replaceAll(ArrayList<Integer> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addData(int position,ArrayList<Integer> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public DemoAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_water_fall, parent, false));
    }

    @Override
    public void onBindViewHolder(DemoAdapter.BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data,int position) {

        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private ImageView ivImage;

        public OneViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.iv_item_water_fall);
 /*           int width = ((Activity) ivImage.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = ivImage.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width/3;
//            params.height =  (int) (200 + Math.random() * 400) ;
            params.height = width/3 + (int)random.nextFloat() * width/3;
            ivImage.setLayoutParams(params);*/

        }

        @Override
        void setData(Object data,int position) {
            if (data != null) {
                int id = (int) data;
//                ivImage.setBackgroundResource(id);
                ivImage.setImageResource(id);
                if (position % 2 == 0) {
                    ivImage.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                } else {
                    ivImage.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                }
            }
        }
    }


}

