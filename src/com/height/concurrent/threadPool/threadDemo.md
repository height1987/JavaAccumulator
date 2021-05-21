### 1. 一些概念



- 包关系

![image-20210518094216978](/Users/height/Library/Application Support/typora-user-images/image-20210518094216978.png)

### 2. Executors接口

### 3.常见的ThreadPool

- SingleThreadPool



### 4.线程池执行流程

> 通过ThreadPoolExecutor来创建线程池?
>
> 主要是因为



当提交一个新任务到线程池时，线程池的处理流程如下：

1. 如果当前运行的线程少于corePoolSize，则创建新线程来执行任务（注意，执行这一步骤需要获取全局锁）。
2. 如果运行的线程等于或多于corePoolSize，则将任务加入BlockingQueue。
3. 如果无法将任务加入BlockingQueue（队列已满），则创建新的线程来处理任务（注意，执行这一步骤也需要获取全局锁）。
4. 如果创建新线程将使当前运行的线程数超出maximumPoolSize，该任务将被拒绝，并调用相应的拒绝策略来处理（RejectedExecutionHandler.rejectedExecution()方法，线程池默认的饱和策略是AbortPolicy，也就是抛异常）。

ThreadPoolExecutor采取上述步骤的总体设计思路，是为了在执行execute()方法时，尽可能地避免获取全局锁（那将会是一个严重的可伸缩瓶颈）。在ThreadPoolExecutor完成预热之后（当前运行的线程数大于等于corePoolSize），几乎所有的execute()方法调用都是执行步骤2，而步骤2不需要获取全局锁。

> 线程池任务 拒绝策略包括 **抛异常**、**直接丢弃**、**丢弃队列中最老的任务**、**将任务分发给调用线程处理**



```java
new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, runnableTaskQueue, handler);
```

参数





 ### 技术文章
- 线程池 https://blog.csdn.net/m0_37542889/article/details/92640903


https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html

https://tech.meituan.com/2021/01/01/2020-summary.html

- 为什么不通过executor创建线程池 https://blog.csdn.net/weixin_38852633/article/details/91210491
- 线程池逻辑 https://juejin.cn/post/6935680090990723108
- 并发编程  https://www.cnblogs.com/dolphin0520/p/3949310.html
- 线程的状态和java的对应关系 https://www.jianshu.com/p/830c9334dd88
- 线程池：https://mp.weixin.qq.com/s/qJW_nPjlNYjePv4PfgpXeQ
- 线程池面试题 ：https://mp.weixin.qq.com/s/OCgfBmmbcCoO4fjMBk7EWg
- 线程池拒绝策略：https://mp.weixin.qq.com/s/xtrWyGO6gTAl94JLI3SRWg
- 如何确认线程池的大小：https://mp.weixin.qq.com/s/2gD9L8BalWcDD-lazuUfwA
- 监控线程池：https://mp.weixin.qq.com/s?__biz=MzIyMzgyODkxMQ==&mid=2247483762&idx=1&sn=c7c25589c57a826508a6f0f62030c936&chksm=e8190fb2df6e86a4363d0157cb9158a4357732dd730a66c719f13747f038ed05c1abccbd8f9d&scene=21#wechat_redirect
- Happen-before:https://mp.weixin.qq.com/s?__biz=MzIwNTI2ODY5OA==&mid=2649939170&idx=1&sn=68ce8cb33e5c710e1ecfae782b7692f9&chksm=8f350f57b8428641684dd38070793646e415355cfcc0e777799bdf5575f4dd6f5b0f96f41275&scene=21#wechat_redirect
- wait notify的原理 ：https://blog.csdn.net/a460708485/article/details/82023662
- threadPoolExecutor 源码分析 https://www.cnblogs.com/huangjuncong/p/10031525.html



