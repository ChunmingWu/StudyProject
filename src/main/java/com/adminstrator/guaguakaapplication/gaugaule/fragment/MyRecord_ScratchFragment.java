package com.adminstrator.guaguakaapplication.gaugaule.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.gaugaule.adapter.PayRVAdapter_Scratch;
import com.adminstrator.guaguakaapplication.gaugaule.inter.OnFragmentInteractionListener;
import com.adminstrator.guaguakaapplication.gaugaule.widget.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * My record
 */
public class MyRecord_ScratchFragment extends Fragment implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();

    private static final String PARAM_AMOUNT = "amount";
    private static final String PARAM_USERNAME = "username";
    private String amount, username;

    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView iv_head_my_record;
    private TextView tv_username_my_record, tv_wallet_amount_my_record, tv_exit_my_record;
    private TextDrawable tv_pay_my_record, tv_win_my_record;
    private RecyclerView rv_pay_my_record, rv_win_my_record;
    private PayRVAdapter_Scratch payRVAdapter_scratch,winRVAdapter_scratch;

    public MyRecord_ScratchFragment() {
    }

    public static MyRecord_ScratchFragment newInstance(String amount, String username) {
        MyRecord_ScratchFragment fragment = new MyRecord_ScratchFragment();
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
        iv_head_my_record = view.findViewById(R.id.iv_head_my_record);
        tv_username_my_record = view.findViewById(R.id.tv_username_my_record);
        tv_wallet_amount_my_record = view.findViewById(R.id.tv_wallet_amount_my_record);
        tv_exit_my_record = view.findViewById(R.id.tv_exit_my_record);
        tv_pay_my_record = view.findViewById(R.id.tv_pay_my_record);
        tv_win_my_record = view.findViewById(R.id.tv_win_my_record);
        tv_win_my_record.setDrawableBottom(null);
        rv_pay_my_record = view.findViewById(R.id.rv_pay_my_record);
        rv_win_my_record = view.findViewById(R.id.rv_win_my_record);
        rv_pay_my_record.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_win_my_record.setLayoutManager(new LinearLayoutManager(getContext()));

        tv_username_my_record.setText(username);
        tv_wallet_amount_my_record.setText(amount);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add(i + "");
        }
        payRVAdapter_scratch = new PayRVAdapter_Scratch(getContext(), datas,PayRVAdapter_Scratch.FLAG_PAY);
        rv_pay_my_record.setAdapter(payRVAdapter_scratch);

        winRVAdapter_scratch = new PayRVAdapter_Scratch(getContext(),datas,PayRVAdapter_Scratch.FLAG_WIN);
        rv_win_my_record.setAdapter(winRVAdapter_scratch);

        tv_exit_my_record.setOnClickListener(this);
        tv_pay_my_record.setOnClickListener(this);
        tv_win_my_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_my_record:
                mListener.onFragmentInteraction(TAG, 0);
                break;
            case R.id.tv_pay_my_record:
                tv_pay_my_record.setDrawableBottom(getResources().getDrawable(R.drawable.shape_red_bar_scratch));
                tv_win_my_record.setDrawableBottom(null);
                rv_pay_my_record.setVisibility(View.VISIBLE);
                rv_win_my_record.setVisibility(View.GONE);
                break;
            case R.id.tv_win_my_record:
                tv_win_my_record.setDrawableBottom(getResources().getDrawable(R.drawable.shape_red_bar_scratch));
                tv_pay_my_record.setDrawableBottom(null);
                rv_pay_my_record.setVisibility(View.GONE);
                rv_win_my_record.setVisibility(View.VISIBLE);
                break;
        }
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
