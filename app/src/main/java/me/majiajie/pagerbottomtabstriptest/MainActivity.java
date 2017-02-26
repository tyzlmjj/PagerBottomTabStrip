package me.majiajie.pagerbottomtabstriptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;


public class MainActivity extends AppCompatActivity {
    //    int[] testColors = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
//    int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    int[] testColors = {0xFF00796B, 0xFF5B4947, 0xFF607D8B, 0xFFF57C00, 0xFFF57C00};



    List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageBottomTabLayout pageBottomTabLayout = (PageBottomTabLayout) findViewById(R.id.tab);

        pageBottomTabLayout.material()
                .addItem(android.R.drawable.ic_menu_compass, "位置", testColors[0])
                .addItem(android.R.drawable.ic_menu_send, "信息", testColors[1])
                .addItem(android.R.drawable.ic_menu_search, "搜索", testColors[2])
                .addItem(android.R.drawable.ic_menu_help, "帮助", testColors[3])
                .build();
    }

}
