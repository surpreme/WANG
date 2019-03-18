**

## Android 模板


    王幼虎是我自己编写的一个app 已经编写一个月了 功能大部分已实现 后续会继续更新 这里我来开源我的项目供大家参考 版权归博主所有
   

>首先先来看我的作品展示 版本为@1.0.1



![在这里插入图片描述](https://img-blog.csdnimg.cn/2019031715012794.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150143309.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150158754.jpg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150219716.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150236871.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150250290.jpg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150303823.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150314777.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150327442.jpg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150336445.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150350105.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317150359192.jpg)



> 这里展示几张示例图片 下面再详细讲解


*Fragment可以参考我以前写的文章 当作模板*

测面是用的glide加载圆形图片 这里用了glide第三方裁剪库
import用的是相机 系统相机和自定义相机并裁剪
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317161049505.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317161511205.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317161522800.jpg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317161535434.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317161556662.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317161612316.jpg)



顶部+是用的卡牌布局+popwindows
这里实现了扫一扫 用了zxing和知乎开源的相册选择器 这里我添加了手动打开闪光灯 
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019031716073536.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317160744362.jpg)



 - 主页
 
 
 
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019031715201119.jpg)
 >1顶部bar使用了android studio给的模板

>2搜索框实现了自定义 这里选择跳转到下一个activity


*搜索框自定义 当输入文字显示控件 这里无服务器回复数据 采用通讯录查询*

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317151656246.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317151725369.jpg)
>3轮播图用的banner+glide

>4这里用的是滑动布局 将textView写进去

>5这里相信大家都会

>6textview动画上下切换文字

>7这里使用了SmartRefreshLayout下拉刷新动画 

这里分功能又写了 本地音乐播放器 图灵机器人高德地图 使用系统mediaplyer+surfaceView播放视频 以及短信验证码和拨打电话的功能

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317155000110.jpg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317155021779.jpg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317155139459.jpg)


这是动态的页面
 

 *1. 关注是用的listView
 2. 推荐是模仿的qq列表点击展开收起 
 3.  小视频写的是recy 变换布局 listView gridView 瀑布流 还有添加删除项以及动画*

 

![在这里插入图片描述](https://img-blog.csdnimg.cn/2019031715574211.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317155754642.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317155815736.jpg)


这是泡泡的页面


![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317160223250.jpg)


用户页面用的是greendao  ORM
顶部通知栏是用的8.0通知栏 有消息提示还有订阅消息两种不同的通知栏通知形式 这里参考郭霖大神的论文
离线中心用的是okhttp断点下载 




![在这里插入图片描述](https://img-blog.csdnimg.cn/2019031716215189.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317162203215.jpg)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190317162214820.jpg)



>版本更新到@1.0.2

添加了调整系统音量 以及textView的扩展的功能这些常见功能 后续还会更新 敬请期待 
