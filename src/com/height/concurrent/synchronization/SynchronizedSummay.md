 ## Java中synchronized的使用和底层原理解读
 
  最近在读Charlie Hunt大神的《Java Performance》，第三章讲《JVM Overview》中间有说到synchronized的一些基本逻辑。本文会做一些整理，主要内容和重要知识点(本文中若未明确说明，JVM默认指的是HotSpot版VM)：
1.  synchronized是什么 
2.  synchronized有哪些常见用法   
3.  synchronized在JVM中的实现原理
    * **同步方法**：通过**ACC_SYNCHRONIZED**标志位来实现
    * **同步代码块**：通过**monitorenter**和**monitorexit**命令来实现
 
4. synchronized使用demo和注意点
    * **类对象锁**：修饰静态方法和class对象时
    * **实例对象锁**：修饰非静态方法、代码块和非class对象时
5.  synchronized锁优化和锁升级过程
    * 无锁
    * 偏向锁
    * 轻量级锁
    * 重量级锁

### 1. synchronized是什么？
  《Java performance》中的定义是：
  ```
    Synchronization is described as a mechanism that prevents, avoids, 
    or recovers from the inopportune interleavings, commonly called races, of concurrent operations. 
  ```
  翻译：
  ```
    同步是一种并发操作机制，用来预防、避免对资源不合适的交替使用（通常竞争），保障交替使用资源的安全。
  ```
### 2. synchronized有哪些常见用法
* 修饰方法
  ```java
   public static synchronized Integer getAgeOne() { //静态方法
          return age;
      }
   public synchronized Integer getAgeTwo() { //实例方法
        return age;
    }
  ```
