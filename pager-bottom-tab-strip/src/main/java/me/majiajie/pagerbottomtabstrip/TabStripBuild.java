package me.majiajie.pagerbottomtabstrip;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import org.jetbrains.annotations.NotNull;

/**
 * 构建导航栏
 */
public interface TabStripBuild
{
    /**
     * 完成构建
     * @return {@link Controller}
     */
    Controller build();

    /**
     * 添加一项导航按钮,使用{@link TabItemBuilder}创建
     * @param tabItemBuilder {@link TabItemBuilder}
     * @return {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@NotNull TabItemBuilder tabItemBuilder);

    /**
     * 添加一项导航按钮
     * @param drawable  图标资源
     * @param text      显示文字内容.尽量简短
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@NotNull Drawable drawable, @NotNull String text);

    /**
     * 添加一项导航按钮
     * @param drawable  图标资源
     * @param text      显示文字内容.尽量简短
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@DrawableRes int drawable, @NotNull String text);

    /**
     * 添加一项导航按钮
     * @param drawable      图标资源
     * @param text          显示文字内容.尽量简短
     * @param selectedColor 选中的颜色
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@NotNull Drawable drawable, @NotNull String text, @ColorInt int selectedColor);

    /**
     * 添加一项导航按钮
     * @param drawable      图标资源
     * @param text          显示文字内容.尽量简短
     * @param selectedColor 选中的颜色
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@DrawableRes int drawable, @NotNull String text, @ColorInt int selectedColor);

    /**
     * 添加一项导航按钮
     * @param drawable          图标资源
     * @param selectedDrawable  按钮选中的图标资源，默认和未选中时相同
     * @param text              显示文字内容.尽量简短
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@DrawableRes int drawable, @DrawableRes int selectedDrawable, @NotNull String text);

    /**
     * 添加一项导航按钮
     * @param drawable          图标资源
     * @param selectedDrawable  按钮选中的图标资源，默认和未选中时相同
     * @param text              显示文字内容.尽量简短
     * @param selectedColor     选中的颜色
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@DrawableRes int drawable, @DrawableRes int selectedDrawable, @NotNull String text, @ColorInt int selectedColor);

    /**
     * 添加一项导航按钮
     * @param drawable          图标资源
     * @param selectedDrawable  按钮选中的图标资源，默认和未选中时相同
     * @param text              显示文字内容.尽量简短
     * @param selectedColor     选中的颜色
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@NotNull Drawable drawable, @NotNull Drawable selectedDrawable, @NotNull String text, @ColorInt int selectedColor);

    /**
     * 添加一项导航按钮
     * @param drawable          图标资源
     * @param selectedDrawable  按钮选中的图标资源，默认和未选中时相同
     * @param text              显示文字内容.尽量简短
     * @return  {@link TabStripBuild}
     */
    TabStripBuild addTabItem(@NotNull Drawable drawable, @NotNull Drawable selectedDrawable, @NotNull String text);

    /**
     * 设置圆形消息的背景颜色
     * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabStripBuild}
     */
    TabStripBuild setMessageBackgroundColor(@ColorInt int color);

    /**
     * 这只圆形消息的数字颜色
     * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabStripBuild}
     */
    TabStripBuild setMessageNumberColor(@ColorInt int color);

    /**
     * 设置导航按钮的默认（未选中状态）颜色
     * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return  {@link TabStripBuild}
     */
    TabStripBuild setDefaultColor(@ColorInt int color);

    /**
     * 设置模式。默认文字一直显示，且背景色不变。
     * 可以通过{@link TabLayoutMode}选择模式。
     *
     * <p>例如:</p>
     * {@code TabLayoutMode.HIDE_TEXT}
     *
     * <p>或者多选:</p>
     * {@code TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR}
     *
     * @param tabStripMode {@link TabLayoutMode}
     * @return {@link TabStripBuild}
     */
    TabStripBuild setMode(int tabStripMode);

}
