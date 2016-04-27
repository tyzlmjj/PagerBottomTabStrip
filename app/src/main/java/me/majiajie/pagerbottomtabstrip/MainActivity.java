package me.majiajie.pagerbottomtabstrip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout);
        TabItem tabItem =  new TabItem(this).builder()
                .setText("看什么看")
                .setDefaultIcon(android.R.drawable.ic_menu_save)
                .build();

        linearLayout.addView(tabItem);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "看什么看"))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "图书"))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "音乐"))
//                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "Movies & TV"))
//                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_save, "Games"))
                .initialise();
    }

}
