package me.majiajie.pagerbottomtabstrip;



interface TabStripLinstener
{

    /**
     * 基本构建完成时的回调
     * @return
     */
    Controller onFinishBuild();

    /**
     * 当通过点击按钮切换选中项时调用
     */
    void onSelect();

    /**
     * 当通过代码控制选中项时调用
     * @param x x坐标
     * @param y y坐标
     */
    void onSelect(float x, float y);

    /**
     * 当还未执行onMeasure（） 方法，并且外部调用了setSelect()方法设置选中项
     * @param color 设置背景色
     */
    void onNotMeasure(int color);
}

