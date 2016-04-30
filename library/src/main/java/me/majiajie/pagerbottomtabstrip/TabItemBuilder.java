package me.majiajie.pagerbottomtabstrip;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * 开放的导航按钮构建入口
 */
public final class TabItemBuilder
{
    public static TabItemBuild create(@NotNull Context context)
    {
        return new TabItem(context).builder();
    }
}
