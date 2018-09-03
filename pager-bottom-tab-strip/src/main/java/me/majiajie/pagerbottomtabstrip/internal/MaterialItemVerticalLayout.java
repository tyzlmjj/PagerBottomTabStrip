package me.majiajie.pagerbottomtabstrip.internal;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.ItemController;
import me.majiajie.pagerbottomtabstrip.R;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.OnlyIconMaterialItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;

/**
 * 存放 Material Design 风格按钮的垂直布局
 */
public class MaterialItemVerticalLayout extends ViewGroup implements ItemController {

    private final int NAVIGATION_ITEM_SIZE;

    private final List<BaseTabItem> mItems = new ArrayList<>();
    private final List<OnTabItemSelectedListener> mListeners = new ArrayList<>();
    private final List<SimpleTabItemSelectedListener> mSimpleListeners = new ArrayList<>();

    private int mSelected = -1;

    private boolean mItemTintIcon;
    private int mItemDefaultColor;

    public MaterialItemVerticalLayout(Context context) {
        this(context, null);
    }

    public MaterialItemVerticalLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialItemVerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        NAVIGATION_ITEM_SIZE = getResources().getDimensionPixelSize(R.dimen.material_bottom_navigation_height);
    }

    public void initialize(List<BaseTabItem> items, boolean animateLayoutChanges, boolean doTintIcon, int color) {

        mItems.clear();
        mItems.addAll(items);

        mItemTintIcon = doTintIcon;
        mItemDefaultColor = color;

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

        final int heightSpec = MeasureSpec.makeMeasureSpec(NAVIGATION_ITEM_SIZE, MeasureSpec.EXACTLY);
        final int widthSpec = MeasureSpec.makeMeasureSpec(NAVIGATION_ITEM_SIZE, MeasureSpec.EXACTLY);

        for (int i = 0; i < n; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            child.measure(widthSpec, heightSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        //只支持top的padding
        int used = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            child.layout(0, used, child.getMeasuredWidth(), used + child.getMeasuredHeight());

            used += child.getMeasuredHeight();
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return MaterialItemVerticalLayout.class.getName();
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

        final OnlyIconMaterialItemView materialItemView = new OnlyIconMaterialItemView(getContext());
        materialItemView.initialization(title, defaultDrawable, selectedDrawable, mItemTintIcon, mItemDefaultColor, selectedColor);

        materialItemView.setChecked(false);
        materialItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mItems.indexOf(materialItemView);
                if (index >= 0) {
                    setSelect(index);
                }
            }
        });

        if (index >= mItems.size()) {
            this.addView(materialItemView);
            mItems.add(materialItemView);
        } else {
            this.addView(materialItemView, index);
            mItems.add(index, materialItemView);
        }
    }

    @Override
    public void addCustomItem(int index, BaseTabItem item) {
        // nothing
    }
}