* 修饰代码块
  ```java
    public Integer getAgeThree() {
        synchronized (this) {
            return age;
        }
    }
  ```
 ### 3. synchronized在HotSpot VM中的实现原理
 * 方法
   * 通过**javap**命令反解析class文件，获取synchronized在字节码层面是如何实现的。
 * 步骤
    * 创建一个demo类
    ```java
    public class SynchronizedDemoOne {
        private static int age = 1;
        /**
         * synchronized 修饰静态方法
         */
        public static synchronized Integer getAgeOne() {
            return age;
        }
        /**
         * synchronized 修饰非静态方法
         */
        public synchronized Integer getAgeTwo() {
            return age;
        }
        /**
         * synchronized 修饰代码块
         */
        public Integer getAgeThree() {
            synchronized (this) {
                return age;
            }
        }
    }
    ```
    * 通过classc命令把java编译成class文件
    ```
        javac -g ./SynchronizedDemoOne.java
    ```
    * 通过classp命令对class文件进行反解析
    ```
        javap  -verbose  SynchronizedDemoOne
    ```
    * 得到反解析后的文件
    ```java
     Classfile /Users/height/git/learn/JavaAccumulator/src/com/height/concurrent/synchronization/implementation/SynchronizedDemoOne.class
       Last modified 2020-9-9; size 877 bytes
       MD5 checksum bdd02e83e30f0ac316a408694f638868
       Compiled from "SynchronizedDemoOne.java"
     public class com.height.concurrent.synchronization.implementation.SynchronizedDemoOne
       minor version: 0
       major version: 52
       flags: ACC_PUBLIC, ACC_SUPER
     Constant pool:
        #1 = Methodref          #5.#26         
        #2 = Fieldref           #4.#27         
        .                                      //中间省略部分
        .
        .
       #34 = Utf8               valueOf
       #35 = Utf8               (I)Ljava/lang/Integer;
     {
       public com.height.concurrent.synchronization.implementation.SynchronizedDemoOne();
         descriptor: ()V
         flags: ACC_PUBLIC
         Code:
           stack=1, locals=1, args_size=1
              0: aload_0
              1: invokespecial #1                  
              4: return
           LineNumberTable:
             line 3: 0
           LocalVariableTable:
             Start  Length  Slot  Name   Signature
                 0       5     0  this   Lcom/height/concurrent/synchronization/implementation/SynchronizedDemoOne;
     
       public static synchronized java.lang.Integer getAgeOne();
         descriptor: ()Ljava/lang/Integer;
         flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED   // ACC_SYNCHRONIZED 标志位
         Code:
           stack=1, locals=0, args_size=0
              0: getstatic     #2                  
              3: invokestatic  #3                  
              6: areturn
           LineNumberTable:
             line 8: 0
     
       public synchronized java.lang.Integer getAgeTwo();
         descriptor: ()Ljava/lang/Integer;
         flags: ACC_PUBLIC, ACC_SYNCHRONIZED             // ACC_SYNCHRONIZED 标志位
         Code:
           stack=1, locals=1, args_size=1
              0: getstatic     #2                  
              3: invokestatic  #3                  
              6: areturn
           LineNumberTable:
             line 12: 0
           LocalVariableTable:
             Start  Length  Slot  Name   Signature
                 0       7     0  this   Lcom/height/concurrent/synchronization/implementation/SynchronizedDemoOne;
     
       public java.lang.Integer getAgeThree();
         descriptor: ()Ljava/lang/Integer;
         flags: ACC_PUBLIC
         Code:
           stack=2, locals=3, args_size=1
              0: aload_0
              1: dup
              2: astore_1
              3: monitorenter                             //获取monitor对象
              4: getstatic     #2                  
              7: invokestatic  #3                  
             10: aload_1
             11: monitorexit                              //释放monitor对象
             12: areturn
             13: astore_2
             14: aload_1
             15: monitorexit                              //锁定过程中发生异常时的释放monitor对象
             16: aload_2
             17: athrow
           Exception table:
              from    to  target type
                  4    12    13   any
                 13    16    13   any
           LineNumberTable:
             line 16: 0
             line 17: 4
             line 18: 13
           LocalVariableTable:
             Start  Length  Slot  Name   Signature
                 0      18     0  this   Lcom/height/concurrent/synchronization/implementation/SynchronizedDemoOne;
           StackMapTable: number_of_entries = 1
             frame_type = 255 /* full_frame */
               offset_delta = 13
               locals = [ class com/height/concurrent/synchronization/implementation/SynchronizedDemoOne, class java/lang/Object ]
               stack = [ class java/lang/Throwable ]
            .
            .                                  //省略部分
            .
     }
    ```
   ###### 完整的文件参见： [反解析完整文件](https://github.com/height1987/JavaAccumulator/blob/master/src/com/height/concurrent/synchronization/implementation/SynchronizedDemoOne_code.txt)
   
 * 分析 
   * 官方对synchronized关键词的解释是这样的[synchronized官方解释](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.11.10)
     ```
        Method-level synchronization is performed implicitly, as part of method invocation and return. 
        A synchronized method is distinguished in the run-time constant pool’s method_info structure by the ACC_SYNCHRONIZED flag,
        which is checked by the method invocation instructions. When invoking a method for which ACC_SYNCHRONIZED is set, 
        the executing thread enters a monitor, invokes the method itself, and exits the monitor whether the method invocation completes normally or abruptly. 
        During the time the executing thread owns the monitor, no other thread may enter it. 
        If an exception is thrown during invocation of the synchronized method and the synchronized method does not handle the exception, 
        the monitor for the method is automatically exited before the exception is rethrown out of the synchronized method.
     ```
     翻译下
     ```
        同步方法的运行是隐式的，类似于jvm对于方法的引用和返回的支持。同步方法通过在运行常量池里method_info数据结构中的ACC_SYNCHRONIZED标签来标注。
        如果一个线程发现调用的方法有ACC_SYNCHRONIZED标记，那么线程的执行过程就变成：获取monitor对象，调用方法，释放monitor对象。
        在某个线程持有monitor对象时，如果其他线程也想获取该对象，则会别阻塞。
        如果一个同步方法执行过程中发生异常，而且方法自己没有处理，那么在异常被向外抛时，线程也会自动释放monitor对象。
        ```
   **官方文档也说的非常清楚了，JVM其实在处理同步方法的时候，是隐式的通过monitor对象来实现。
   从反解析的class中也可以看到，同步代码块是显式的通过monitor对象来实现互斥访问。
   因此可以简单的归纳下，synchronized关键词的实现，在JVM中，主要通过获取monitor对象来实现的。**

   
   
   ### 4. synchronized使用demo和注意点
   ##### 4.1 案例1 
   * 代码
   ```java
   public class SynchronizedDemoTwo {
       public synchronized static void synchronizedStaticMethodMethod() {  //同步静态方法
           System.out.println("synchronized static method start !");
           sleep(1000);
           System.out.println("synchronized static method  end ！");
       }
       public static void synchronizedClassMethod() {                     //同步代码块-同步对象为class对象
           synchronized (SynchronizedDemoTwo.class) {
               System.out.println("synchronized class start !");
               sleep(1000);
               System.out.println("synchronized class end ！");
           }
       }
       public static void main(String args[]) {
           synchronizedRun();
       }
       private static void synchronizedRun() {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   SynchronizedDemoTwo.synchronizedStaticMethodMethod();
               }
           }).start();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   SynchronizedDemoTwo.synchronizedClassMethod();
               }
           }).start();
       }
       private static void sleep(int second) {
           try {
               Thread.sleep(second);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   }
   ```
   * 执行结果
   ```
   synchronized static method start !          
   synchronized static method  end ！
   synchronized class start !               //在静态同步方法执行结束后才开始执行同步代码块
   synchronized class end ！
   ```
   * 分析
     * 静态方法和同步参数是class对象时，执行时会获取class对象的锁，所以上述代码会发生锁竞争，执行结果也证实了这个逻辑。
   * 注意点
     * 当你使用synchronized修饰静态方法或者class对象时，要非常谨慎，同一个class只有一把锁，这个锁作用域是非常大的。像String.class,Integer.class这些原生类也不要轻易加锁。
     
   ##### 4.2 案例2
   * 代码
   ```java
   public class SynchronizedDemoThree {
       public synchronized void firstSynchronizedMethod() {    //同步方法1
           System.out.println("first synchronized start !");
           sleep(1000);
           System.out.println("first synchronized end ！");
       }
       public synchronized void secondSynchronizedMethod() {   //同步方法2
           System.out.println("second synchronized start !");
           sleep(1000);
           System.out.println("second synchronized  end ！");
       }
       public void synchronizedBlockMethod() {                //同步代码块-同步对象为实例对象
           synchronized (this) {
               System.out.println("synchronized block start !");
               sleep(1000);
               System.out.println("synchronized block end ！");
           }
       }
       public static void main(String args[]) {
           SynchronizedDemoThree demo1 = new SynchronizedDemoThree();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   demo1.firstSynchronizedMethod();
               }
           }).start();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   demo1.secondSynchronizedMethod();
               }
           }).start();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   demo1.synchronizedBlockMethod();
               }
           }).start();
       }
       private static void sleep(int second) {
           try {
               Thread.sleep(second);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   }
   ```
   * 执行结果
    ```
    first synchronized start !
    first synchronized end ！
    synchronized block start !          
    synchronized block end ！
    second synchronized start !
    second synchronized  end ！            //有序执行这3个方法，说明发生了竞争
    ```
      * 分析
        * 实例方法和代码块被synchronized修饰时，执行时会获取实例对象的锁，所以上述代码会发生锁竞争，执行结果也证实了这个逻辑。
      * 注意点
        * 尽量不要用一些公用对象的锁，比如封装类常量池中的一些对象： Integer,String等都有类似的逻辑。
        
