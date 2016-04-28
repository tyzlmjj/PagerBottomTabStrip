package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import me.majiajie.library.R;

/**
 * 底部导航的按钮项
 */
public final class TabItem extends View
{

    /**
     * 文字未选中时的大小
     */
    private final float TEXTSIZE_DEFAULT = 12;

    /**
     * 文字选中时的大小
     */
    private final float TEXTSIZE_SELECTED = 14;

    /**
     * 图标的宽度（正方形）
     */
    private final float WIDTH_ICON = 24;

    /**
     * 显示的文字
     */
    private String mText;

    /**
     * 未选中状态下的图标
     */
    private Bitmap mIconDefault;

    /**
     * 选中状态下的图标，未设置的话就和未选中状态下的一样
     */
    private Bitmap mIconSelected;

    /**
     * 未选中状态下的图标和文字颜色
     */
    private int mColorDefault = 0xFFAAAAAA;

    /**
     * 选中状态下的图标和文字颜色
     */
    private int mColorSelected = 0xFFFF0000;

    /**
     * 圆形消息图案的背景色
     */
    private int mColorMessageBackground = 0xFFFF0000;

    /**
     * 圆形消息图案的数字颜色
     */
    private int mColorMessageText = 0xFFFFFFFF;


    private Context mContext;

    private int mMode = 0;

    private final float SELECTED = 1f;

    private final float DEFAULT = 0f;

    /**
     * 期望的缩放值，表示选中或未选中
     */
    private float mScale = DEFAULT;

    /**
     * 过渡用的缩放值，记录当前确切的缩放值
     */
    private float mScaleTem = mScale;

    /**
     * 消息数量
     */
    private int mMessageNumber = 0;

    /**
     * 判断是否显示无数字的消息小圆点
     */
    private boolean hasMessages = false;


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


    //*******START***公共方法************************************************************************

    /**
     * 设置模式
     * @param mode {@link TabStripMode TabStripMode}
     */
    protected void setMode(int mode)
    {
        mMode = mode;
    }

    /**
     * 设置是否显示无数字的消息小圆点
     * @param display   true显示
     */
    protected void setDisplayOval(boolean display)
    {
        hasMessages = display;
        invalidateView();
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

    /**
     * 设置圆形消息的背景颜色
     * @param color  16进制整形表示的颜色，例如红色：0xFFFF0000
     */
    public void setMessageBackgroundColor(@ColorInt int color)
    {
        mColorMessageBackground = color;
    }

    /**
     * 设置圆形消息的数字颜色
     * @param color  16进制整形表示的颜色，例如红色：0xFFFF0000
     */
    public void setMessageTextColor(@ColorInt int color)
    {
        mColorMessageText = color;
    }

    /**
     * 构建导航按钮
     * @return
     */
    public TabItemBuilder builder()
    {
        return new builder();
    }

    //*******END***公共方法**************************************************************************


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
            drawMessages(canvas,scale);
            drawOval(canvas,scale);
            invalidate();
        }
        else
        {
            drawText(canvas,mScale);
            drawIcon(canvas,mScale);
            drawMessages(canvas,mScale);
            drawOval(canvas,mScale);
        }
    }

    private float getTemScale()
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

        Rect textBound = new Rect();
        Paint textPaint = new Paint();
        textPaint.setTextSize(Utils.sp2px(mContext,TEXTSIZE_DEFAULT+(TEXTSIZE_SELECTED-TEXTSIZE_DEFAULT)*n));
        textPaint.getTextBounds(mText, 0, mText.length(), textBound);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //判断是否选中再设置颜色
        textPaint.setColor(mScale == DEFAULT?mColorDefault:mColorSelected);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        float x = getMeasuredWidth()/2f;
        float y = getMeasuredHeight() - Utils.dp2px(mContext,10)- textBound.height()/2f- fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

        canvas.drawText(mText,x,y, textPaint);
    }


    /**
     * 画ICON
     * @param canvas {@link android.graphics.Canvas Canvas}
     */
    private void drawIcon(Canvas canvas,float n)
    {
        int width = (int) Utils.dp2px(mContext,WIDTH_ICON);

        Bitmap bitmap = Bitmap.createBitmap(width,width,Bitmap.Config.ARGB_8888);
        Canvas canvasTem = new Canvas(bitmap);
        Paint paint = new Paint();
        //判断是否选中再设置颜色
        paint.setColor(mScale == DEFAULT?mColorDefault:mColorSelected);
        paint.setAntiAlias(true);
        canvasTem.drawRect(0,0,width,width,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvasTem.drawBitmap(mScale == DEFAULT?mIconDefault:mIconSelected,0,0,paint);


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
     * 画消息图标
     * @param canvas {@link android.graphics.Canvas Canvas}
     */
    private void drawMessages(Canvas canvas,float n)
    {
        if(mMessageNumber > 0)
        {
            //画背景的圆形
            Paint backgroundPaint = new Paint();
            backgroundPaint.setColor(mColorMessageBackground);
            backgroundPaint.setAntiAlias(true);

            int width = (int) Utils.dp2px(mContext,20);
            Bitmap bitmap = Bitmap.createBitmap(width,width,Bitmap.Config.ARGB_8888);
            Canvas canvasMessages = new Canvas(bitmap);
            RectF messageRectF = new RectF(0,0,width,width);
            canvasMessages.drawOval(messageRectF,backgroundPaint);

            //画数字
            String number = mMessageNumber>99?"99+":mMessageNumber+"";
            float textSize;
            if (number.length()==1)
            {
                textSize = 13;
            }else if(number.length()==2)
            {
                textSize = 11;
            }else
            {
                textSize = 10;
            }
            Paint numberPaint = new Paint();
            numberPaint.setColor(mColorMessageText);
            numberPaint.setTextSize(Utils.dp2px(mContext,textSize));
            numberPaint.setAntiAlias(true);
            numberPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();

            float x = width/2f;
            float y = width/2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;
            canvasMessages.drawText(number,x,y,numberPaint);


            float left = getMeasuredWidth()/2f+Utils.dp2px(mContext,6);

            float top;
            top = Utils.dp2px(mContext,6);
//            if((mMode & TabStripMode.HIDE_TEXT) > 0)
//            {
//                top = Utils.dp2px(mContext,16-10*n);
//            }
//            else
//            {
//                top = Utils.dp2px(mContext,8-2*n);
//            }
            canvas.drawBitmap(bitmap,left,top,null);
        }
    }

    /**
     * 画无数字的消息红点
     * @param canvas {@link android.graphics.Canvas Canvas}
     */
    private void drawOval(Canvas canvas,float n)
    {
        if(hasMessages && mMessageNumber <= 0)
        {
            Paint paint = new Paint();
            paint.setColor(mColorMessageBackground);
            paint.setAntiAlias(true);
            float left = getMeasuredWidth()/2f+Utils.dp2px(mContext,6);
            float top = Utils.dp2px(mContext,6);
            float width = Utils.dp2px(getContext(),10);
            RectF messageRectF = new RectF(left,top,left+width,top+width);
            canvas.drawOval(messageRectF,paint);
        }
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


    class builder implements TabItemBuilder
    {
        @Override
        public TabItem build()
        {
            if(mIconSelected == null)
            {
                mIconSelected = mIconDefault;
            }
            return TabItem.this;
        }

        @Override
        public TabItemBuilder setText(@NonNull String text)
        {
            mText = text;
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
