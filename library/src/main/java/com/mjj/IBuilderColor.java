package com.mjj;

public interface IBuilderColor {
	/**文字大小*/
	public IBuilderColor TabTextSize(int textsize);
	/**内边距，单位为DP*/
	public IBuilderColor TabPadding(int padding);
	/**文字颜色*/
	public IBuilderColor TabTextColor(int textcolor);
	/**选中之后文字的颜色*/
	public IBuilderColor TabClickTextColor(int clicktextcolor);
	/**在COLORMODE模式下图标的颜色*/
	public IBuilderColor TabIconColor(int iconcolor);
	/**在COLORMODE模式下选中后图标的颜色*/
	public IBuilderColor TabClickIconColor(int clickiconcolor);
	/**圆形消息的背景颜色*/
	public IBuilderColor TabMessageBackgroundColor(int backgroundcolor);
	/**圆形消息的数字颜色*/
	public IBuilderColor TabMessageTextColor(int textcolor);
	/**设置图标，非XML文件*/
	public IBuilderColor TabIcon(int[] resids);
	/**设置选中后的图标，非XML文件*/
	public IBuilderColor TabClickIcon(int[] resids);
	/**TAB按钮背景*/
	public IBuilderColor TabBackground(int resid);
	/**完成构建*/
	public void build();

}
