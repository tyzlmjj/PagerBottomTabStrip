package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 点击切换颜色的View
 */
class ChangeColorsView extends View
{

    public ChangeColorsView(Context context)
    {
        super(context);
        init(context);
    }

    public ChangeColorsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChangeColorsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context mContext;

    private float R;

    List<Oval> mOvals;

    private Paint mPaint;

    void init(Context context)
    {
        mContext = context;

        mOvals = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        setFitsSystemWindows(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        R = (float) Math.sqrt(getMeasuredHeight()*getMeasuredHeight()+getMeasuredWidth()*getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Iterator<Oval> iterator = mOvals.iterator();
        while(iterator.hasNext())
        {
            Oval oval = iterator.next();
            mPaint.setColor(oval.color);
            if(oval.r < R)
            {
                oval.r += R/30f;
                RectF rectF = new RectF(oval.x-oval.r,oval.y-oval.r,oval.x+oval.r,oval.y+oval.r);
                canvas.drawOval(rectF, mPaint);
            }
            else
            {
                ChangeColorsView.this.setBackgroundColor(oval.color);
                canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
                //单线程下防止ConcurrentModificationException异常
                iterator.remove();
            }
            invalidate();
        }
    }

    /**
     * 添加一个圆形颜色
     * @param color 颜色
     * @param x X座标
     * @param y y座标
     */
    public void addOvalColor(int color,float x,float y)
    {
        Oval oval = new Oval();
        oval.color = color;
        oval.r = Utils.dp2px(mContext,2);
        oval.x = x;
        oval.y = y;

        mOvals.add(oval);

        invalidate();
    }

    class Oval
    {
        public int color;

        public float r;

        public float x;

        public float y;
    }



//    //测试
//    private int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event)
//    {
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//
//        Oval oval = new Oval();
//        oval.color = testColors[(int) (Math.random()* testColors.length)];
//        oval.r = Utils.dp2px(mContext,3);
//        oval.x = event.getX();
//        oval.y = event.getY();
//
//        mOvals.add(oval);
//
//        invalidate();
//
//        return super.dispatchTouchEvent(event);
//    }
}
