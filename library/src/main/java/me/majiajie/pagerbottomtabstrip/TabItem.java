package me.majiajie.pagerbottomtabstrip;


import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 底部导航的按钮项
 */
public class TabItem extends View
{

    public TabItem(Context context) {
        super(context);
        init(context);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
    }





}
