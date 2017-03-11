package me.majiajie.pagerbottomtabstrip;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.internal.CustomItemLayout;
import me.majiajie.pagerbottomtabstrip.internal.MaterialItemLayout;
import me.majiajie.pagerbottomtabstrip.internal.Utils;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.MaterialItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class PageBottomTabLayout extends ViewGroup
{
    private int mTabPaddingTop;
    private int mTabPaddingBottom;

    private NavigationController mNavigationController;

    private ViewPagerPageChangeListener mPageChangeListener;
    private ViewPager mViewPager;

    private OnTabItemSelectedListener mTabItemListener = new OnTabItemSelectedListener() {
        @Override
        public void onSelected(int index, int old) {
            if(mViewPager != null){
                mViewPager.setCurrentItem(index,false);
            }
        }
        @Override
        public void onRepeat(int index) {}
    };

    public PageBottomTabLayout(Context context) {
        this(context,null);
    }

    public PageBottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageBottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0,0,0,0);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.PageBottomTabLayout);
        if(a.hasValue(R.styleable.PageBottomTabLayout_tabPaddingTop)) {
            mTabPaddingTop = a.getDimensionPixelSize(R.styleable.PageBottomTabLayout_tabPaddingTop,0);
        }
        if(a.hasValue(R.styleable.PageBottomTabLayout_tabPaddingBottom)) {
            mTabPaddingBottom = a.getDimensionPixelSize(R.styleable.PageBottomTabLayout_tabPaddingBottom,0);
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int count = getChildCount();
        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredWidth(), MeasureSpec.EXACTLY);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredHeight(), MeasureSpec.EXACTLY);

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int width = r - l;
        final int height = b - t;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            child.layout(0, 0, width, height);
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
    public CustomBuilder custom()
    {
        return new CustomBuilder();
    }


    /**
     * 构建 自定义 的导航栏
     */
    public class CustomBuilder
    {
        List<BaseTabItem> items;

        CustomBuilder(){
            items = new ArrayList<>();
        }

        /**
         * 完成构建
         * @return  {@link NavigationController},通过它进行后续操作
         */
        public NavigationController build(){

            //未添加任何按钮
            if(items.size() == 0){return null;}

            CustomItemLayout customItemLayout = new CustomItemLayout(getContext());
            customItemLayout.initialize(items);
            customItemLayout.setPadding(0,mTabPaddingTop,0,mTabPaddingBottom);

            PageBottomTabLayout.this.removeAllViews();
            PageBottomTabLayout.this.addView(customItemLayout);

            mNavigationController = new NavigationController(new Controller(),customItemLayout);
            mNavigationController.addTabItemSelectedListener(mTabItemListener);

            return mNavigationController;
        }

        /**
         * 添加一个导航按钮
         * @param baseTabItem   {@link BaseTabItem},所有自定义Item都必须继承它
         * @return {@link CustomBuilder}
         */
        public CustomBuilder addItem(BaseTabItem baseTabItem){
            items.add(baseTabItem);
            return CustomBuilder.this;
        }
    }

    /**
     * 构建 Material Desgin 风格的导航栏
     */
    public class MaterialBuilder
    {
        List<MaterialItemView> items;
        int defaultColor;
        int mode;
        int messageBackgroundColor;
        int messageNumberColor;
        int itemBackgroundResource;

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

            //检查是否设置了消息圆点的颜色
            if(messageBackgroundColor != 0) {
                for (MaterialItemView v:items) {
                    v.setMessageBackgroundColor(messageBackgroundColor);
                }
            }

            //检查是否设置了消息数字的颜色
            if(messageNumberColor != 0) {
                for (MaterialItemView v:items) {
                    v.setMessageNumberColor(messageNumberColor);
                }
            }

            MaterialItemLayout materialItemLayout = new MaterialItemLayout(getContext());
            materialItemLayout.initialize(items,mode);
            materialItemLayout.setPadding(0,mTabPaddingTop,0,mTabPaddingBottom);

            PageBottomTabLayout.this.removeAllViews();
            PageBottomTabLayout.this.addView(materialItemLayout);

            mNavigationController = new NavigationController(new Controller(),materialItemLayout);
            mNavigationController.addTabItemSelectedListener(mTabItemListener);

            //检查是否设置了Item背景
            if(itemBackgroundResource != 0) {
                for (MaterialItemView v:items) {
                    v.setBackgroundResource(itemBackgroundResource);
                }
            }

            return mNavigationController;
        }

        /**
         * 添加一个导航按钮
         * @param drawable  图标资源
         * @param title     显示文字内容.尽量简短
         * @return {@link MaterialBuilder}
         */
        public MaterialBuilder addItem(@DrawableRes int drawable, String title){
            addItem(drawable,drawable,title,Utils.getColorPrimary(getContext()));
            return MaterialBuilder.this;
        }

        /**
         * 添加一个导航按钮
         * @param drawable          图标资源
         * @param checkedDrawable   选中时的图标资源
         * @param title             显示文字内容.尽量简短
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder addItem(@DrawableRes int drawable, @DrawableRes int checkedDrawable,String title){
            addItem(drawable,checkedDrawable,title,Utils.getColorPrimary(getContext()));
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
         * 设置消息圆点的颜色
         * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder setMessageBackgroundColor(@ColorInt int color){
            messageBackgroundColor = color;
            return MaterialBuilder.this;
        }

        /**
         * 设置消息数字的颜色
         * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder setMessageNumberColor(@ColorInt int color){
            messageNumberColor = color;
            return MaterialBuilder.this;
        }

        /**
         * 设置每个Item的背景
         * @param resid 背景资源
         * @return  {@link MaterialBuilder}
         */
        public MaterialBuilder setItemBackgroundResource(@DrawableRes int resid){
            itemBackgroundResource = resid;
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

    /**
     * 实现控制接口
     */
    private class Controller implements BottomLayoutController{

        private ObjectAnimator animator;
        private boolean hide = false;

        @Override
        public void setupWithViewPager(ViewPager viewPager) {
            if(viewPager == null){return;}

            mViewPager = viewPager;

            if(mPageChangeListener != null){
                mViewPager.removeOnPageChangeListener(mPageChangeListener);
            } else {
                mPageChangeListener = new ViewPagerPageChangeListener();
            }

            if(mNavigationController != null) {
                int n = mViewPager.getCurrentItem();
                if(mNavigationController.getSelected() != n){
                    mNavigationController.setSelect(n);
                }
                mViewPager.addOnPageChangeListener(mPageChangeListener);
            }
        }

        @Override
        public void hideBottomLayout() {
            if(!hide){
                hide = true;
                getAnimator().start();
            }
        }

        @Override
        public void showBottomLayout() {
            if(hide){
                hide = false;
                getAnimator().reverse();
            }
        }

        private ObjectAnimator getAnimator(){
            if (animator == null){
                animator = ObjectAnimator.ofFloat(
                        PageBottomTabLayout.this,"translationY",0,PageBottomTabLayout.this.getHeight());
                animator.setDuration(300);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
            }
            return animator;
        }
    }

    private class ViewPagerPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(mNavigationController != null && mNavigationController.getSelected() != position){
                mNavigationController.setSelect(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private static final String INSTANCE_STATUS = "INSTANCE_STATUS";
    private final String STATUS_SELECTED = "STATUS_SELECTED";

    @Override
    protected Parcelable onSaveInstanceState()
    {
        if(mNavigationController == null){return super.onSaveInstanceState();}
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putInt(STATUS_SELECTED,mNavigationController.getSelected());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            int selected = bundle.getInt(STATUS_SELECTED,0);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));

            if(selected != 0 && mNavigationController != null)
            {
                mNavigationController.setSelect(selected);
            }

            return;
        }
        super.onRestoreInstanceState(state);
    }
}
