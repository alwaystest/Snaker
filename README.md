



#开 发 文 档

- 说明:P键暂停,C-s 重新开始

#####开发人员：



##本游戏（贪吃蛇）基于JAVA开发。

***
- **待完成**
    记分板与界面结合

***
###Snaker -08:
- 0527
1.抽取共性，将imgpanel类提取为一个 
2.修改add组件的位置，使其更合理
3.提取键盘监听器，放到同一个地方，提高代码易读性。
- 0527 
记录:不能随便修改组件的paint() 会导致组件被覆盖 向窗口添加背景图片的时候可以重写paintComponent(Graphics g) 不会覆盖组件
paint方法实际上将绘制工作委托给三个受保护的方法：paintComponent、paintBorder 和 paintChildren。按列出的顺序调用这些方法，以确保子组件出现在组件本身的顶部
- 0526
完成数据库记分板。[理解java数据库的使用](http://www.cnblogs.com/hongten/archive/2011/03/29/1998311.html)。采用sqlite数据库 sqlite不支持if else 语句，数据库中time用来区分数据，删除的时候不会把分值相同的数据删掉，
  待完善方法：sqlite.in() 的时候判断分数是否小于最小值，不会出现相同分数多个记录
- 0524
界面完善基本完成，细节待修改
- 0519
完成重新开始功能,System.gc();通知JVM回收内存,具体回收由JVM确定.防止重新开始次数过多内存泄露神马的问题

- 0519
完成游戏暂停功能.
计分板,界面按钮正在开发,下一步做重新开始的函数
做线程暂停耗了好大时间,写出[我的理解](synchronized.md),供以后回忆或者学习代码,如有错误,请指正.

###Snaker -07:

- 0517 修正蛇与食物的位置,待细化

- 0516 感觉模仿蛇的动作太难，考虑先完成基本行为，已完成吃食物长大 实现于雏形分支的最新版本，不要下载错
- LinkedList<Node>参考  [泛型类](http://bbs.bccn.net/thread-56010-1-1.html)
- 去掉“<Node>”会报warning 对addFirst()的调用未经过检查
- 蛇身体用linkedlist存储，将蛇分为一个一个的方块，每个方块有位置属性，repaint的时候根据位置画出方块，组成蛇。
- linkedlist相当于数据结构的链表，容易增加和删除
- ListIterator是对ListLink的迭代器，详见api文档的Iterator，类似数组的指针，这个可以后移next()，也可以前移previous()，直接用Iterator也可以，但不能前移
	
	
###SnakeClient-06版本：
添加键盘监听器类KeyMonitor，
添加键盘监听器，针对不同的键改变方块的位置，与重画线程结合产生不同方向运动。 
建立Snake类，为Snake类添加成员变量x y，添加draw方法，
使Snake类独立控制自己的画法，添加Snake类处理按键的方法，                      
根据Snake类修改SnakeClient类。

###SnakeClient-05版本：
代码重构，将以后可能需要多处改变的量定义为常量。

###SnakeClient-04版本：
让贪吃蛇运动起来。
	
###SnakeClient-03版本：
画出代表贪吃蛇的实心矩形。
	
###SnakeClient-02版本：
添加关闭窗口的事件处理，不允许窗口的大小改动。

###SnakeClient-01版本：
生成一个800×600的窗体。