### 5. synchronized锁优化逻辑和锁升级过程分析
#### 5.1 锁的几种状态
  由于开始设计的同步逻辑，在发生互斥资源竞争访问时，等待的线程会变成block状态。而线程的调度是在内核态运行的，所以涉及到了内核态和用户态的切换，而且是2次：block时一次，唤醒时一次。
  所以这样的操作效率不高，JDK1.6开始，就对synchronized的机制做了优化，把锁的状态分成了以下几种：
  * 无锁状态：已解锁
  * 偏向锁：已锁定/已解锁且无共享
  * 轻量级锁：已锁定且共享，但非竞争。
  * 重量级锁：已锁定/已解锁且共享和竞争。线程在monitor-enter或wait()时被阻塞。
  
#### 5.2 举个例子来说明这几种状态
  * 银行交易有一个窗口可以办业务，门口有个取票机和一个引导员（帮助不会操作的客户）。
  * 为了每次只有1个客户到窗口办业务，办业务前客户必须取票，然后系统会根据取票顺序按一定逻辑来叫号。
  * 等待叫号必须去专门的等候区，等候区域距离取票机和窗口都有一定的距离。**（系统调度效率不高）**
  * 运行一段时间后，发现有时候客户很少，还是需要取号，然后到等候区等待叫号，如果一个同一个客户多次办业务，就需要来回跑。
  
  优化后：
  * 在客户很少的时候，如果窗口空闲，则第一个来办理业务的人，引导员会只需记录他的名字，不用取票，直接让他去办业务，而且只要没有新客户，他多次办业务都不需要取票。**（这时候变成了偏向锁）**
  * 正在他享受这超级vip服务的时候，有来了新的客户，新客户也知道银行的新规定，没有直接取号，而是询问引导员是否可办业务，引导员说不行，因为现在有人在办。**（这时候变成了轻量级锁，通过cas判断是否能获取锁）**
  * 新客户知道取票等候区一套流程蛮麻烦，所以告诉引导员说，他可以旁边等一等，前面人办完了，他也想直接进去办业务。**（这时候变成了自旋锁）**
  * 新客户发现自己询问了10次都没等到办业务，所以直接向银行大堂经理投诉。银行经理就过来说，今天你们不准不取号了，每次进去办业务必须取号。**（这时候变成了重锁）**
  
  分析：
   * 偏向锁适合的场景是，某段时间内访问互斥资源的线程基本是同一个，没有共享访问的场景
   * 轻量级锁适合的场景是，每次访问互斥资源的时间很短，大家能共享访问，互不影响
   * 重量级锁适合的场景是，常发生竞争，每次占用资源的时间都不短

