package com.adminstrator.guaguakaapplication.gaugaule.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.Util;
import com.adminstrator.guaguakaapplication.gaugaule.adapter.PackageRVAdapter_Scratch_Prizes_Dialog;
import com.adminstrator.guaguakaapplication.gaugaule.bean.ScratchPrizesDialogPackageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/17.
 */

@SuppressLint("ValidFragment")
public class PrizesDialog extends DialogFragment {
    private Context mContext;
    private boolean isHasPackage = false;
    private RecyclerView ry_package_prizes_dialog;
    private PackageRVAdapter_Scratch_Prizes_Dialog adapter_scratch_prizes_dialog;
    private List<ScratchPrizesDialogPackageBean> datas;

    public static final int FLAG_BUY = 0,FLAG_DESCRIPTION = 1;
    private int flag;

    @SuppressLint("ValidFragment")
    public PrizesDialog(Context mContext,int flag) {
        this.mContext = mContext;
        this.flag = flag;
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
        View view = inflater.inflate(R.layout.layout_prizes_dialog, container);
        ImageView iv_cha_settings_dialog = view.findViewById(R.id.iv_cha_lost_scratch_result_dialog);
        LinearLayout ll_left_prizes_description = view.findViewById(R.id.ll_left_prizes_description);
        LinearLayout ll_right_prizes_description = view.findViewById(R.id.ll_right_prizes_description);
        TextView tv_rules_prizes_Dialog = view.findViewById(R.id.tv_rules_prizes_Dialog);
        ScrollView sv_prizes_dialog = view.findViewById(R.id.sv_prizes_dialog);
        ry_package_prizes_dialog = view.findViewById(R.id.ry_package_prizes_dialog);
        LinearLayout ll_amount_prizes_dialog = view.findViewById(R.id.ll_amount_prizes_dialog);
        TextView tv_buy_prizes_dialog = view.findViewById(R.id.tv_buy_prizes_dialog);
        RelativeLayout rl_old_price_buy_prizes_dialog = view.findViewById(R.id.rl_old_price_buy_prizes_dialog);
        TextView tv_old_price_buy_prizes_dialog = view.findViewById(R.id.tv_old_price_buy_prizes_dialog);

        if(flag == FLAG_BUY){
            ry_package_prizes_dialog.setVisibility(View.VISIBLE);
            ll_amount_prizes_dialog.setVisibility(View.VISIBLE);
        }else if(flag == FLAG_DESCRIPTION){
            ry_package_prizes_dialog.setVisibility(View.GONE);
            ll_amount_prizes_dialog.setVisibility(View.GONE);
        }

        tv_rules_prizes_Dialog.setText("1.分为奖池区与游戏区两部分\n" +
                "2.游戏区的刮开不会影响奖池区中奖兑奖\n" +
                "3.奖池区:刮出数字即可兑换相应金额奖励\n" +
                "4.游戏区(Gaming Zone) :\n" +
                "*每行均包含两个刮开区，正确数字随机出现在其中一个刮开区域\n" +
                "*从奖金最少的底层刮起，每行只能选择一个刮层刮开，对比左侧标准金额数据，若刮开正确金额，即可领取该层奖励或挑战\"\n");
        for (int i = 1; i <= 6; i++) {
            ll_left_prizes_description.addView(getPrizeView(i, "88000000"));
        }
        for (int i = 7; i <= 12; i++) {
            ll_right_prizes_description.addView(getPrizeView(i, "88000000"));
        }

        //如果有买N增M的活动，则设置固定高度
        if (isHasPackage) {
            sv_prizes_dialog.getLayoutParams().height = Util.dp2px(mContext, 260);
            GridLayoutManager manager = new GridLayoutManager(mContext,2);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            ry_package_prizes_dialog.setLayoutManager(manager);
            datas = new ArrayList<>();
            datas.add(new ScratchPrizesDialogPackageBean("6","1","1000",true));
            datas.add(new ScratchPrizesDialogPackageBean("6","1","24000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            datas.add(new ScratchPrizesDialogPackageBean("10","2","40000",false));
            adapter_scratch_prizes_dialog = new PackageRVAdapter_Scratch_Prizes_Dialog(mContext,datas);
            ry_package_prizes_dialog.setAdapter(adapter_scratch_prizes_dialog);
            adapter_scratch_prizes_dialog.setOnItemClickListener(new PackageRVAdapter_Scratch_Prizes_Dialog.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    refreshAdapter(position);
                }
            });
        }else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ry_package_prizes_dialog.getLayoutParams();
            params.height = Util.dp2px(mContext,5);
        }

