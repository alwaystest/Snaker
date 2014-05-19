#利用synchronized获取监视器实现暂停,恢复线程的功能

###原理

java提供关键字**synchronized**来实现多个线程的同步,相当于给方法或对象设置了一次只允许一个人访问的限制,好多博客的说法是获取目标的锁(大概是这个意思)

synchronized(Obj) Obj是指Object对象,java.lang.Object是一个超类,网上是这么说的
> [Object类是所有Java类的祖先。每个类都使用 Object 作为超类](http://lavasoft.blog.51cto.com/62575/15456/w)

synchronized(obj)用于获取obj的锁,只有获取到目标的锁,才能对目标所在的线程执行wait(),notify()

要唤醒wait()的线程,用到notify(),要求两者之前获取锁的对象相同,
eg:
	Object ob=new Object();
	synchronized(ob){
		try{
			wait();
		}
		catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}

	synchronized(ob){
		notify();
	}

这样才能把ob对应的线程暂停再启动.
这只是个例子,不要太计较细节,能表达意思就行了

###贪吃蛇内的实现

目前游戏内有两个线程,一个主线程给出窗口,另一个线程负责绘图,更新屏幕

目前暂停游戏的方法就是把负责绘图的线程wait掉,wait方法在绘图的线程内执行

我在 run 方法获取了锁,按键响应后,跳到主线程的changeState方法执行改变 (表示运行状态的) runstate 的值.
然后根据runstate判断是否应该唤醒绘图线程.
写代码的时候暂停还比较简单,直接获取run方法的锁就可以wait了.
重新开始的时候在changeState方法中很难找到刚才获取的那个锁,因为notify要求跟wait是一个锁.
之后想到一个办法:
new Object , 获取了run的锁以后,Object=this,把当前的锁的位置传出去(因为Object是超类),notify的时候直接synchronized(Object)就可以获取到同一只锁了.

刚才想到把wait也放到changeState函数里面,这时就容易获取到同一只锁了(待测试)  ^_^

内容拙略,如有错误,请各位赐教.
