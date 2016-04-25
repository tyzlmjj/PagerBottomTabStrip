package me.majiajie.pagerbottomtabstrip;

/**
 * 构建导航栏
 */
public interface TabStripBuilder
{
    /**
     * 完成构建
     * @return {@link PagerBottomTabStrip}
     */
    PagerBottomTabStrip build();

    /**
     * 增加一个导航按钮项
     * @param tabItem {@link TabItem}
     * @return {@link TabStripBuilder}
     */
    TabStripBuilder addTabItem(TabItem tabItem);


    /**
     * 设置模式。默认文字一直显示，且背景色不变。
     * 可以通过{@link TabStripMode}选择模式。
     *
     * <p>例如:</p>
     * {@code TabStripMode.HIDE_TEXT}
     *
     * <p>或者多选:</p>
     * {@code TabStripMode.HIDE_TEXT | TabStripMode.MULTIPLE_COLOR}
     *
     * @param tabStripMode {@link TabStripMode}
     * @return {@link TabStripBuilder}
     */
    TabStripBuilder setMode(int tabStripMode);





}
