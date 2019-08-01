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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.adminstrator.guaguakaapplication.R;

/**
 * Created by Administrator on 2019/7/26.
 * 刮刮卡的蒙层View
 */

public class GuaGuaKaLayer extends View {
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

    /**
     * 蒙层和橡皮擦
     * */
    private Bitmap mBackBitmap;
    private Bitmap moveBitmap;

    /**
     * 记录手势移动位置
     * */
    private int mLastX;
    private int mLastY;

    /**
     * 用于判断何时算刮开完成
     * */
    private boolean isComplete = false;

    /**
     * 用于判断是否显示橡皮擦
     * */
    private boolean isDownOrMove = false;

    private Resources resources;

    public GuaGuaKaLayer(Context context) {
        this(context, null);
    }

    public GuaGuaKaLayer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaLayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔宽度
        mOutterPaint.setStrokeWidth(20);

        //将Bitmap精确缩放到指定的大小
        Bitmap tempBackBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.guaguaceng));
        Bitmap tempMoveBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.cleaner));
        moveBitmap = Bitmap.createScaledBitmap(tempMoveBitmap,100,100,true);
        mBackBitmap= Bitmap.createScaledBitmap(tempBackBitmap, width, height, true);

        mCanvas.drawBitmap(mBackBitmap, 0, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null);
            if(isDownOrMove){
                //绘制橡皮擦
                canvas.drawBitmap(moveBitmap, mLastX - moveBitmap.getWidth()/2, mLastY - moveBitmap.getHeight()/2, null);
            }
        }
    }

    /**
     * 绘制线条
     * */
    private void drawPath() {
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
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
                    //被擦除
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

                if (percent > 70) {
                    isComplete = true;
                    postInvalidate();
                }
            }
        }

    };
}
