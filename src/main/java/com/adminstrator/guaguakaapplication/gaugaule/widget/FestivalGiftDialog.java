package com.adminstrator.guaguakaapplication.gaugaule.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;

/**
 * Created by Administrator on 2019/8/17.
 */

@SuppressLint("ValidFragment")
public class FestivalGiftDialog extends DialogFragment {
    private Context mContext;
    private int position;//透传参数

    @SuppressLint("ValidFragment")
    public FestivalGiftDialog(Context mContext,int position) {
        this.mContext = mContext;
        this.position = position;
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
        View view = inflater.inflate(R.layout.layout_festival_gift_dialog,container);
        TextView tv_title_festival_gift_dialog = view.findViewById(R.id.tv_title_festival_gift_dialog);
        TextView tv_content_festival_gift_dialog = view.findViewById(R.id.tv_content_festival_gift_dialog);
        TextView tv_buy_festival_gift_dialog = view.findViewById(R.id.tv_buy_festival_gift_dialog);
        TextView tv_old_price_buy_festival_gift_dialog = view.findViewById(R.id.tv_old_price_buy_festival_gift_dialog);
        RelativeLayout rl_old_price_buy_festival_gift_dialog = view.findViewById(R.id.rl_old_price_buy_festival_gift_dialog);
        LinearLayout ll_amount_festival_gift_dialog = view.findViewById(R.id.ll_amount_festival_gift_dialog);
        ImageView iv_cha_festival_gift_dialog = view.findViewById(R.id.iv_cha_lost_scratch_result_dialog);

        tv_content_festival_gift_dialog.setText("新春大礼包包含6张刮刮乐商品。\n" +
                "\n" +
                "Dare Or Not (2张) $4,000*2、Add Friend (2张) $ 4,000*2、Dream (2张) $ 4,000*2。\"\n");
        iv_cha_festival_gift_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ll_amount_festival_gift_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnBuyListener){
                    mOnBuyListener.onClick(position);
                }
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
