package com.adminstrator.guaguakaapplication.gaugaule.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.gaugaule.adapter.PayRVAdapter_Scratch;
import com.adminstrator.guaguakaapplication.gaugaule.inter.OnFragmentInteractionListener;
import com.adminstrator.guaguakaapplication.gaugaule.widget.GuaGuaKaView;
import com.adminstrator.guaguakaapplication.gaugaule.widget.Lost_ScratchResultDialog;
import com.adminstrator.guaguakaapplication.gaugaule.widget.PrizesDialog;
import com.adminstrator.guaguakaapplication.gaugaule.widget.TextDrawable;
import com.adminstrator.guaguakaapplication.gaugaule.widget.Win_ScratchResultDialog;
import com.adminstrator.guaguakaapplication.widget.GuaGuaKaLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 刮奖页面
 */
public class ScratchCardFragment extends Fragment implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();

    private static final String PARAM_AMOUNT = "amount";
    private static final String PARAM_USERNAME = "username";
    private String amount, username;

    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView iv_head_scratch_card;
    private TextView tv_username_scratch_card, tv_wallet_amount_scratch_card, tv_exit_scratch_card,tv_description_scratch_card,tv_claim_payout_scratch_card;
    private RelativeLayout rl_scratch_card;

    public ScratchCardFragment() {
    }

    public static ScratchCardFragment newInstance(String amount, String username) {
        ScratchCardFragment fragment = new ScratchCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_AMOUNT, amount);
        bundle.putString(PARAM_USERNAME, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            amount = (String) getArguments().get(PARAM_AMOUNT);
            username = (String) getArguments().get(PARAM_USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scratch_card, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        iv_head_scratch_card = view.findViewById(R.id.iv_head_scratch_card);
        tv_username_scratch_card = view.findViewById(R.id.tv_username_scratch_card);
        tv_wallet_amount_scratch_card = view.findViewById(R.id.tv_wallet_amount_scratch_card);
        tv_exit_scratch_card = view.findViewById(R.id.tv_exit_scratch_card);
        tv_description_scratch_card = view.findViewById(R.id.tv_description_scratch_card);
        tv_claim_payout_scratch_card = view.findViewById(R.id.tv_claim_payout_scratch_card);
        rl_scratch_card = view.findViewById(R.id.rl_scratch_card);

        tv_username_scratch_card.setText(username);
        tv_wallet_amount_scratch_card.setText(amount);

        tv_exit_scratch_card.setOnClickListener(this);
        tv_claim_payout_scratch_card.setOnClickListener(this);
        tv_description_scratch_card.setOnClickListener(this);
    }

    int i = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_scratch_card:
                mListener.onFragmentInteraction(TAG, 0);
                break;
            case R.id.tv_description_scratch_card:
                PrizesDialog prizesDialog = new PrizesDialog(getContext(),PrizesDialog.FLAG_DESCRIPTION);
                prizesDialog.show(getActivity().getSupportFragmentManager(),null);
                break;
            case R.id.tv_claim_payout_scratch_card:
                if(i == 0){
                    createScratchView(R.drawable.p3);
                }else if(i == 1){
                    rl_scratch_card.removeAllViews();
                    createScratchView(R.drawable.p6);
                }else if(i == 2){
                    rl_scratch_card.removeAllViews();
                    createScratchView(R.drawable.p5);
                }else if(i == 3){
                    rl_scratch_card.removeAllViews();
                    createScratchView(R.drawable.p2);
                }
                i++;

                if(0 == 0){
                    break;
                }
                if(i % 2 == 0){
                    Win_ScratchResultDialog win_scratchResultDialog = new Win_ScratchResultDialog(getContext());
                    win_scratchResultDialog.show(getActivity().getSupportFragmentManager(),null);
                }else{
                    Lost_ScratchResultDialog lost_scratchResultDialog = new Lost_ScratchResultDialog(getContext());
                    lost_scratchResultDialog.show(getActivity().getSupportFragmentManager(),null);
                }
                i++;
                break;
        }
    }

    private void createScratchView(int resId_GuaGuaKaView) {
        int size = 500;
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.p1);
        RelativeLayout.LayoutParams params_tv = new RelativeLayout.LayoutParams(size,size);
        params_tv.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(params_tv);
        rl_scratch_card.addView(textView);

        GuaGuaKaView guaGuaKaView = new GuaGuaKaView(getContext());
        guaGuaKaView.setLayer(getContext().getResources().getDrawable(resId_GuaGuaKaView),size,size);
        RelativeLayout.LayoutParams params_ggv = new RelativeLayout.LayoutParams(size,size);
        params_ggv.addRule(RelativeLayout.CENTER_IN_PARENT);
        guaGuaKaView.setLayoutParams(params_ggv);
        rl_scratch_card.addView(guaGuaKaView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
