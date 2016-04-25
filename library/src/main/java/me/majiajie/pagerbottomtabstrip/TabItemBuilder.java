package me.majiajie.pagerbottomtabstrip;


import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * 构建底部导航栏的一项按钮
 */
public interface TabItemBuilder
{

    /**
     * 完成构建
     * @return  {@link TabItem TabItem}
     */
    TabItem build();

    /**
     * 设置文字内容。尽量简短
     * @return  {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setText(@NonNull String text);

    /**
     * 设置圆形消息的背景颜色
     * @param color  16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setMessageBackgroundColor(@ColorInt int color);

    /**
     * 设置圆形消息的数字颜色
     * @param color  16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setMessageTextColor(@ColorInt int color);

    /**
     * 设置被选中后的图标
     * @param drawable  Drawable资源
     * @return {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setSelectedIcon(@DrawableRes int drawable);

    /**
     * 设置被选中后的图标
     * @param drawable  Drawable
     * @return  {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setSelectedIcon(@NonNull Drawable drawable);

    /**
     * 设置默认（未被选中）的图标
     * @param drawable  Drawable资源
     * @return  {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setDefaultIcon(@DrawableRes int drawable);

    /**
     * 设置默认（未被选中）的图标
     * @param drawable  Drawable
     * @return  {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setDefaultIcon(@NonNull Drawable drawable);

    /**
     * 设置被选中之后的颜色
     * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setSelectedColor(@ColorInt int color);

    /**
     * 设置默认（未被选中）颜色
     * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setDefaultColor(@ColorInt int color);

    /**
     * 设置被选中之后的导航栏背景颜色。
     * 注意：只有设置了模式{@code TabStripMode.MULTIPLE_COLOR}之后生效
     * @param color 16进制整形表示的颜色，例如红色：0xFFFF0000
     * @return {@link TabItemBuilder TabItemBuilder}
     */
    TabItemBuilder setSelectedBackgroundColor(@ColorInt int color);

}
