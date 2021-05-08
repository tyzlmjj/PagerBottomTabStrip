PagerBottomTabStrip
=====

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.majiajie/pager-bottom-tab-strip/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.majiajie/pager-bottom-tab-strip)
|[使用指南][1]|[DEMO.apk][2]

Android 页面底部和侧边的导航栏。

- 支持[Material Design规范](https://www.google.com/design/spec/components/bottom-navigation.html)的样式
- 可以自定义Item进行扩展
- 自带消息圆点
- 可在构建完成之后动态增加/删除导航按钮、修改图标和文字
- 方便适配常用的ViewPager
- 支持与[Navigation Architecture Component](https://developer.android.com/topic/libraries/architecture/navigation/)配合使用
- 支持[AndroidX](https://developer.android.com/jetpack/androidx/)


## 实现效果图

|![horizontal](/img/demo.png "horizontal")|![vertical](/img/demo8.png "vertical")|
|---|---|
|![Material 1](/img/demo1.gif "Material 1")|![Material 2](/img/demo2.gif "Material 2")|
|![Material 3](/img/demo3.gif "Material 3")|![Material 4](/img/demo4.gif "Material 4")|

## 自定义扩展例子

|Library中已经实现的一个最普通的效果|
|---|
|![PagerBottomTabStrip](/img/demo5.gif "PagerBottomTabStrip")|

|Demo中的例子|Demo中的例子|
|---|---|
|![PagerBottomTabStrip](/img/demo7.png "PagerBottomTabStrip")|![PagerBottomTabStrip](/img/demo6.png "PagerBottomTabStrip")|

## 引入库

```gradle
repositories {
  mavenCentral()
}

dependencies {
  implementation 'me.majiajie:pager-bottom-tab-strip:2.4.0'
}
```

## 联系我

**QQ**: 809402737

## 错误反馈

这个库有BUG？请点这里 [Github Issues](https://github.com/tyzlmjj/PagerBottomTabStrip/issues)

[1]: https://github.com/tyzlmjj/PagerBottomTabStrip/wiki
[2]: https://github.com/tyzlmjj/PagerBottomTabStrip/releases/download/2.4.0/Demo.apk