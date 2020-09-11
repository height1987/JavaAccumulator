 ## Java中synchronized的使用和底层原理解读
 
  最近在读Charlie Hunt大神的《Java Performance》，第三章讲《JVM Overview》中间有说到synchronized的一些基本逻辑。本文会做一些整理，主要内容和重要知识点(本文中若未明确说明，JVM默认指的是HotSpot版VM)：
*  1.synchronized是什么
*  2.synchronized常见的用法
    * **类锁**：修饰静态方法和class对象时
    * **对象锁**：修饰非静态方法、代码块和非class对象时
*  3.synchronized在JVM中的实现原理
    * **同步代码块**：通过**monitorenter**和**monitorexit**命令来实现
    * **同步方法**：通过**ACC_SYNCHRONIZED**标志位来实现
###### 注：在JVM中，其实ACC_SYNCHRONIZED标志也是通过monitor对象来实现的，不会在汇编层面实现有些区别。 
*  4.synchronized使用demo和注意点

*  5.synchronized在源码中的使用和分析

### 1.synchronized是什么？
  《Java performance》中的定义是：
  ```
    Synchronization is described as a mechanism that prevents, avoids, 
    or recovers from the inopportune interleavings, commonly called races, of concurrent operations. 
  ```
  翻译：
  ```
    同步是一种并发操作机制，用来预防、避免对资源不合适的交替使用（通常竞争），保障交替使用资源的安全。
  ```
  在Java中，是通过线程来实现并发。
### 2.synchronized有哪些常见用法
* 修饰方法
  ```
   public static synchronized Integer getAgeOne() {
          return age;
      }
   public synchronized Integer getAgeTwo() {
        return age;
    }
   

* 修饰代码块
  ```
    public Integer getAgeThree() {
        synchronized (this) {
            return age;
        }
    }

 