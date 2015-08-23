package com.mjj;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

public class Tabstrip extends View {

	private float TabTextSize ;

	private String TabText = "";

	private int TabPadding;

	private int TabTextColor = 0xFFAAAAAA;

	private int TabClickTextColor = 0xFF3F9FE0;

	private int TabIconColor = 0xFFAAAAAA;

	private int TabClickIconColor = 0xFF3F9FE0;

	private int TabMessageBackgroundColor = Color.RED;

	private int TabMessageTextColor = Color.WHITE;

	private Bitmap TabIcon;

	private Bitmap TabClickIcon;

	private int TabBackground;

	private float BitmapAlpha = 0f;

	private Rect IconRect;
	private Rect TextBound;

	private Paint TextPaint;

	private int MessageNumber = 0;

	private boolean HaveNews = false;

	public static final int MODE_DEFAULT = 0;

	public static final int MODE_COLOR = 1;

	private int Mode = 0;

	public Tabstrip(Context context) {
		this(context,null);
	}

	public Tabstrip(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public Tabstrip(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public void configure(Context context,int mode){

		Mode = mode;

		if (TabTextSize == 0f) {
			TabTextSize = Utils.dp2px(context,14);
		}

		if (TabIcon == null) {
			TabIcon = BitmapFactory.decodeResource(context.getResources(),android.R.drawable.ic_menu_add);
		}

		if (TabClickIcon == null) {
			TabClickIcon = TabIcon;
		}

		TextBound = new Rect();
		TextPaint = new Paint();
		TextPaint.setTextSize(TabTextSize);
		TextPaint.getTextBounds(TabText, 0, TabText.length(), TextBound);
		TextPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int iconWidth = Math.min(getMeasuredWidth() - TabPadding*2, getMeasuredHeight() - TabPadding*2 - TextBound.height());

		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (TextBound.height() + iconWidth)
				/ 2;
		IconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int alpha = (int) Math.ceil(255 * BitmapAlpha);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

		drawText(canvas,255,TabTextColor);
		drawText(canvas,alpha,TabClickTextColor);

		if (Mode == MODE_COLOR) {
			drawColorBitmap(canvas, 255, TabIconColor, TabIcon);
			drawColorBitmap(canvas,alpha,TabClickIconColor,TabClickIcon);
		}
		if (Mode == MODE_DEFAULT) {
			drawDefaultBitmap(canvas,255,TabIcon);
			drawDefaultBitmap(canvas,alpha, TabClickIcon);
		}

		if (MessageNumber > 0){
			drawMessages(canvas);
		}else{
			if (HaveNews) {
				drawNews(canvas);
			}
		}

	}

	/**
	 * 绘制文本
	 * @param canvas
	 * @param alpha
	 * @param color
	 */
	private void drawText(Canvas canvas, int alpha,int color)
	{
		TextPaint.setColor(color);
		TextPaint.setAlpha(alpha);
		int x = getMeasuredWidth() / 2 - TextBound.width() / 2;
		int y = IconRect.bottom + TextBound.height();
		canvas.drawText(TabText, x, y, TextPaint);

	}

	/**
	 * 画原始图标
	 * @param canvas
	 * @param alpha
	 * @param color
	 * @param bitmap
	 */
	private void drawDefaultBitmap(Canvas canvas, int alpha,Bitmap bitmap)
	{
		Paint paint = new Paint();
		paint.setAlpha(alpha);
		canvas.drawBitmap(bitmap, null, IconRect, paint);
	}

	/**
	 * 画改变颜色的图标
	 * @param canvas
	 * @param alpha
	 * @param color
	 * @param bitmap
	 */
	private void drawColorBitmap(Canvas canvas, int alpha,int color,Bitmap bitmap)
	{
		Bitmap bitmapTem = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvasTem = new Canvas(bitmapTem);
		Paint paint = new Paint();
		canvasTem.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		paint.setColor(color);
		paint.setAlpha(alpha);
		canvasTem.drawRect(IconRect, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvasTem.drawBitmap(bitmap, null, IconRect, paint);
		canvas.drawBitmap(bitmapTem, 0, 0, null);
	}


	/**
	 * 画消息数量
	 * @param canvas
	 */
	private void drawMessages(Canvas canvas){
		//数字画笔内容大小等创建
		Paint textPaint = new Paint();
		Rect textRect = new Rect();
		String text = MessageNumber>99?"99+":MessageNumber+"";
		int textSize = 0;
		if (text.length()==1) {
			textSize = (int) Utils.dp2px(getContext(), 13);
		}else if(text.length()==2){
			textSize = (int) Utils.dp2px(getContext(), 11);
		}else{
			textSize = (int) Utils.dp2px(getContext(), 10);
		}

		textPaint.setColor(TabMessageTextColor);
		textPaint.setFakeBoldText(true);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(textSize);
		textPaint.getTextBounds(text, 0, text.length(), textRect);
		textPaint.setTextAlign(Paint.Align.CENTER);
		Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

		//画圆
		int width = (int) Utils.dp2px(getContext(), 20);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(TabMessageBackgroundColor);

		RectF messageRectF = new RectF(IconRect.right-width, IconRect.top, IconRect.right, IconRect.top+width);
		canvas.drawOval(messageRectF, paint);

		//画数字
		float x = messageRectF.right-messageRectF.width()/2f;
		float y = messageRectF.bottom-messageRectF.height()/2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;
		canvas.drawText(text, x, y,textPaint);

	}

	/**
	 * 小圆点
	 * @param canvas
	 */
	private void drawNews(Canvas canvas){
		//画圆
		int width = (int) Utils.dp2px(getContext(), 10);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(TabMessageBackgroundColor);

		RectF messageRectF = new RectF(IconRect.right-width, IconRect.top, IconRect.right, IconRect.top+width);
		canvas.drawOval(messageRectF, paint);
	}

	/**
	 * 重绘
	 */
	public void invalidateView()
	{
		if (Looper.getMainLooper() == Looper.myLooper())
		{
			invalidate();
		} else
		{
			postInvalidate();
		}
	}

	public float getTabTextSize() {
		return TabTextSize;
	}

	public void setTabTextSize(float tabTextSize) {
		TabTextSize = tabTextSize;
	}

	public float getTabPadding() {
		return TabPadding;
	}

	public void setTabPadding(int tabPadding) {
		TabPadding = tabPadding;
	}

	public String getTabText() {
		return TabText;
	}

	public void setTabText(String tabText) {
		TabText = tabText;
	}

	public int getTabTextColor() {
		return TabTextColor;
	}

	public void setTabTextColor(int tabTextColor) {
		TabTextColor = tabTextColor;
	}

	public int getTabClickTextColor() {
		return TabClickTextColor;
	}

	public void setTabClickTextColor(int tabClickTextColor) {
		TabClickTextColor = tabClickTextColor;
	}

	public int getTabIconColor() {
		return TabIconColor;
	}

	public void setTabIconColor(int tabIconColor) {
		TabIconColor = tabIconColor;
	}

	public int getTabClickIconColor() {
		return TabClickIconColor;
	}

	public void setTabClickIconColor(int tabClickIconColor) {
		TabClickIconColor = tabClickIconColor;
	}

	public int getTabMessageBackgroundColor() {
		return TabMessageBackgroundColor;
	}

	public void setTabMessageBackgroundColor(int tabMessageBackgroundColor) {
		TabMessageBackgroundColor = tabMessageBackgroundColor;
	}

	public int getTabMessageTextColor() {
		return TabMessageTextColor;
	}

	public void setTabMessageTextColor(int tabMessageTextColor) {
		TabMessageTextColor = tabMessageTextColor;
	}

	public Bitmap getTabIcon() {
		return TabIcon;
	}

	public void setTabIcon(Bitmap tabIcon) {
		TabIcon = tabIcon;
	}

	public Bitmap getTabClickIcon() {
		return TabClickIcon;
	}

	public void setTabClickIcon(Bitmap tabClickIcon) {
		TabClickIcon = tabClickIcon;
	}

	public int getTabBackground() {
		return TabBackground;
	}

	public void setTabBackground(int tabBackground) {
		TabBackground = tabBackground;
		this.setBackgroundResource(TabBackground);
	}

	public float getBitmapAlpha() {
		return BitmapAlpha;
	}

	public void setBitmapAlpha(float bitmapAlpha) {
		BitmapAlpha = bitmapAlpha;
		invalidateView();
	}

	public int getMessageNumber() {
		return MessageNumber;
	}

	public void setMessageNumber(int messageNumber) {
		MessageNumber = messageNumber;
		invalidateView();
	}

	public void addMessageNumber(int number){
		MessageNumber += number;
		if (MessageNumber<0) {
			MessageNumber = 0;
		}
		invalidateView();
	}

	public void setNews(boolean news){
		HaveNews = news;
		invalidateView();
	}

	public boolean getNews(){
		return HaveNews;
	}

}
