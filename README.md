



#开 发 文 档

#####开发人员：



##本游戏（贪吃蛇）基于JAVA开发。

***
###SnakeClient-01版本：
	生成一个800×600的窗体。

###SnakeClient-02版本：
	添加关闭窗口的事件处理，不允许窗口的大小改动。

###SnakeClient-03版本：
	画出代表贪吃蛇的实心矩形。

###SnakeClient-04版本：
	让贪吃蛇运动起来。

###SnakeClient-05版本：
	代码重构，将以后可能需要多处改变的量定义为常量。

###SnakeClient-06版本：
	添加键盘监听器类KeyMonitor，
	添加键盘监听器，针对不同的键改变方块的位置，与重画线程结合产生不同方向运动。 
	建立Snake类，为Snake类添加成员变量x y，添加draw方法，                        使Snake类独立控制自己的画法，添加Snake类处理按键的方法，                      
	根据Snake类修改SnakeClient类。



