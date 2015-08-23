package com.mjj;


public interface IBuilderDefault {
	/**文字大小*/
	public IBuilderDefault TabTextSize(int textsize);
	/**内边距，单位为DP*/
	public IBuilderDefault TabPadding(int padding);
	/**文字颜色*/
	public IBuilderDefault TabTextColor(int textcolor);
	/**选中之后文字的颜色*/
	public IBuilderDefault TabClickTextColor(int clicktextcolor);
	/**圆形消息的背景颜色*/
	public IBuilderDefault TabMessageBackgroundColor(int backgroundcolor);
	/**圆形消息的数字颜色*/
	public IBuilderDefault TabMessageTextColor(int textcolor);
	/**设置图标，非XML文件*/
	public IBuilderDefault TabIcon(int[] resids);
	/**设置选中后的图标，非XML文件*/
	public IBuilderDefault TabClickIcon(int[] resids);
	/**TAB按钮背景*/
	public IBuilderDefault TabBackground(int resid);
	/**完成构建*/
	public void build();
}
