package me.majiajie.pagerbottomtabstrip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import me.majiajie.pagerbottomtabstrip.i.TabStripLinstener;

public class MainActivity extends AppCompatActivity
{
    private int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "看什么看").setActiveColor(0xFFFF0000))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "图书").setActiveColor(0xFF00FF00))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "音乐").setActiveColor(0xFF0000FF))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "通知").setActiveColor(0xFFFF0000))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "个人").setActiveColor(0xFF00FF00))
                .initialise();

        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        pagerBottomTabLayout.builder()
                .addTabItem(android.R.drawable.ic_menu_save, "看什么看",testColors[0])
                .addTabItem(android.R.drawable.ic_menu_save, "图书",testColors[1])
                .addTabItem(android.R.drawable.ic_menu_save, "音乐",testColors[2])
                .addTabItem(android.R.drawable.ic_menu_save, "通知",testColors[3])
                .addTabItem(android.R.drawable.ic_menu_save, "个人",testColors[4])
                .setMode(TabStripMode.HIDE_TEXT|TabStripMode.MULTIPLE_COLOR)
                .build();
    }

}
