package me.majiajie.pagerbottomtabstrip;


/**
 * <p>模式选择。</p>
 * <p>采用组合的形式，比如要两种效果可以这样做：</p>
 * TabStripMode.HIDE_TEXT | TabStripMode.MULTIPLE_COLOR
 */
public final class TabStripMode
{
    /**
     * 隐藏文字内容，只在选中时显示
     */
    public static final int HIDE_TEXT = 0XF;

    /**
     * 开启导航栏背景变换。点击不同项切换不同颜色
     */
    public static final int MULTIPLE_COLOR = 0XF0;

}
