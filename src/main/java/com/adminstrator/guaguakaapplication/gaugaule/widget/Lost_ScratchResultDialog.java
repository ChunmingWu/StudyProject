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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;

/**
 * Created by Administrator on 2019/8/17.
 */

@SuppressLint("ValidFragment")
public class Lost_ScratchResultDialog extends DialogFragment {
    private Context mContext;

    @SuppressLint("ValidFragment")
    public Lost_ScratchResultDialog(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.bigDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.layout_lost_scratch_result_dialog,container);
        ImageView iv_cha_lost_scratch_result_dialog = view.findViewById(R.id.iv_cha_lost_scratch_result_dialog);
        TextView tv_buy_one_lost_scratch_result_dialog = view.findViewById(R.id.tv_buy_one_lost_scratch_result_dialog);

        iv_cha_lost_scratch_result_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_buy_one_lost_scratch_result_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
