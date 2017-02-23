package me.majiajie.pagerbottomtabstrip.item;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import me.majiajie.pagerbottomtabstrip.R;
import me.majiajie.pagerbottomtabstrip.Utils;

public class MaterialItemView extends BaseTabItem
{
    private final int mDefaultHeight;
    private final int mDefaultMargin;
    private final int mShiftAmount;
    private final float mScaleUpFactor;
    private final float mScaleDownFactor;

    private ImageView mIcon;
    private final TextView mSmallLabel;
    private final TextView mLargeLabel;

    private boolean mShiftingMode=true;

    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;

    private int mDefaultColor = 0x89000000;
    private int mCheckedColor;

    private boolean mChecked;

    public MaterialItemView(Context context) {
        this(context,null);
    }

    public MaterialItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaterialItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final Resources res = getResources();
        int inactiveLabelSize =
                res.getDimensionPixelSize(R.dimen.material_bottom_navigation_text_size);
        int activeLabelSize = res.getDimensionPixelSize(
                R.dimen.material_bottom_navigation_active_text_size);
        mDefaultMargin = res.getDimensionPixelSize(R.dimen.material_bottom_navigation_margin);
        mDefaultHeight = res.getDimensionPixelSize(R.dimen.material_bottom_navigation_height);
        mShiftAmount = inactiveLabelSize - activeLabelSize;
        mScaleUpFactor = 1f * activeLabelSize / inactiveLabelSize;
        mScaleDownFactor = 1f * inactiveLabelSize / activeLabelSize;

        LayoutInflater.from(context).inflate(R.layout.item_material, this, true);
//        setBackgroundResource(R.drawable.material_bottom_navigation_item_background);
        mIcon = (ImageView) findViewById(R.id.icon);
        mSmallLabel = (TextView) findViewById(R.id.smallLabel);
        mLargeLabel = (TextView) findViewById(R.id.largeLabel);

        mCheckedColor = Utils.getAttrColor(context,R.attr.colorPrimary);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), mDefaultHeight);
    }

    @Override
    public void setChecked(boolean checked)
    {
        mChecked = checked;

        ViewCompat.setPivotX(mLargeLabel, mLargeLabel.getWidth() / 2);
        ViewCompat.setPivotY(mLargeLabel, mLargeLabel.getBaseline());
        ViewCompat.setPivotX(mSmallLabel, mSmallLabel.getWidth() / 2);
        ViewCompat.setPivotY(mSmallLabel, mSmallLabel.getBaseline());
        if (mShiftingMode) {
            if (checked) {
                LayoutParams iconParams = (LayoutParams) mIcon.getLayoutParams();
                iconParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                iconParams.topMargin = mDefaultMargin;
                mIcon.setLayoutParams(iconParams);
                mLargeLabel.setVisibility(VISIBLE);
                ViewCompat.setScaleX(mLargeLabel, 1f);
                ViewCompat.setScaleY(mLargeLabel, 1f);
            } else {
                LayoutParams iconParams = (LayoutParams) mIcon.getLayoutParams();
                iconParams.gravity = Gravity.CENTER;
                iconParams.topMargin = mDefaultMargin;
                mIcon.setLayoutParams(iconParams);
                mLargeLabel.setVisibility(INVISIBLE);
                ViewCompat.setScaleX(mLargeLabel, 0.5f);
                ViewCompat.setScaleY(mLargeLabel, 0.5f);
            }
            mSmallLabel.setVisibility(INVISIBLE);
        } else {
            if (checked) {
                LayoutParams iconParams = (LayoutParams) mIcon.getLayoutParams();
                iconParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                iconParams.topMargin = mDefaultMargin + mShiftAmount;
                mIcon.setLayoutParams(iconParams);
                mLargeLabel.setVisibility(VISIBLE);
                mSmallLabel.setVisibility(INVISIBLE);

                ViewCompat.setScaleX(mLargeLabel, 1f);
                ViewCompat.setScaleY(mLargeLabel, 1f);
                ViewCompat.setScaleX(mSmallLabel, mScaleUpFactor);
                ViewCompat.setScaleY(mSmallLabel, mScaleUpFactor);
            } else {
                LayoutParams iconParams = (LayoutParams) mIcon.getLayoutParams();
                iconParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                iconParams.topMargin = mDefaultMargin;
                mIcon.setLayoutParams(iconParams);
                mLargeLabel.setVisibility(INVISIBLE);
                mSmallLabel.setVisibility(VISIBLE);

                ViewCompat.setScaleX(mLargeLabel, mScaleDownFactor);
                ViewCompat.setScaleY(mLargeLabel, mScaleDownFactor);
                ViewCompat.setScaleX(mSmallLabel, 1f);
                ViewCompat.setScaleY(mSmallLabel, 1f);
            }
        }

        refreshDrawableState();
    }

    @Override
    public void setTitle(String title) {
        mSmallLabel.setText(title);
        mLargeLabel.setText(title);
    }

    @Override
    public void setIcon(Drawable drawable) {
        mDefaultDrawable = Utils.tint(drawable,mDefaultColor);
        if(!mChecked)
        {
            mIcon.setImageDrawable(mDefaultDrawable);
        }
    }

    @Override
    public void setCheckedIcon(Drawable drawable) {
        mCheckedDrawable = Utils.tint(drawable,mCheckedColor);
        if(mChecked)
        {
            mIcon.setImageDrawable(mCheckedDrawable);
        }
    }

    @Override
    public void setColor(int color) {
        mDefaultColor = color;

        if(mDefaultDrawable != null)
        {
            mDefaultDrawable = Utils.tint(mDefaultDrawable,mDefaultColor);

            if(!mChecked)
            {
                mIcon.setImageDrawable(mDefaultDrawable);
            }
        }
    }

    @Override
    public void setCheckedColor(int color) {
        mCheckedColor = color;

        if(mCheckedDrawable != null)
        {
            mCheckedDrawable = Utils.tint(mCheckedDrawable,mCheckedColor);

            if(mChecked)
            {
                mIcon.setImageDrawable(mCheckedDrawable);
            }
        }
    }

    @Override
    public void setMessageNumber(int number) {

    }

    @Override
    public void setHasMessage(boolean hasMessage) {

    }
}
