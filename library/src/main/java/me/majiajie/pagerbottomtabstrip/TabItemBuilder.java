package me.majiajie.pagerbottomtabstrip;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * 开放的导航按钮构建入口
 */
public final class TabItemBuilder
{
    public static TabItemBuild create(@NonNull Context context, @DrawableRes int drawable,@NonNull String text)
    {
        return new TabItem(context).builder().setText(text).setDefaultIcon(drawable);
    }

    public static TabItemBuild create(@NonNull Context context, @NonNull Drawable drawable,@NonNull String text)
    {
        return new TabItem(context).builder().setText(text).setDefaultIcon(drawable);
    }
}
