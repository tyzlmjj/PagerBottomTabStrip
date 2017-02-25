package me.majiajie.pagerbottomtabstrip.listener;

/**
 * 导航栏按钮选中事件监听
 */
public interface OnTabItemSelectedListener
{
    /**
     * 选中导航栏的某一项
     * @param index 索引导航按钮，按添加顺序排序
     * @param old   前一个选中项，如果没有就等于-1
     */
    void onSelected(int index, int old);

    /**
     * 重复选中
     * @param index 索引导航按钮，按添加顺序排序
     */
    void onRepeat(int index);
}
