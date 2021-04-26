### CMS

#### 概述

![image-20210423224740531](/Users/height/Library/Application Support/typora-user-images/image-20210423224740531.png)

- 步骤

  - 初始标记：仅仅关联GC Roots直接关联的对象。**Stop World** **耗时短**
  - 并发标记：Concurrent-Mark，从根遍历整个对象图。**不需要StopWorld** **耗时长**
  - 重新标记：Remark,修正在并发标记中出现的变动对象的标记。**Stop World** **耗时短**
    - 这个过程中会用到write barriers（写屏障）:对新增的引用会重新进行标记。这个过程需要重新扫码
  - 并发清除：Concurrent-Sweep。清理删除判断已死亡的对象，释放内存空间。**不需要StopWorld** **耗时长**

  

- 优缺点
  - 优：并发收集，低延迟。耗时的操作都是并发执行，STW的时间比较短，就2次。
  - 缺:标记清除算法 就会有内存碎片。
  - 缺：在执行过程中，没有STW，所以用户线程执行的过程中，需要有足够的内存处理代码。失败则报 Concurrent Mode Failure
  - 缺：CMS对CPU资源比较敏感。一直会占用内存，吞吐量会下降。
  - 缺：无法处理浮动垃圾。在并发标记阶段和重新标记阶段，没办法处理产生的浮动垃圾，只能在下次GC中被回收。

- 参数

  - -XX:+UseConcMarkSweepGC，手动指定使用CMS。

  - -XX:CMSInitiatingOccupanyFraction设置内存使用率的阈值，JDK6之后默认92%。

    - 如果内存增长比较缓慢，则可以稍微大一点，如果涨的快，则需要小一点。

  - -XX:ParallelCMSThreads设置CMS的线程数量。

    - CMS默认数量 (ParallelCMSThreads+3)/4

  - -XX:CMSFullGCsBeforeCompaction=n 可以设置在多少次cms后进行内存整理压缩
  
  - -XX+UseCMSCompactAtFullCollection：在CMS后进行内存整理 ，一般不开启
  
  - -XX:+UseCMSInitiatingOccupancyOnly:使用手动定义初始化定义开始CMS收集.（禁止hotspot自己触发）
  
  - -XX:+CMSParallelRemarkEnabled ： 开启并发标记
  
  - 
  
    

- 参考：https://blog.csdn.net/zqz_zqz/article/details/70568819
- CMS的详细说明：https://plumbr.io/handbook/garbage-collection-algorithms-implementations#concurrent-mark-and-sweep
- 很不错的优化：https://blog.csdn.net/shudaqi2010/article/details/102567067
- gcLog分析：https://www.cnblogs.com/zhangxiaoguang/p/5792468.html
- metaspace的问题：https://www.jianshu.com/p/b448c21d2e71





