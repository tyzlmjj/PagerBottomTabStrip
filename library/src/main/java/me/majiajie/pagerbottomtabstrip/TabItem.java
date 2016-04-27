package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import me.majiajie.library.R;

/**
 * 底部导航的按钮项
 */
public final class TabItem extends View
{

    private String mText;

    private Bitmap mIconDefault;

    private Bitmap mIconSelected;

    private int mColorDefault = 0xFFAAAAAA;

    private int mColorSelected = 0xFFFF0000;

    private int mColorMessageBackground = 0xFFFF0000;

    private int mColorMessageText = 0xFFFFFFFF;

    private int mColorSelectedBackground = 0xFFFFFFFF;


    private Context mContext;

    private Paint mTextPaint;

    private Rect mTextBound;

    private final float TEXTSIZE_DEFAULT = 12;

    private final float TEXTSIZE_SELECTED = 14;


    private int mMode = TabStripMode.HIDE_TEXT;

    private final float SELECTED = 1f;

    private final float DEFAULT = 0f;

    private float mScale = DEFAULT;

    private float mScaleTem = mScale;


    public TabItem(Context context)
    {
        super(context);
        init(context);
    }

    public TabItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        //设置点击效果
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, typedValue, true);
        setBackgroundResource(typedValue.resourceId);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mScale = mScale == DEFAULT? SELECTED:DEFAULT;
                invalidateView();
            }
        });
    }

    /**
     * 设置模式
     * @param mode {@link TabStripMode TabStripMode}
     */
    protected void setMode(int mode)
    {
        mMode = mode;
    }

    /**
     * 设置是否选中
     * @param isSelected true选中
     */
    protected void setSelect(boolean isSelected)
    {
        if(isSelected)
        {
            mScale = SELECTED;
        }
        else
        {
            mScale = DEFAULT;
        }
        invalidateView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension((int)Utils.dp2px(mContext,96),(int)Utils.dp2px(mContext,56));
        setMinimumWidth((int)Utils.dp2px(mContext,96));

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(mScale != mScaleTem)
        {
            float scale = getTemScale();
            drawText(canvas,scale);
            drawIcon(canvas,scale);
            invalidate();
        }
        else
        {
            drawText(canvas,mScale);
            drawIcon(canvas,mScale);
        }
    }

    public float getTemScale()
    {
        mScaleTem += mScale-mScaleTem>0? +0.1f:-0.1f;
        if(mScaleTem < 0f)
        {
            mScaleTem = 0f;
        }

        if(mScaleTem > 1f)
        {
            mScaleTem = 1f;
        }
        return mScaleTem;
    }

    /**
     * 画文字
     * @param canvas {@link android.graphics.Canvas Canvas}
     */
    private void drawText(Canvas canvas,float n)
    {
        if((mMode & TabStripMode.HIDE_TEXT) > 0 && mScale == DEFAULT)
        {
            return;
        }
        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(Utils.sp2px(mContext,TEXTSIZE_DEFAULT+(TEXTSIZE_SELECTED-TEXTSIZE_DEFAULT)*n));
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //判断是否选中
        if(mScale == DEFAULT)
        {
            mTextPaint.setColor(mColorDefault);
        }
        else
        {
            mTextPaint.setColor(mColorSelected);
        }

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

        float x = getMeasuredWidth()/2f;
        float y = getMeasuredHeight() - Utils.dp2px(mContext,10)- mTextBound.height()/2f- fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

        canvas.drawText(mText,x,y, mTextPaint);
    }


    /**
     * 画ICON
     * @param canvas {@link android.graphics.Canvas Canvas}
     */
    private void drawIcon(Canvas canvas,float n)
    {
        int width = (int) Utils.dp2px(mContext,24);

        Bitmap bitmap = Bitmap.createBitmap(width,width,Bitmap.Config.ARGB_8888);
        Canvas canvasTem = new Canvas(bitmap);
        Paint paint = new Paint();
        //判断是否选中
        if(mScale == DEFAULT)
        {
            paint.setColor(mColorDefault);
        }
        else
        {
            paint.setColor(mColorSelected);
        }
        paint.setAntiAlias(true);
        canvasTem.drawRect(0,0,width,width,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvasTem.drawBitmap(mIconDefault,0,0,paint);


        float left = getMeasuredWidth()/2f-Utils.dp2px(mContext,12);

        float top;
        if((mMode & TabStripMode.HIDE_TEXT) > 0)
        {
            top = Utils.dp2px(mContext,16-10*n);
        }
        else
        {
            top = Utils.dp2px(mContext,8-2*n);
        }

        canvas.drawBitmap(bitmap,left,top,null);
    }


    /**
     * 刷新视图
     */
    private void invalidateView()
    {
        if (Looper.getMainLooper() == Looper.myLooper())
        {
            invalidate();
        } else
        {
            postInvalidate();
        }
    }

    /**
     * 构建导航按钮
     * @return
     */
    public TabItemBuilder builder()
    {
        return new builder();
    }

    class builder implements TabItemBuilder
    {
        @Override
        public TabItem build()
        {
            return TabItem.this;
        }

        @Override
        public TabItemBuilder setText(@NonNull String text)
        {
            mText = text;
            return builder.this;
        }

        @Override
        public TabItemBuilder setMessageBackgroundColor(@ColorInt int color)
        {
            mColorMessageBackground = color;
            return builder.this;
        }

        @Override
        public TabItemBuilder setMessageTextColor(@ColorInt int color)
        {
            mColorMessageText = color;
            return builder.this;
        }

        @Override
        public TabItemBuilder setSelectedIcon(@DrawableRes int drawable)
        {
            return setSelectedIcon(ContextCompat.getDrawable(mContext,drawable));
        }

        @Override
        public TabItemBuilder setSelectedIcon(@NonNull Drawable drawable)
        {
            mIconSelected = getICON(drawable);
            return builder.this;
        }

        @Override
        public TabItemBuilder setDefaultIcon(@DrawableRes int drawable)
        {
            return setDefaultIcon(ContextCompat.getDrawable(mContext,drawable));
        }

        @Override
        public TabItemBuilder setDefaultIcon(@NonNull Drawable drawable)
        {
            mIconDefault = getICON(drawable);
            return builder.this;
        }

        @Override
        public TabItemBuilder setSelectedColor(@ColorInt int color)
        {
            mColorSelected = color;
            return builder.this;
        }

        @Override
        public TabItemBuilder setDefaultColor(@ColorInt int color)
        {
            mColorDefault = color;
            return builder.this;
        }

        @Override
        public TabItemBuilder setSelectedBackgroundColor(@ColorInt int color)
        {
            mColorSelectedBackground = color;
            return builder.this;
        }

        private Bitmap getICON(Drawable drawable)
        {
            int width = drawable.getIntrinsicWidth();
            int height= drawable.getIntrinsicHeight();

            Bitmap oldbmp = Utils.drawableToBitmap(drawable);
            Matrix matrix = new Matrix();
            float scaleWidth = (Utils.dp2px(mContext,24)/ width);
            float scaleHeight = (Utils.dp2px(mContext,24)/ height);
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);

            return newbmp;
        }
    }


}
