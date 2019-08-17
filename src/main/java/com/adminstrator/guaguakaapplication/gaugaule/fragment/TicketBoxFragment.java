package com.adminstrator.guaguakaapplication.gaugaule.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.gaugaule.adapter.TicketBoxRVAdapter_Scratch;
import com.adminstrator.guaguakaapplication.gaugaule.inter.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩票盒页面
 */
public class TicketBoxFragment extends Fragment implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private static final String ARG_PARAM1 = "username";
    private static final String ARG_PARAM2 = "amount";
    private String username;
    private String amount;
    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView iv_head_my_record;
    private TextView tv_username_my_record,tv_wallet_amount_my_record,tv_exit_my_record;
    private RecyclerView rv_ticket_box;
    private TicketBoxRVAdapter_Scratch ticketBoxRVAdapterScratch;

    public TicketBoxFragment() {
        // Required empty public constructor
    }

    public static TicketBoxFragment newInstance(String param1, String param2) {
        TicketBoxFragment fragment = new TicketBoxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            amount = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_box, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        iv_head_my_record = view.findViewById(R.id.iv_head_my_record);
        tv_username_my_record = view.findViewById(R.id.tv_username_my_record);
        tv_wallet_amount_my_record = view.findViewById(R.id.tv_wallet_amount_my_record);
        tv_exit_my_record = view.findViewById(R.id.tv_exit_my_record);
        rv_ticket_box = view.findViewById(R.id.rv_ticket_box);
        rv_ticket_box.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add(i + "");
        }
        ticketBoxRVAdapterScratch = new TicketBoxRVAdapter_Scratch(getContext(),datas);
        rv_ticket_box.setAdapter(ticketBoxRVAdapterScratch);
        ticketBoxRVAdapterScratch.setOnItemClickListener(new TicketBoxRVAdapter_Scratch.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),"click " + position,Toast.LENGTH_LONG).show();
            }
        });

        tv_username_my_record.setText(username);
        tv_wallet_amount_my_record.setText(amount);

        tv_exit_my_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_exit_my_record:
                mListener.onFragmentInteraction(TAG,0);
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
