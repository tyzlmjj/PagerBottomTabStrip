package me.majiajie.pagerbottomtabstrip.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


public class RoundMessageView extends View
{
    public RoundMessageView(Context context) {
        super(context);
    }

    public RoundMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public RoundMessageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHasMessage(boolean hasMessage)
    {

    }


}
