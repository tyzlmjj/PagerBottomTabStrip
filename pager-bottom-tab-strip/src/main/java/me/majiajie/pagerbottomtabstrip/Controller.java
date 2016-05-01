package me.majiajie.pagerbottomtabstrip;


import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * 导航栏控制
 */
public interface Controller
{
    /**
     * 导航栏按钮点击监听
     * @param listener {@link OnTabItemSelectListener}
     */
    void addTabItemClickListener(OnTabItemSelectListener listener);

    /**
     * 设置导航按钮上显示的圆形消息数字，通过顺序索引。
     * @param index     顺序索引
     * @param number    消息数字
     */
    void setMessageNumber(int index, int number);

    /**
     * 设置导航按钮上显示的圆形消息数字，通过TAG索引。
     * @param tag       TAG,默认是按钮的文字，可在构建时设置
     * @param number    消息数字
     */
    void setMessageNumber(Object tag, int number);

    /**
     * 设置显示无数字的消息小原点
     * @param index     顺序索引
     * @param display   true显示
     */
    void setDisplayOval(int index, boolean display);

    /**
     * 设置显示无数字的消息小原点
     * @param tag       TAG,默认是按钮的文字，可在构建时设置
     * @param display   true显示
     */
    void setDisplayOval(Object tag, boolean display);

    /**
     * 设置选中项
     * @param index 顺序索引
     */
    void setSelect(int index);

    /**
     * 设置选中项
     * @param tag   目标TAG
     */
    void setSelect(Object tag);

    /**
     * 获取当前选中项索引
     * @return
     */
    int getSelected();

    /**
     * 获取当前选中项的TAG
     * @return
     */
    Object getSelectedTag();

    /**
     * 设置背景-直接引用{@link android.view.View}的方法
     * @param color 颜色
     */
    void setBackgroundColor(@ColorInt int color);

    /**
     * 设置背景-直接引用{@link android.view.View}的方法
     * @param drawable {@link Drawable}
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void setBackground(Drawable drawable);

    /**
     * 设置背景-直接引用{@link android.view.View}的方法
     * @param resid 资源id
     */
    void setBackgroundResource(@DrawableRes int resid);


}
