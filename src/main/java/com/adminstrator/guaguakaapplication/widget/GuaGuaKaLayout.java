package com.adminstrator.guaguakaapplication.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.adminstrator.guaguakaapplication.R;

/**
 * Created by Administrator on 2019/7/26.
 * 刮刮卡的擦层
 * 使用方法，使用FrameLayout，把底层真实内容的图片或者布局和蒙层放在一起，使用PorterDuff.Mode.CLEAR擦除蒙层
 * 优点：底层布局随意写
 */

public class GuaGuaKaLayout extends View {
    /**
     * 绘制线条的Paint,即用户手指绘制Path
     */
    private Paint mOutterPaint = new Paint();

    /**
     * 记录用户绘制的Path
     */
    private Path mPath = new Path();

    /**
     * 内存中创建的Canvas
     */
    private Canvas mCanvas;

    /**
     * mCanvas绘制内容在其上
     */
    private Bitmap mBitmap;

    private Bitmap mBackBitmap;
    private Bitmap moveBitmap;

    private int mLastX;
    private int mLastY;

    private boolean isComplete = false;
    private boolean isDownOrMove = false;

    private Resources resources;

    public GuaGuaKaLayout(Context context) {
        this(context, null);
    }

    public GuaGuaKaLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resources = context.getResources();
        init();
    }


    private void init() {
        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //初始化bitmap
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        //设置画笔
        mOutterPaint.setColor(Color.RED);
        mOutterPaint.setAntiAlias(true);
        mOutterPaint.setDither(true);
        mOutterPaint.setStyle(Paint.Style.STROKE);
        //圆角
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔宽度
        mOutterPaint.setStrokeWidth(20);

        mBackBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.guaguaceng));
        Bitmap tempMoveBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.cleaner));
        moveBitmap = Bitmap.createScaledBitmap(tempMoveBitmap,100,100,true);

        //将Bitmap精确缩放到指定的大小
        Bitmap finalBitmap = Bitmap.createScaledBitmap(mBackBitmap, width, height, true);
        mCanvas.drawBitmap(finalBitmap, 0, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null);
            if(isDownOrMove){
                canvas.drawBitmap(moveBitmap, mLastX - moveBitmap.getWidth()/2, mLastY - moveBitmap.getHeight()/2, null);
            }
        }
    }

    //绘制线条
    private void drawPath() {
//        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mOutterPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);

                isDownOrMove = true;
                break;

            case MotionEvent.ACTION_MOVE:

                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx > 3 || dy > 3) {
                    mPath.lineTo(x, y);
                }
                mLastX = x;
                mLastY = y;

                isDownOrMove = true;
                break;
            case MotionEvent.ACTION_UP:
                isDownOrMove = false;
                new Thread(mRunnable).start();
                break;
        }
        invalidate();
        return true;
    }


    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {

            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;

            Bitmap bitmap = mBitmap;

            mPixels = new int[w * h];

            /**
             * 拿到所有的像素信息
             */
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            /**
             * 遍历统计擦除的区域
             */
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，进行一些操作
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.e("TAG", percent + "");

                if (percent > 70) {
                    isComplete = true;
                    postInvalidate();
                }
            }
        }

    };
}
