package com.adminstrator.guaguakaapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.gaugaule.activity.GuaGuaKaHomeActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_scratch_demo,tv_water_fall_demo,tv_file_demo,tv_px_demo,tv_my_project_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        tv_scratch_demo = findViewById(R.id.tv_scratch_demo);
        tv_water_fall_demo = findViewById(R.id.tv_water_fall_demo);
        tv_file_demo = findViewById(R.id.tv_file_demo);
        tv_px_demo = findViewById(R.id.tv_px_demo);
        tv_my_project_demo = findViewById(R.id.tv_my_project_demo);

        tv_scratch_demo.setOnClickListener(this);
        tv_water_fall_demo.setOnClickListener(this);
        tv_file_demo.setOnClickListener(this);
        tv_px_demo.setOnClickListener(this);
        tv_my_project_demo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_scratch_demo:
                goToActivity(ScratchActivity.class);
                break;
            case R.id.tv_water_fall_demo:
                goToActivity(WaterFallActivity.class);
                break;
            case R.id.tv_file_demo:
                goToActivity(LoadAndUnzipFileActivity.class);
                break;
            case R.id.tv_px_demo:
                goToActivity(PxActivity.class);
                break;
            case R.id.tv_my_project_demo:
                goToActivity(GuaGuaKaHomeActivity.class);
                break;
        }
    }

    private void goToActivity(Class<? extends Activity> cls){
        Intent intent  = new Intent(this,cls);
        startActivity(intent);
    }
}
