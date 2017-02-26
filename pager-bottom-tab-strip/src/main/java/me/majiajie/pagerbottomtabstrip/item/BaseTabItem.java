package me.majiajie.pagerbottomtabstrip.item;


import android.content.Context;
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
     * 设置消息数字。注意：数字需要大于0才会显示
     */
    abstract public void setMessageNumber(int number);

    /**
     * 设置是否显示无数字的小圆点。注意：如果消息数字不为0，优先显示带数字的圆
     */
    abstract public void setHasMessage(boolean hasMessage);

    /**
     * 获取标题文字
     */
    abstract public String getTitle();

}
