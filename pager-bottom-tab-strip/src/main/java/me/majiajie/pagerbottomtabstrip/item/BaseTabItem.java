package me.majiajie.pagerbottomtabstrip.item;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 所有自定义Item都必须继承此类
 */
public abstract class BaseTabItem extends FrameLayout
{
    public BaseTabItem(Context context) {
        super(context);
    }

    public BaseTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置选中状态
     */
    abstract public void setChecked(boolean checked);

    /**
     * 设置标题
     */
    abstract public void setTitle(String title);

    /**
     * 设置默认图标
     */
    abstract public void setIcon(Drawable drawable);

    /**
     * 设置选中时的图标，如果为null,则使用默认的图标
     */
    abstract public void setCheckedIcon(Drawable drawable);

    /**
     * 设置默认颜色（未选中状态）
     */
    abstract public void setColor(int color);

    /**
     * 设置选中时的颜色
     */
    abstract public void setCheckedColor(int color);

    /**
     * 设置消息数字。注意：数字需要大于0才会显示
     */
    abstract public void setMessageNumber(int number);

    /**
     * 设置是否显示无数字的小圆点。注意：如果消息数字不为0，优先显示带数字的圆
     */
    abstract public void setHasMessage(boolean hasMessage);

}