        iv_cha_settings_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ll_amount_prizes_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private void refreshAdapter(int position) {
        for(int i = 0 ; i < datas.size() ; i ++){
            datas.get(i).setSelected(false);
        }
        datas.get(position).setSelected(true);
        adapter_scratch_prizes_dialog.notifyDataSetChanged();
    }

    private LinearLayout getPrizeView(int prizeLevel, String prize) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        TextView tv1 = new TextView(mContext);
        tv1.setWidth(Util.dp2px(mContext, 35));
        tv1.setHeight(Util.dp2px(mContext, 22));
        if (prizeLevel == 1) {
            tv1.setBackgroundResource(R.drawable.scratch_prize1);
        } else if (prizeLevel == 2) {
            tv1.setBackgroundResource(R.drawable.scratch_prize2);
        } else if (prizeLevel == 3) {
            tv1.setBackgroundResource(R.drawable.scratch_prize3);
        } else {
            tv1.setText(prizeLevel + "");
            tv1.setTextColor(mContext.getResources().getColor(R.color.colorBlack_33));
            tv1.setTextSize(12);
            tv1.setGravity(Gravity.CENTER);
        }
        linearLayout.addView(tv1);

        TextView tv2 = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Util.dp2px(mContext, 85), Util.dp2px(mContext, 22));
        tv2.setLayoutParams(params);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(mContext.getResources().getColor(R.color.colorBlack_33));
        tv2.setTextSize(12);
        tv2.setText(Util.qianweifenge(prize));
        linearLayout.addView(tv2);
        return linearLayout;
    }

    int i = 100;
    private RelativeLayout getPackageView(String number, String freeNumber, String amount) {
        RelativeLayout linearLayout = new RelativeLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Util.dp2px(mContext,110),Util.dp2px(mContext,28));
        params.topMargin = Util.dp2px(mContext,6);
        linearLayout.setLayoutParams(params);
        linearLayout.setBackgroundResource(R.drawable.laylist_package_scratch_dialog);
        linearLayout.setId(i);
        i++;

        TextView tv1 = new TextView(mContext);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(Util.dp2px(mContext,110),Util.dp2px(mContext,14));
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tv1.setLayoutParams(layoutParams1);
        tv1.setText("buy " + number + " free " + freeNumber);
        tv1.setTextColor(mContext.getResources().getColor(R.color.colorBlack_33));
        tv1.setTextSize(10);
        tv1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        linearLayout.addView(tv1);

        TextView tv2 = new TextView(mContext);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(Util.dp2px(mContext,110),Util.dp2px(mContext,14));
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv2.setLayoutParams(layoutParams2);
        tv2.setText("KHR " + Util.qianweifenge(amount));
        tv2.setTextColor(mContext.getResources().getColor(R.color.colorBlack_33));
        tv2.setTextSize(10);
        tv2.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        linearLayout.addView(tv2);

        TextView tv3 = new TextView(mContext);
        tv3.setBackgroundResource(R.drawable.icon_cha);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Util.dp2px(mContext,11),Util.dp2px(mContext,11));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.rightMargin = Util.dp2px(mContext,3);
        tv3.setLayoutParams(layoutParams);
        linearLayout.addView(tv3);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return linearLayout;
    }

    public interface OnCheckListener {
        void onCheck(int flag, boolean checked);
    }

    private OnCheckListener mOnCheckListener;

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        mOnCheckListener = onCheckListener;
    }

}
