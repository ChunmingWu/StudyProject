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
public class Win_ScratchResultDialog extends DialogFragment {
    private Context mContext;

    @SuppressLint("ValidFragment")
    public Win_ScratchResultDialog(Context mContext) {
        this.mContext = mContext;
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
        View view = inflater.inflate(R.layout.layout_win_scratch_result_dialog,container);
        ImageView iv_cha_win_scratch_result_dialog = view.findViewById(R.id.iv_cha_lost_scratch_result_dialog);
        TextView tv_peize_level_win_scratch_result = view.findViewById(R.id.tv_peize_level_win_scratch_result);
        TextView tv_amount_win_scratch_result = view.findViewById(R.id.tv_amount_win_scratch_result);

        iv_cha_win_scratch_result_dialog.setOnClickListener(new View.OnClickListener() {
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
