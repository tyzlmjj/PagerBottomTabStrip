package me.majiajie.pagerbottomtabstrip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends AppCompatActivity
{
//    private int[] testColors = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
//    private int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    private int[] testColors = {0xFF00796B,0xFF5B4947,0xFF607D8B,0xFFF57C00};
    Controller controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        //用TabItemBuilder构建一个导航按钮
        TabItem tabItem = TabItemBuilder.create(this)
                .setDefaultIcon(android.R.drawable.ic_menu_send)
                .setText("信息")
                .setSelectedColor(testColors[0])
                .setTag("A")
                .build();

        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(tabItem)
                .addTabItem(android.R.drawable.ic_menu_compass, "位置",testColors[1])
                .addTabItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
                .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
//                .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
//                .setMode(TabLayoutMode.HIDE_TEXT)
                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();
        controller.setSelect(2);
        controller.setMessageNumber("A",1);
        controller.setDisplayOval(1,true);

        controller.addTabItemClickListener(new OnTabItemSelectListener() {
            @Override
            public void onSelected(int index, Object tag)
            {
                Log.i("asd","onSelected:"+index+"   TAG: "+tag.toString());
            }

            @Override
            public void onRepeatClick(int index, Object tag) {
                Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
            }
        });

    }


    public void click(View v){
        EditText editText = (EditText) findViewById(R.id.editText);
        int number = Integer.parseInt(editText.getText().toString());
        controller.setSelect(number);
    }

}
