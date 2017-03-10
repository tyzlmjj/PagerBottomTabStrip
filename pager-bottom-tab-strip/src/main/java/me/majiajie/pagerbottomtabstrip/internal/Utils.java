package me.majiajie.pagerbottomtabstrip.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;


public class Utils {

	/**
	 * 获取自定义属性的资源ID
	 * @param context	上下文
	 * @param attrRes	自定义属性
     * @return	resourceId
     */
	public static int getResourceId(Context context, int attrRes)
	{
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(attrRes, typedValue, true);
		return typedValue.resourceId;
	}

	/**
	 * 获取colorPrimary的颜色,需要V7包的支持
	 * @param context 上下文
	 * @return 0xAARRGGBB
	 */
	public static int getColorPrimary(Context context){
		Resources res = context.getResources();
		int attrRes =res.getIdentifier("colorPrimary","attr",context.getPackageName());
		if(attrRes == 0){
			return 0xFF009688;
		}
		return ContextCompat.getColor(context,getResourceId(context,attrRes));
	}

	public static Drawable tint(Drawable drawable, int color)
	{
		final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
		wrappedDrawable.mutate();
		DrawableCompat.setTint(wrappedDrawable, color);
		return wrappedDrawable;
	}
}
