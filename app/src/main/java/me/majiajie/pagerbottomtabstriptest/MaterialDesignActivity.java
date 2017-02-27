package me.majiajie.pagerbottomtabstriptest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.majiajie.pagerbottomtabstriptest.other.MyViewPagerAdapter;

import static me.majiajie.pagerbottomtabstriptest.R.id.tab;

public class MaterialDesignActivity extends AppCompatActivity {

    int[] testColors = {0xFF00796B, 0xFF5B4947, 0xFF607D8B, 0xFFF57C00, 0xFFF57C00};
//    int[] testColors = {0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        PageBottomTabLayout pageBottomTabLayout = (PageBottomTabLayout) findViewById(tab);

        NavigationController navigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.ic_restore_gray_24dp,"位置",testColors[0])
                .addItem(android.R.drawable.ic_menu_send, "信息",testColors[1])
                .addItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
                .addItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR)//这里可以设置样式模式
                .build();


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),navigationController.getItemCount()));

        //自动适配ViewPager页面切换
        pageBottomTabLayout.setupWithViewPager(viewPager);

        //也可以设置Item选中事件的监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("asd","selected: " + index + " old: " + old);
            }

            @Override
            public void onRepeat(int index) {
                Log.i("asd","onRepeat selected: " + index);
            }
        });

        //设置消息圆点
//        navigationController.setMessageNumber(0,8);
//        navigationController.setHasMessage(1,true);
    }
}
