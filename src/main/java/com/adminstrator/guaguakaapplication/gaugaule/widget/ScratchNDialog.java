package com.adminstrator.guaguakaapplication.gaugaule.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.gaugaule.adapter.ScratchNRVAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/17.
 */

@SuppressLint("ValidFragment")
public class ScratchNDialog extends DialogFragment {
    private Context mContext;
    private int position;//透传参数

    @SuppressLint("ValidFragment")
    public ScratchNDialog(Context mContext, int position) {
        this.mContext = mContext;
        this.position = position;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                int dialogHeight = getContextRect(getActivity());
                //设置弹窗大小为会屏
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
                //去除阴影
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.dimAmount = 0.6f;
                window.setAttributes(layoutParams);
            }
        }
    }

    //获取内容区域
    private int getContextRect(Activity activity){
        //应用区域
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        return outRect1.height();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.bigDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_scratch_n_dialog,container);
        RecyclerView rv_scratch_n_dialog = view.findViewById(R.id.rv_scratch_n_dialog);
        ImageView iv_cha_scratch_n_dialog = view.findViewById(R.id.iv_cha_scratch_n_dialog);
        TextView tv_title_scratch_n_dialog = view.findViewById(R.id.tv_title_scratch_n_dialog);
        TextView tv_description_scratch_n_dialog = view.findViewById(R.id.tv_description_scratch_n_dialog);
        LinearLayout ll_amount_scratch_n_dialog = view.findViewById(R.id.ll_amount_scratch_n_dialog);
        TextView tv_buy_festival_gift_dialog = view.findViewById(R.id.tv_buy_festival_gift_dialog);
        RelativeLayout rl_old_price_buy_scratch_n_dialog = view.findViewById(R.id.rl_old_price_buy_scratch_n_dialog);
        TextView tv_old_price_buy_scratch_n_dialog = view.findViewById(R.id.tv_old_price_buy_scratch_n_dialog);

        rv_scratch_n_dialog.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> datas = new ArrayList<>();
        for(int i = 0 ; i < 10;i++){
            datas.add("");
        }
        ScratchNRVAdapter scratchNRVAdapter = new ScratchNRVAdapter(mContext,datas);
        rv_scratch_n_dialog.setAdapter(scratchNRVAdapter);

        iv_cha_scratch_n_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ll_amount_scratch_n_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnBuyListener){
                    mOnBuyListener.onClick(position);
                    dismiss();
                }
            }
        });

        return view;
    }

    public interface OnBuyClickListener {
        void onClick(int position);
    }

    private OnBuyClickListener mOnBuyListener;

    public void setOnBuyClickListener(OnBuyClickListener onBuyListener ){
        mOnBuyListener = onBuyListener;
    }

}
