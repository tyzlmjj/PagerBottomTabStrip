package me.majiajie.pagerbottomtabstrip.I;

/**
 * 导航栏按钮点击监听
 */
public interface OnTabItemClickListener
{
    /**
     * 未选中状态下点击
     * @param index 索引导航按钮，按添加顺序排序
     * @param tag   默认是导航按钮的文字内容
     */
    void onUnselected(int index,Object tag);

    /**
     * 已经选中状态下点击
     * @param index 索引导航按钮，按添加顺序排序
     * @param tag   默认是导航按钮的文字内容
     */
    void onSelected(int index,Object tag);
}
