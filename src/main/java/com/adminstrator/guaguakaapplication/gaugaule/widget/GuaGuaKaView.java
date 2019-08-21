package com.adminstrator.guaguakaapplication.gaugaule.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.adminstrator.guaguakaapplication.R;
import com.adminstrator.guaguakaapplication.Util;

/**
 * Created by Administrator on 2019/7/26.
 * 刮刮卡的蒙层View
 */

public class GuaGuaKaView extends View {
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
     */
    private Bitmap mBackBitmap;
    private Bitmap moveBitmap;

    /**
     * 记录手势移动位置
     */
    private int mLastX;
    private int mLastY;

    /**
     * 用于判断何时算刮开完成
     */
    private boolean isComplete = false;

    /**
     * 用于判断是否显示橡皮擦
     */
    private boolean isDownOrMove = false;

    private Resources resources;

    private int layerWidth, layerHeight;
    private int coinWidth, coinHeight;
    private Drawable layerDrawable, coinDrawable;

    public GuaGuaKaView(Context context) {
        this(context, null);
    }

    public GuaGuaKaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resources = context.getResources();
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mPath = new Path();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GuaGuaKaView);
        layerDrawable = typedArray.getDrawable(R.styleable.GuaGuaKaView_layerDrawable);

        coinWidth = typedArray.getDimensionPixelOffset(R.styleable.GuaGuaKaView_coinWidth, Util.dp2px(context, 148));
        coinHeight = typedArray.getDimensionPixelOffset(R.styleable.GuaGuaKaView_coinHeight, Util.dp2px(context, 129));
        coinDrawable = typedArray.getDrawable(R.styleable.GuaGuaKaView_coinDrawable);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width != 0) {
            layerWidth = getMeasuredWidth();
        }
        if (height != 0) {
            layerHeight = getMeasuredHeight();
        }


        //初始化bitmap
        mBitmap = Bitmap.createBitmap(layerWidth, layerHeight, Bitmap.Config.ARGB_8888);
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

        if(null == coinDrawable){
            moveBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.cleaner)),coinWidth,coinHeight,true);
        }else{
            moveBitmap = drawableToBitmap(coinDrawable, coinWidth, coinHeight);
        }
        mBackBitmap = drawableToBitmap(layerDrawable, layerWidth, layerHeight);

        mCanvas.drawBitmap(mBackBitmap, 0, 0, null);
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (null == drawable || 0 == width || 0 == height) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(
                width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null);
            if (isDownOrMove) {
                //绘制橡皮擦
//                canvas.drawBitmap(moveBitmap, mLastX - moveBitmap.getWidth() / 2, mLastY - moveBitmap.getHeight() / 2, null);
                canvas.drawBitmap(moveBitmap, mLastX - moveBitmap.getWidth() * 3 / 4 , mLastY- moveBitmap.getHeight() * 1/ 4, null);
            }
        }
    }

    /**
     * 绘制线条
     */
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

            int w = layerWidth;
            int h = layerHeight;

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


    public void clearAll() {
        isComplete = true;
        postInvalidate();
    }

    public void setLayer(Drawable drawable, int width, int height) {
        this.layerDrawable = drawable;
        this.layerWidth = width;
        this.layerHeight = height;
        measure(width, height);
        invalidate();
    }

    public void setCoin(Drawable drawable, int width, int height) {
        this.coinDrawable = drawable;
        this.coinWidth = width;
        this.coinHeight = height;
        moveBitmap = drawableToBitmap(coinDrawable, coinWidth, coinHeight);
        invalidate();
    }
}
