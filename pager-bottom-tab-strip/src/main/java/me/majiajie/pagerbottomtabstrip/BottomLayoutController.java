package me.majiajie.pagerbottomtabstrip;


import androidx.viewpager.widget.ViewPager;

interface BottomLayoutController {

    /**
     * 方便适配ViewPager页面切换<p>
     * 注意：ViewPager页面数量必须等于导航栏的Item数量
     *
     * @param viewPager {@link ViewPager}
     */
    void setupWithViewPager(ViewPager viewPager);

    /**
     * 向下移动隐藏导航栏
     */
    void hideBottomLayout();

    /**
     * 向上移动显示导航栏
     */
    void showBottomLayout();
}
