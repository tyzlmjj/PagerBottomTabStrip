package me.majiajie.pagerbottomtabstrip.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.ItemController;
import me.majiajie.pagerbottomtabstrip.R;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 存放 Material Design 风格按钮的垂直布局
 */
public class MaterialItemVerticalLayout extends ViewGroup implements ItemController {

    private final int NAVIGATION_ITEM_SIZE;

    private List<BaseTabItem> mItems;
    private List<OnTabItemSelectedListener> mListeners = new ArrayList<>();

    private int mSelected = -1;

    public MaterialItemVerticalLayout(Context context) {
        this(context,null);
    }

    public MaterialItemVerticalLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaterialItemVerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        NAVIGATION_ITEM_SIZE = getResources().getDimensionPixelSize(R.dimen.material_bottom_navigation_height);
    }

    public void initialize(List<BaseTabItem> items) {
        mItems = items;

        //添加按钮到布局，并注册点击事件
        int n = mItems.size();
        for (int i = 0; i < n; i++){
            BaseTabItem v = mItems.get(i);
            v.setChecked(false);
            this.addView(v);

            final int finali = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(finali);
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
    public void setSelect(int index) {

        //重复选择
        if(index == mSelected){
            for(OnTabItemSelectedListener listener:mListeners) {
                listener.onRepeat(mSelected);
            }
            return;
        }

        //记录前一个选中项和当前选中项
        int oldSelected = mSelected;
        mSelected = index;

        //前一个选中项必须不小于0才有效
        if(oldSelected >= 0) {
            mItems.get(oldSelected).setChecked(false);
        }

        mItems.get(mSelected).setChecked(true);

        //事件回调
        for(OnTabItemSelectedListener listener:mListeners) {
            listener.onSelected(mSelected,oldSelected);
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
}
