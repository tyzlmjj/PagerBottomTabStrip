package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.MaterialItemView;

public class PageBottomTabLayout extends LinearLayout
{
    private final int MATERIAL_BOTTOM_NAVIGATION_ACTIVE_ITEM_MAX_WIDTH;
    private final int MATERIAL_BOTTOM_NAVIGATION_ITEM_MAX_WIDTH;
    private final int MATERIAL_BOTTOM_NAVIGATION_ITEM_MIN_WIDTH;

    private List<BaseTabItem> mItems;

    private boolean mIsMaterial = false;

    private int mIndex = -1;
    private int mOldIndex = -1;

    public PageBottomTabLayout(Context context) {
        this(context,null);
    }

    public PageBottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageBottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final Resources res = getResources();

        MATERIAL_BOTTOM_NAVIGATION_ACTIVE_ITEM_MAX_WIDTH = res.getDimensionPixelSize(R.dimen.material_bottom_navigation_active_item_max_width);
        MATERIAL_BOTTOM_NAVIGATION_ITEM_MAX_WIDTH = res.getDimensionPixelSize(R.dimen.material_bottom_navigation_item_max_width);
        MATERIAL_BOTTOM_NAVIGATION_ITEM_MIN_WIDTH = res.getDimensionPixelSize(R.dimen.material_bottom_navigation_item_min_width);

        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mItems.size() <= 0 ){return;}

        if(mIsMaterial) {

        }
        else {
            int childWidth = getMeasuredWidth() / mItems.size();

            for (BaseTabItem v : mItems) {
                v.getLayoutParams().width = childWidth;
            }
        }
    }

    /**
     * 构建 Material Desgin 风格的导航栏
     */
    public MaterialBuilder material()
    {
        return new MaterialBuilder();
    }

    /**
     * 构建自定义导航栏
     */
    public void custom()
    {

    }

    void checked(int n)
    {
        //不正常的选择项
        if(n >= mItems.size() || n < 0){return;}

        //重复选择
        if(n == mIndex){return;}

        //记录前一个选中项和当前选中项
        mOldIndex = mIndex;
        mIndex = n;

        //前一个选中项必须大于0
        if(mOldIndex >= 0)
        {
            mItems.get(mOldIndex).setChecked(false);
        }
        mItems.get(mIndex).setChecked(true);
    }

    /**
     * 构建 Material Desgin 风格的导航栏
     */
    public class MaterialBuilder
    {
        List<MaterialItemView> items;
        int defaultColor;

        MaterialBuilder(){
            items = new ArrayList<>();
        }

        /**
         * 完成构建
         *
         * @return  {@link Controller},通过它进行后续操作
         */
        public Controller build()
        {
            if(defaultColor != 0) {
                for (MaterialItemView v:items) {
                    v.setColor(defaultColor);
                }
            }

            int n = items.size();
            for (int i = 0; i < n; i++) {
                final int finalI = i;
                items.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checked(finalI);
                    }
                });

                PageBottomTabLayout.this.addView(items.get(i));
            }

            mItems = new ArrayList<BaseTabItem>(items);

            return null;
        }

        /**
         * 添加一个导航按钮
         * @param drawable  图标资源
         * @param title     显示文字内容.尽量简短
         * @return {@link MaterialBuilder}
         */
        public MaterialBuilder addItem(@DrawableRes int drawable, String title){

            MaterialItemView itemView = new MaterialItemView(getContext());
            itemView.setIcon(ContextCompat.getDrawable(getContext(),drawable));
            itemView.setTitle(title);
            items.add(itemView);

            return MaterialBuilder.this;
        }

        /**
         * 添加一个导航按钮
         * @param drawable      图标资源
         * @param title         显示文字内容.尽量简短
         * @param chekedColor   选中的颜色
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder addItem(@DrawableRes int drawable,String title,@ColorInt int chekedColor){

            MaterialItemView itemView = new MaterialItemView(getContext());
            itemView.setIcon(ContextCompat.getDrawable(getContext(),drawable));
            itemView.setTitle(title);
            itemView.setCheckedColor(chekedColor);
            items.add(itemView);

            return MaterialBuilder.this;
        }

        /**
         * 添加一个导航按钮
         * @param drawable          图标资源
         * @param checkedDrawable   选中时的图标资源
         * @param title             显示文字内容.尽量简短
         * @param chekedColor       选中的颜色
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder addItem(@DrawableRes int drawable,@DrawableRes int checkedDrawable,String title,@ColorInt int chekedColor){

            MaterialItemView itemView = new MaterialItemView(getContext());
            itemView.setIcon(ContextCompat.getDrawable(getContext(),drawable));
            itemView.setCheckedIcon(ContextCompat.getDrawable(getContext(),checkedDrawable));
            itemView.setTitle(title);
            itemView.setCheckedColor(chekedColor);
            items.add(itemView);

            return MaterialBuilder.this;
        }

        /**
         * 设置导航按钮的默认（未选中状态）颜色
         * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
         * @return  {@link TabStripBuild}
         */
        public MaterialBuilder setDefaultColor(@ColorInt int color) {
            defaultColor = color;
            return MaterialBuilder.this;
        }

        /**
         * 设置模式。默认文字一直显示，且背景色不变。
         * 可以通过{@link TabLayoutMode}选择模式。
         *
         * <p>例如:</p>
         * {@code TabLayoutMode.HIDE_TEXT}
         *
         * <p>或者多选:</p>
         * {@code TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR}
         *
         * @param tabStripMode {@link TabLayoutMode}
         * @return {@link TabStripBuild}
         */
        public MaterialBuilder setMode(int tabStripMode){

            return MaterialBuilder.this;
        }
    }
}
