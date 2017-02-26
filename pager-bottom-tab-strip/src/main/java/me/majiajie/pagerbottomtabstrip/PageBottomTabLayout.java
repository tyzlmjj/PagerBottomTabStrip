package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.internal.MaterialItemLayout;
import me.majiajie.pagerbottomtabstrip.item.MaterialItemView;

public class PageBottomTabLayout extends FrameLayout
{

    private int mTabPaddingTop;
    private int mTabPaddingBottom;

    public PageBottomTabLayout(Context context) {
        this(context,null);
    }

    public PageBottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageBottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0,0,0,0);

        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                R.styleable.PageBottomTabLayout);
        if(a.hasValue(R.styleable.PageBottomTabLayout_tabPaddingTop)) {
            mTabPaddingTop = a.getDimensionPixelSize(R.styleable.PageBottomTabLayout_tabPaddingTop,0);
        }
        if(a.hasValue(R.styleable.PageBottomTabLayout_tabPaddingBottom)) {
            mTabPaddingBottom = a.getDimensionPixelSize(R.styleable.PageBottomTabLayout_tabPaddingBottom,0);
        }
        a.recycle();
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

    /**
     * 构建 Material Desgin 风格的导航栏
     */
    public class MaterialBuilder
    {
        List<MaterialItemView> items;
        int defaultColor;
        int mode;

        MaterialBuilder() {
            items = new ArrayList<>();
        }

        /**
         * 完成构建
         *
         * @return  {@link NavigationController},通过它进行后续操作
         */
        public NavigationController build()
        {
            //未添加任何按钮
            if(items.size() == 0){return null;}

            //检查是否设置了默认颜色
            if(defaultColor != 0) {
                for (MaterialItemView v:items) {
                    v.setColor(defaultColor);
                }
            }

            MaterialItemLayout materialItemLayout = new MaterialItemLayout(getContext());
            materialItemLayout.initialize(items,mode);
            materialItemLayout.setPadding(0,mTabPaddingTop,0,mTabPaddingBottom);

            PageBottomTabLayout.this.removeAllViews();
            PageBottomTabLayout.this.addView(materialItemLayout);

            return materialItemLayout;
        }

        /**
         * 添加一个导航按钮
         * @param drawable  图标资源
         * @param title     显示文字内容.尽量简短
         * @return {@link MaterialBuilder}
         */
        public MaterialBuilder addItem(@DrawableRes int drawable, String title){
            addItem(drawable,drawable,title,Utils.getAttrColor(getContext(),R.attr.colorPrimary));
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
            addItem(drawable,drawable,title,chekedColor);
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
            itemView.setCheckedColor(chekedColor);
            itemView.setIcon(ContextCompat.getDrawable(getContext(),drawable));
            itemView.setCheckedIcon(ContextCompat.getDrawable(getContext(),checkedDrawable));
            itemView.setTitle(title);
            items.add(itemView);
            return MaterialBuilder.this;
        }

        /**
         * 设置导航按钮的默认（未选中状态）颜色
         * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder setDefaultColor(@ColorInt int color) {
            defaultColor = color;
            return MaterialBuilder.this;
        }

        /**
         * 设置模式。默认文字一直显示，且背景色不变。
         * 可以通过{@link MaterialMode}选择模式。
         *
         * <p>例如:</p>
         * {@code MaterialMode.HIDE_TEXT}
         *
         * <p>或者多选:</p>
         * {@code MaterialMode.HIDE_TEXT | MaterialMode.CHANGE_BACKGROUND_COLOR}
         *
         * @param mode {@link MaterialMode}
         * @return {@link MaterialBuilder}
         */
        public MaterialBuilder setMode(int mode){
            MaterialBuilder.this.mode = mode;
            return MaterialBuilder.this;
        }
    }
}
