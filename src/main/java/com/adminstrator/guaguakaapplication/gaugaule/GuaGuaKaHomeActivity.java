package com.adminstrator.guaguakaapplication.gaugaule;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.Util;
import com.adminstrator.guaguakaapplication.gaugaule.fragment.HomeFragment;
import com.adminstrator.guaguakaapplication.gaugaule.inter.OnFragmentInteractionListener;
import com.gyf.immersionbar.ImmersionBar;

public class GuaGuaKaHomeActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private LinearLayout root_home;
    private HomeFragment homeFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public static final int ACTION_BACK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guaguaka_home);
        ImmersionBar.with(this).statusBarColor(R.color.colorRed_scratch).statusBarDarkFont(false).init();
        root_home = findViewById(R.id.root_home);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            root_home.setPadding(0, Util.getStatusBarHeight(this), 0, 0);
        }

        if(null == savedInstanceState){
            homeFragment = new HomeFragment();
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_scratch_home,homeFragment);
            transaction.commit();
        }

    }


    @Override
    public void onFragmentInteraction(String from, int actionFlag) {
        if(actionFlag == ACTION_BACK){
            onBackPressed();
        }
    }
}
