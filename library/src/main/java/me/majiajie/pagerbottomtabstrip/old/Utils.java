package me.majiajie.pagerbottomtabstrip.old;


import android.content.Context;
import android.util.TypedValue;

public class Utils {

    public static float dp2px(Context context, float dp)
    {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }
}