#### 5.3锁升级简化版
   * Mark Word介绍
     * JVM主要通过对象头中的Mark Word来标记锁的相关状态，包括当前锁的状态和持有锁对象的信息，下面是在不同状态下Mark Word的信息。
     ![mark word.png](http://outter.oss-cn-shanghai.aliyuncs.com/MarkWord.png)
   * 锁升级流程简化版
     * 很多博客中有一个详细版的锁升级流程，我把他们简化了下，更容易理解一些
     ![锁升级.png](http://outter.oss-cn-shanghai.aliyuncs.com/LockUpgradeV2.png)
   * 注意点
     * 锁的状态只有4种，无锁->偏向锁->轻量级锁->重量级锁
     * 升级过程不可逆，不同阶段通过从轻到重的方式获取锁
     * 自旋这个操作是通过线程死循环，而防止被阻塞，试图避免用户态和内核态的切换，所以本身不属于锁的状态，是配合轻量级锁使用的一种方式
     
  

#### 本文中所有的代码和说明都可以在github中找到，[真相戳这里>](https://github.com/height1987/JavaAccumulator/tree/master/src/com/height/concurrent/synchronization)

----

我是大旗，努力用易理解的案例分析进阶知识，一起来学习JVM调优，高并发，常用中间件吧~

如果喜欢我的文章，请关注我吧~

![二维码](http://outter.oss-cn-shanghai.aliyuncs.com/qrcode-daqi.jpg)