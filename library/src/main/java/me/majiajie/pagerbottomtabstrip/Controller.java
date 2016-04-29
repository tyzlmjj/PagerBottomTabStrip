package me.majiajie.pagerbottomtabstrip;


import me.majiajie.pagerbottomtabstrip.i.OnTabItemClickListener;

/**
 * 导航栏控制
 */
public interface Controller
{
    /**
     * 导航栏按钮点击监听
     * @param listener {@link OnTabItemClickListener}
     */
    void addTabItemClickListener(OnTabItemClickListener listener);

    /**
     * 设置导航按钮上显示的圆形消息数字，通过顺序索引。
     * @param index
     * @param number    消息数字
     */
    void setMessageNumber(int index ,int number);

    /**
     * 设置导航按钮上显示的圆形消息数字，通过TAG索引。
     * @param tag       默认是输入的文字，可在构建时设置
     * @param number    消息数字
     */
    void setMessageNumber(Object tag ,int number);

    /**
     * 设置选中项
     * @param index 顺序索引
     */
    void setSelected(int index);


}
