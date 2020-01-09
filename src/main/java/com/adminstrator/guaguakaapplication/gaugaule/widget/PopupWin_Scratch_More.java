package com.adminstrator.guaguakaapplication.gaugaule.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.util.Util;

/**
 * Created by Administrator on 2019/8/16.
 */

public class PopupWin_Scratch_More {
    private Context mContext;
    private View dropdownView;
    private PopupWindow morePopup;

    public PopupWin_Scratch_More(Context mContext, View dropdownView) {
        this.mContext = mContext;
        this.dropdownView = dropdownView;
        init();
    }

    private void init() {
        if (null != morePopup && morePopup.isShowing()) {
            morePopup = null;
            return;
        }
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_popup_scratch_more, null);
        morePopup = new PopupWindow(contentView, Util.dp2px(mContext, 82), Util.dp2px(mContext, 91));
        morePopup.setTouchable(true);
        morePopup.setOutsideTouchable(true);
        morePopup.showAsDropDown(dropdownView, Util.getScreenWidth(mContext) - Util.dp2px(mContext, 82) - 3, 3, Gravity.LEFT);

        TextView tv_my_record_more = contentView.findViewById(R.id.tv_my_record_more);
        TextView tv_ticket_box_more = contentView.findViewById(R.id.tv_ticket_box_more);
        TextView tv_settings_more = contentView.findViewById(R.id.tv_settings_more);

        tv_my_record_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnItemClickLitener){
                    mOnItemClickLitener.onItemClick(0,null);
                }
                morePopup.dismiss();
            }
        });

        tv_ticket_box_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnItemClickLitener){
                    mOnItemClickLitener.onItemClick(1,null);
                }
                morePopup.dismiss();
            }
        });

        tv_settings_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnItemClickLitener){
                    mOnItemClickLitener.onItemClick(2,null);
                }
                morePopup.dismiss();
            }
        });


        morePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                morePopup = null;
            }
        });
    }


    public interface OnItemClickLitener {
        void onItemClick(int flag, String message);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener itemClickLitener) {
        mOnItemClickLitener = itemClickLitener;
    }


}
