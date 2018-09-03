package me.majiajie.pagerbottomtabstrip;


import android.graphics.drawable.Drawable;

import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;

public interface ItemController {

    /**
     * 设置选中项
     *
     * @param index 顺序索引
     */
    void setSelect(int index);

    /**
     * 设置选中项，并可以控制是否回调监听事件
     *
     * @param index    顺序索引
     * @param listener true:假如存在监听事件{@link OnTabItemSelectedListener}，就会调用相关的回调方法。false:不会触发监听事件
     */
    void setSelect(int index, boolean listener);

    /**
     * 设置导航按钮上显示的圆形消息数字，通过顺序索引。
     *
     * @param index  顺序索引
     * @param number 消息数字
     */
    void setMessageNumber(int index, int number);

    /**
     * 设置显示无数字的消息小原点
     *
     * @param index      顺序索引
     * @param hasMessage true显示
     */
    void setHasMessage(int index, boolean hasMessage);

    /**
     * 导航栏按钮点击监听
     *
     * @param listener {@link OnTabItemSelectedListener}
     */
    void addTabItemSelectedListener(OnTabItemSelectedListener listener);

    /**
     * 导航栏按钮点击监听(只有选中事件)
     *
     * @param listener {@link SimpleTabItemSelectedListener}
     */
    void addSimpleTabItemSelectedListener(SimpleTabItemSelectedListener listener);

    /**
     * 设置标题
     *
     * @param index 顺序索引
     * @param title 标题文字
     */
    void setTitle(int index, String title);

    /**
     * 设置未选中状态下的图标
     *
     * @param index    顺序索引
     * @param drawable 图标资源
     */
    void setDefaultDrawable(int index, Drawable drawable);

    /**
     * 设置选中状态下的图标
     *
     * @param index    顺序索引
     * @param drawable 图标资源
     */
    void setSelectedDrawable(int index, Drawable drawable);

    /**
     * 获取当前选中项索引
     *
     * @return 索引
     */
    int getSelected();

    /**
     * 获取导航按钮总数
     *
     * @return 总数
     */
    int getItemCount();

    /**
     * 获取导航按钮文字
     *
     * @param index 顺序索引
     * @return 文字
     */
    String getItemTitle(int index);

    /**
     * 移除指定的导航项.需要注意,不能移除当前选中的导航项
     *
     * @return 移除是否成功
     */
    boolean removeItem(int index);

    /**
     * 添加一个材料设计样式的TabItem.注意,只对<code>material()</code>构建的导航栏有效
     *
     * @param index            顺序索引
     * @param defaultDrawable  未选中状态的图标资源
     * @param selectedDrawable 选中状态的图标资源
     * @param title            标题
     * @param selectedColor    选中状态的颜色
     */
    void addMaterialItem(int index, Drawable defaultDrawable, Drawable selectedDrawable, String title, int selectedColor);

    /**
     * 添加一个自定义样式的TabItem.注意,只对<code>custom()</code>构建的导航栏有效
     *
     * @param index 顺序索引
     * @param item  自定义的Item
     */
    void addCustomItem(int index, BaseTabItem item);
}
