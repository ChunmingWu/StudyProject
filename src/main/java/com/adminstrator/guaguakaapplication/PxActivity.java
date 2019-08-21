package com.adminstrator.guaguakaapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 使用Px设置宽高，并做缩放
 * */
public class PxActivity extends AppCompatActivity {
    private EditText edt_width, edt_height, edt_left, edt_top;
    private Button btn_confirm;
    private FrameLayout fl_scratch;
    private RelativeLayout rl_px;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
        edt_width = findViewById(R.id.edt_width);
        edt_height = findViewById(R.id.edt_height);
        edt_left = findViewById(R.id.edt_left);
        edt_top = findViewById(R.id.edt_top);
        btn_confirm = findViewById(R.id.btn_confirm);
        fl_scratch = findViewById(R.id.fl_scratch);
        rl_px = findViewById(R.id.rl_px);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_px.removeAllViews();
                paintScratch();
//                scaleScratch();
            }
        });
    }

    private void scaleScratch() {
        String widthStr = edt_width.getText().toString() + "";
        String heightStr = edt_height.getText().toString() + "";
        String leftStr = edt_left.getText().toString() + "";
        String topStr = edt_top.getText().toString() + "";
        int width = Integer.parseInt(widthStr), height = Integer.parseInt(heightStr);
        int left = Integer.parseInt(leftStr), top = Integer.parseInt(topStr);

        String scaleStr = getFixLayoutParams(width, height);
        float scale_width = Float.parseFloat(scaleStr.split("HORIZON")[0]);
        float scale_height = Float.parseFloat(scaleStr.split("HORIZON")[1]);

        TextView tv1 = new TextView(this);
        tv1.setBackgroundResource(R.color.colorAccent);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(200), (int)(200));
        params.leftMargin = (int)(left);
        params.topMargin = (int)(top);
        rl_px.addView(tv1, params);


        LinearLayout.LayoutParams params_fl = new LinearLayout.LayoutParams((int) (width), (int) (height));
        fl_scratch.setLayoutParams(params_fl);
        scaleWindow(fl_scratch,scale_width,scale_height);

        FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) rl_px.getLayoutParams();
        Log.e("老铁","--11--->width:" + params1.width + "   height:" + params1.height);

    }

    //就用这个方式了，本来就得一个个布局控件，现在只不过是在布局的时候乘上一个缩放值
    private void paintScratch() {
        String widthStr = edt_width.getText().toString() + "";
        String heightStr = edt_height.getText().toString() + "";
        String leftStr = edt_left.getText().toString() + "";
        String topStr = edt_top.getText().toString() + "";
        int width = Integer.parseInt(widthStr), height = Integer.parseInt(heightStr);
        int left = Integer.parseInt(leftStr), top = Integer.parseInt(topStr);

        String scaleStr = getFixLayoutParams(width, height);
        float scale_width = Float.parseFloat(scaleStr.split("HORIZON")[0]);
        float scale_height = Float.parseFloat(scaleStr.split("HORIZON")[1]);

        TextView tv1 = new TextView(this);
        tv1.setBackgroundResource(R.color.colorAccent);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(200 * scale_width), (int)(200 * scale_height));
        params.leftMargin = (int)(left * scale_width);
        params.topMargin = (int)(top * scale_height);
        rl_px.addView(tv1, params);


        LinearLayout.LayoutParams params_fl = new LinearLayout.LayoutParams((int) (width * scale_width), (int) (height * scale_height));
        fl_scratch.setLayoutParams(params_fl);

        //最后让布局居中
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(fl_scratch.getLayoutParams());
        params1.gravity = Gravity.CENTER_HORIZONTAL;
        fl_scratch.setLayoutParams(params1);
    }

    /**
     * 获取宽高的缩放值
     * <p>
     * 高度最高为应用显示区域的百分之七十
     */
    private String getFixLayoutParams(float width, float height) {
        float sWidth = getScreenWidth(), sHeight = (float) (getAvilableHeight() * 0.7);
        float finalWidth = 0, finalHeight = 0;

        float tempWidth = width, tempHeight = height;
        float finalMutipleWidth = 0, finalMutipleHeight = 0;

        //先对宽度做处理
        if (width > sWidth) {
            finalWidth = sWidth;
            //将高度等比缩放
            float mutiple = sWidth / width;
            height = (height * mutiple);
        } else {
            finalWidth = width;
        }

        //对高度做处理
        if (height > sHeight) {
            finalHeight = sHeight;
            float multiple = sHeight / height;
            finalWidth = (finalWidth * multiple);
        } else {
            finalHeight = height;
        }

        finalMutipleWidth = finalWidth / tempWidth;
        finalMutipleHeight = finalHeight / tempHeight;
        return finalMutipleWidth + "HORIZON" + finalMutipleHeight;
    }

    private int getScreenWidth() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        return widthPixels;
    }

    //获取应用程序的显示区域高度
    private int getAvilableHeight() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        return heightPixels;
    }



    //使用缩放动画遇到的问题，无法使缩放后的布局内容居中
    private void scaleWindow(View content,float xScale,float yScale) {
        //属性动画，如果缩放前的宽度大于屏幕宽度，那么缩放后的布局将是不居中的。而如果先把宽度调整为屏幕宽度，那么其他所有的布局或者空间的额宽高也要做调整，这样的话还不如不用
//        动画，直接计算和设置布局和控件的大小及位置
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(content, "scaleX", 1f, xScale,xScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(content, "scaleY", 1f, yScale,yScale);

        animatorSetsuofang.setDuration(2000);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();

        if(0 == 0){
            return;
        }

        /** 设置缩放动画 缩放后的布局不居中*/
        final ScaleAnimation animation_1 = new ScaleAnimation(1f, xScale, 1f, yScale,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);// 从相对于自身0.5倍的位置开始缩放，也就是从控件的位置缩放

        final  ScaleAnimation animation = new ScaleAnimation(1f, xScale, 1f, yScale,Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF,0);
        animation.setDuration(2000);//设置动画持续时间

        /** 常用方法 */
        //animation.setRepeatCount(int repeatCount);//设置重复次数
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        //animation.setStartOffset(long startOffset);//执行前的等待时间

        content.setAnimation(animation);
        /** 开始动画 */
        animation.startNow();
    }

}
