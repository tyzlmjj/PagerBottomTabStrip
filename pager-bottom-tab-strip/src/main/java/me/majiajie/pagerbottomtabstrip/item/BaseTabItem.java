package me.majiajie.pagerbottomtabstrip.item;


/**
 * Item
 */
public interface BaseTabItem
{
    void setTitle(String title);

    /**
     * 设置选中状态
     */
    void setSelected(boolean selected);

    /**
     * 设置消息数字。注意：数字需要大于0才会显示
     */
    void setMessageNumber(int number);

    /**
     * 设置是否显示无数字的小圆点。注意：如果消息数字不为0，优先显示带数字的圆
     */
    void setDisplayOval(boolean hasMessage);

    /**
     * 设置TAG
     */
    void setItemTag(Object tag);
}
