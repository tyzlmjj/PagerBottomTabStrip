# PagerBottomTabStrip
帮助开发者快速创建与ViewPager配合使用的底部导航栏。带有消息数量显示、小红点。
![PagerBottomTabStrip](https://github.com/tyzlmjj/IMAGES/blob/master/PagerBottomTabStrip.gif?raw=true)

# 使用方法
1.在XML文件中添加布局，一般设置在底部。
```
 <com.mjj.PagerBottomTabStrip
        android:id="@+id/tab"
        android:layout_height="56dp"
        android:layout_width="match_parent"
        android:background="#FFFFFF"
        android:layout_alignParentBottom="true"
        />
```

2.在Activity的onCreate方法或Fragement的onCreateView方法，绑定控件到ViewPager。

需要注意的：
	
- ViewPager需要在此之前就初始化
- ViewPager的适配器中的 getPageTitle()方法需要重写，以便获取标题文字

```
PagerBottomTabStrip pagerBottomTabStrip = (PagerBottomTabStrip) findViewById(R.id.tab);	
pagerBottomTabStrip.builder(mViewPager)
	.ColorMode()
	.TabPadding(5)
	.build();
```
3.如果你想监听ViewPager的滑动变化事件，应该设置在此控件上，不要直接在ViewPager上设置
```
mPagerBottomTabStrip.addOnPageChangeListener(Listener);
```
# 可自定义控制的所有方法

开始构建
- `builder(ViewPager)`	从这里开始构建，放入一个初始化完毕的ViewPager

模式选择
- `DefaultMode()`	默认模式：显示原始的图标，不做颜色上的修改
- `ColorMode()`	纯色模式：改变图标的颜色为设置的颜色（纯色的）

属性设置
- `TabTextSize(int textsize)`文字大小
- `TabPadding(int padding)`		内边距，单位为DP
- `TabTextColor(int textcolor)`		文字颜色
- `TabClickTextColor(int clicktextcolor)` 选中之后文字的颜色
- `TabIconColor(int iconcolor)` 在COLORMODE模式下图标的颜色
- `TabClickIconColor(int clickiconcolor)` 在COLORMODE模式下选中后图标的颜色
- `TabMessageBackgroundColor(int backgroundcolor)`	圆形消息的背景颜色
- `TabMessageTextColor(int textcolor)`	圆形消息的数字颜色
- `TabIcon(int[] resids)`	设置图标，非XML文件
- `TabClickIcon(int[] resids)`	设置选中后的图标，非XML文件
- `TabBackground(int resid)`	TAB按钮背景

完成构建
- `build()`

其他方法
- `setMessageNumber(int postion,int messageNumber)` 

设置按钮显示的消息数量

postion 第几个按钮,从0开始。

messageNumber 需要显示的消息数量
- `addMessageNumber(int postion,int messageNumber)`

增减按钮显示的消息数量

postion 第几个按钮,从0开始。

messageNumber 需要增减的消息数量
- `setNews(int postion,Boolean news)`

控制无数字小圆点的显示与消失

news false消失,true显示。

postion 第几个按钮,从0开始。
- `setFocus(int number)`

手动设置选中的Tab项（一般情况下不需要手动设置），从0开始	
- `addOnPageChangeListener(OnPageChangeListener listener)`

增加ViewPager滑动监听
	
