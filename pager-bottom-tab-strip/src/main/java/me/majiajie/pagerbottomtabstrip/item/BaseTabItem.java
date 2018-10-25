package me.majiajie.pagerbottomtabstrip.item;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 所有自定义Item都必须继承此类
 */
public abstract class BaseTabItem extends FrameLayout {

    public BaseTabItem(@NonNull Context context) {
        super(context);
    }

    public BaseTabItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTabItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置选中状态
     */
    abstract public void setChecked(boolean checked);

    /**
     * 设置消息数字。注意：一般数字需要大于0才会显示
     */
    abstract public void setMessageNumber(int number);

    /**
     * 设置是否显示无数字的小圆点。注意：如果消息数字不为0，优先显示带数字的圆
     */
    abstract public void setHasMessage(boolean hasMessage);

    /**
     * 设置标题
     */
    abstract public void setTitle(String title);

    /**
     * 设置未选中状态下的图标
     */
    abstract public void setDefaultDrawable(Drawable drawable);

    /**
     * 设置选中状态下的图标
     */
    abstract public void setSelectedDrawable(Drawable drawable);

    /**
     * 获取标题文字
     */
    abstract public String getTitle();

    /**
     * 已选中的状态下再次点击时触发
     */
    public void onRepeat() {}


}
