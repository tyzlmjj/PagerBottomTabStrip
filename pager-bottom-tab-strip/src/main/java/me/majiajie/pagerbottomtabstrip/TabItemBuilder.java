package me.majiajie.pagerbottomtabstrip;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * 开放的导航按钮构建入口
 */
public class TabItemBuilder
{
    private TabItem mTabItem;

    private Context mContext;

    public TabItemBuilder(@NotNull Context context){
        mContext = context;
    }

    public TabItemBuild create()
    {
        mTabItem = new TabItem(mContext);
        return mTabItem.builder(this);
    }

    protected TabItem getTabItem()
    {
        return mTabItem;
    }
}
