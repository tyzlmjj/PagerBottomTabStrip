package me.majiajie.pagerbottomtabstrip.internal;


import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.ItemController;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;

/**
 * 存放自定义项的布局
 */
public class CustomItemLayout extends ViewGroup implements ItemController {

    private final List<BaseTabItem> mItems = new ArrayList<>();
    private final List<OnTabItemSelectedListener> mListeners = new ArrayList<>();
    private final List<SimpleTabItemSelectedListener> mSimpleListeners = new ArrayList<>();

    private int mSelected = -1;

    public CustomItemLayout(Context context) {
        this(context, null);
    }

    public CustomItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutTransition(new LayoutTransition());
    }

    public void initialize(List<BaseTabItem> items, boolean animateLayoutChanges) {
        mItems.clear();
        mItems.addAll(items);

        if (animateLayoutChanges) {
            setLayoutTransition(new LayoutTransition());
        }

        //添加按钮到布局，并注册点击事件
        int n = mItems.size();
        for (int i = 0; i < n; i++) {
            final BaseTabItem tabItem = mItems.get(i);
            tabItem.setChecked(false);
            this.addView(tabItem);

            tabItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mItems.indexOf(tabItem);
                    if (index >= 0) {
                        setSelect(index);
                    }
                }
            });
        }

        //默认选中第一项
        mSelected = 0;
        mItems.get(0).setChecked(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int n = getChildCount();
        int visableChildCount = 0;
        for (int i = 0; i < n; i++) {
            if (getChildAt(i).getVisibility() != GONE) {
                visableChildCount++;
            }
        }

        if (visableChildCount == 0) {
            return;
        }

        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) / visableChildCount, MeasureSpec.EXACTLY);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop()), MeasureSpec.EXACTLY);

        for (int i = 0; i < n; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        final int width = right - left;
        final int height = bottom - top;
        //只支持top、bottom的padding
        final int padding_top = getPaddingTop();
        final int padding_bottom = getPaddingBottom();
        int used = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                child.layout(width - used - child.getMeasuredWidth(), padding_top, width - used, height - padding_bottom);
            } else {
                child.layout(used, padding_top, child.getMeasuredWidth() + used, height - padding_bottom);
            }
            used += child.getMeasuredWidth();
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return CustomItemLayout.class.getName();
    }

    @Override
    public void setSelect(int index) {
        setSelect(index, true);
    }

    @Override
    public void setSelect(int index, boolean needListener) {

        //重复选择
        if (index == mSelected) {
            if (needListener) {
                for (OnTabItemSelectedListener listener : mListeners) {
                    mItems.get(mSelected).onRepeat();
                    listener.onRepeat(mSelected);
                }
            }
            return;
        }

        //记录前一个选中项和当前选中项
        int oldSelected = mSelected;
        mSelected = index;

        //前一个选中项必须不小于0才有效
        if (oldSelected >= 0) {
            mItems.get(oldSelected).setChecked(false);
        }

        mItems.get(mSelected).setChecked(true);

        if (needListener) {
            //事件回调
            for (OnTabItemSelectedListener listener : mListeners) {
                listener.onSelected(mSelected, oldSelected);
            }
            for (SimpleTabItemSelectedListener listener : mSimpleListeners) {
                listener.onSelected(mSelected, oldSelected);
            }
        }
    }

    @Override
    public void setMessageNumber(int index, int number) {
        mItems.get(index).setMessageNumber(number);
    }

    @Override
    public void setHasMessage(int index, boolean hasMessage) {
        mItems.get(index).setHasMessage(hasMessage);
    }

    @Override
    public void addTabItemSelectedListener(OnTabItemSelectedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void addSimpleTabItemSelectedListener(SimpleTabItemSelectedListener listener) {
        mSimpleListeners.add(listener);
    }

    @Override
    public void setTitle(int index, String title) {
        mItems.get(index).setTitle(title);
    }

    @Override
    public void setDefaultDrawable(int index, Drawable drawable) {
        mItems.get(index).setDefaultDrawable(drawable);
    }

    @Override
    public void setSelectedDrawable(int index, Drawable drawable) {
        mItems.get(index).setSelectedDrawable(drawable);
    }

    @Override
    public int getSelected() {
        return mSelected;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public String getItemTitle(int index) {
        return mItems.get(index).getTitle();
    }

    @Override
    public boolean removeItem(int index) {
        if (index == mSelected || index >= mItems.size() || index < 0) {
            return false;
        }

        if (mSelected > index) {
            mSelected--;
        }

        this.removeViewAt(index);
        mItems.remove(index);
        return true;
    }

    @Override
    public void addMaterialItem(int index, Drawable defaultDrawable, Drawable selectedDrawable, String title, int selectedColor) {
        // nothing
    }

    @Override
    public void addCustomItem(int index, final BaseTabItem item) {
        item.setChecked(false);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mItems.indexOf(item);
                if (index >= 0) {
                    setSelect(index);
                }
            }
        });
        if (index >= mItems.size()) {
            mItems.add(index, item);
            this.addView(item);
        } else {
            mItems.add(index, item);
            this.addView(item, index);
        }
    }

}
