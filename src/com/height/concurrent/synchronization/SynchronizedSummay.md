 ## Java中synchronized的使用和底层原理解读
 
  最近在读Charlie Hunt大神的《Java Performance》，第三章讲《JVM Overview》中间有说到synchronized的一些基本逻辑。本文会做一些整理，主要内容和重要知识点(本文中若未明确说明，JVM默认指的是HotSpot版VM)：
*  synchronized是什么    
*  synchronized在JVM中的实现原理
    * **同步代码块**：通过**monitorenter**和**monitorexit**命令来实现
    * **同步方法**：通过**ACC_SYNCHRONIZED**标志位来实现
###### 注：在JVM中，其实ACC_SYNCHRONIZED标志也是通过monitor对象来实现的，不会在汇编层面实现有些区别。 
*  synchronized使用demo和注意点
    * **类对象锁**：修饰静态方法和class对象时
    * **实例对象锁**：修饰非静态方法、代码块和非class对象时
*  synchronized在源码中的使用和分析

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

 ### 3.synchronized在HotSpot VM中的实现原理
 * 方法：通过javap命令反解析class文件，获取对应class的代码区、本地变量表等等。今天我们主要用它来看下JVM最终把synchronized转化成了什么。
 * 步骤
    * 创建一个demo类
    ```
    package com.height.concurrent.synchronization.implementation;
    
    public class SynchronizedDemoOne {
        private static int age = 1;
        /**
         * synchronized 修饰静态方法
         * @return
         */
        public static synchronized Integer getAgeOne() {
            return age;
        }
        /**
         * synchronized 修饰非静态方法
         * @return
         */
        public synchronized Integer getAgeTwo() {
            return age;
        }
        /**
         * synchronized 修饰代码块
         * @return
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
    ```
     Classfile /Users/height/git/learn/JavaAccumulator/src/com/height/concurrent/synchronization/implementation/SynchronizedDemoOne.class
       Last modified 2020-9-9; size 877 bytes
       MD5 checksum bdd02e83e30f0ac316a408694f638868
       Compiled from "SynchronizedDemoOne.java"
     public class com.height.concurrent.synchronization.implementation.SynchronizedDemoOne
       minor version: 0
       major version: 52
       flags: ACC_PUBLIC, ACC_SUPER
     Constant pool:
        #1 = Methodref          #5.#26         // java/lang/Object."<init>":()V
        #2 = Fieldref           #4.#27         // com/height/concurrent/synchronization/implementation/SynchronizedDemoOne.age:I
        #3 = Methodref          #28.#29        // java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        #4 = Class              #30            // com/height/concurrent/synchronization/implementation/SynchronizedDemoOne
        #5 = Class              #31            // java/lang/Object
        #6 = Utf8               age
        #7 = Utf8               I
        #8 = Utf8               <init>
        #9 = Utf8               ()V
       #10 = Utf8               Code
       #11 = Utf8               LineNumberTable
       #12 = Utf8               LocalVariableTable
       #13 = Utf8               this
       #14 = Utf8               Lcom/height/concurrent/synchronization/implementation/SynchronizedDemoOne;
       #15 = Utf8               getAgeOne
       #16 = Utf8               ()Ljava/lang/Integer;
       #17 = Utf8               getAgeTwo
       #18 = Utf8               getAgeThree
       #19 = Utf8               StackMapTable
       #20 = Class              #30            // com/height/concurrent/synchronization/implementation/SynchronizedDemoOne
       #21 = Class              #31            // java/lang/Object
       #22 = Class              #32            // java/lang/Throwable
       #23 = Utf8               <clinit>
       #24 = Utf8               SourceFile
       #25 = Utf8               SynchronizedDemoOne.java
       #26 = NameAndType        #8:#9          // "<init>":()V
       #27 = NameAndType        #6:#7          // age:I
       #28 = Class              #33            // java/lang/Integer
       #29 = NameAndType        #34:#35        // valueOf:(I)Ljava/lang/Integer;
       #30 = Utf8               com/height/concurrent/synchronization/implementation/SynchronizedDemoOne
       #31 = Utf8               java/lang/Object
       #32 = Utf8               java/lang/Throwable
       #33 = Utf8               java/lang/Integer
       #34 = Utf8               valueOf
       #35 = Utf8               (I)Ljava/lang/Integer;
     {
       public com.height.concurrent.synchronization.implementation.SynchronizedDemoOne();
         descriptor: ()V
         flags: ACC_PUBLIC
         Code:
           stack=1, locals=1, args_size=1
              0: aload_0
              1: invokespecial #1                  // Method java/lang/Object."<init>":()V
              4: return
           LineNumberTable:
             line 3: 0
           LocalVariableTable:
             Start  Length  Slot  Name   Signature
                 0       5     0  this   Lcom/height/concurrent/synchronization/implementation/SynchronizedDemoOne;
     
       public static synchronized java.lang.Integer getAgeOne();
         descriptor: ()Ljava/lang/Integer;
         flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
         Code:
           stack=1, locals=0, args_size=0
              0: getstatic     #2                  // Field age:I
              3: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
              6: areturn
           LineNumberTable:
             line 8: 0
     
       public synchronized java.lang.Integer getAgeTwo();
         descriptor: ()Ljava/lang/Integer;
         flags: ACC_PUBLIC, ACC_SYNCHRONIZED
         Code:
           stack=1, locals=1, args_size=1
              0: getstatic     #2                  // Field age:I
              3: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
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
              3: monitorenter
              4: getstatic     #2                  // Field age:I
              7: invokestatic  #3                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
             10: aload_1
             11: monitorexit
             12: areturn
             13: astore_2
             14: aload_1
             15: monitorexit
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
     
       static {};
         descriptor: ()V
         flags: ACC_STATIC
         Code:
           stack=1, locals=0, args_size=0
              0: iconst_1
              1: putstatic     #2                  // Field age:I
              4: return
           LineNumberTable:
             line 5: 0
     }
     SourceFile: "SynchronizedDemoOne.java"

    ```