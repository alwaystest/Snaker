



#开 发 文 档

#####开发人员：



##本游戏（贪吃蛇）基于JAVA开发。

***
###Snaker -07:
模仿蛇的运动，现在利用draw时偏移，而不是直接偏移Node的位置
待完成，纠正蛇拐弯体位不正,此时可以判断蛇的重叠
LinkedList<Node>参考  泛型类http://bbs.bccn.net/thread-56010-1-1.html  去掉“<Node>”会报warning 对addFirst()的调用未经过检查
蛇身体用linkedlist存储，将蛇分为一个一个的方块，每个方块有位置属性，repaint的时候根据位置画出方块，组成蛇。
linkedlist相当于数据结构的链表，容易增加和删除
ListIterator是对ListLink的迭代器，详见api文档的Iterator，类似数组的指针，这个可以后移next()，也可以前移previous()，直接用Iterator也可以，但不能前移
	
	
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











