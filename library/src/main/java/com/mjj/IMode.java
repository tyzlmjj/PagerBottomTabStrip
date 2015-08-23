package com.mjj;


public interface IMode {
	/**默认模式：显示原始的图标，不做颜色上的修改*/
	public IBuilderDefault DefaultMode();
	/**纯色模式：改变图标的颜色为设置的颜色（纯色的）*/
	public IBuilderColor ColorMode();


}
