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





gclog解读

```
//第一步 初始标记 这一步会停顿
2021-04-26T21:01:46.924+0800: 11.678: [GC (CMS Initial Mark) [1 CMS-initial-mark: 35588K(353920K)] 46791K(507264K), 0.0074042 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 

//第二步 并发标记
2021-04-26T21:01:46.932+0800: 11.685: [CMS-concurrent-mark-start]
2021-04-26T21:01:46.989+0800: 11.742: [CMS-concurrent-mark: 0.037/0.057 secs] [Times: user=0.10 sys=0.01, real=0.05 secs] 

//第三步 预清理
2021-04-26T21:01:46.989+0800: 11.742: [CMS-concurrent-preclean-start]
2021-04-26T21:01:46.994+0800: 11.748: [CMS-concurrent-preclean: 0.005/0.005 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 

//第四步 可被终止的预清理
2021-04-26T21:01:46.994+0800: 11.748: [CMS-concurrent-abortable-preclean-start]
2021-04-26T21:01:47.875+0800: 12.628: [CMS-concurrent-abortable-preclean: 0.613/0.880 secs] [Times: user=1.61 sys=0.02, real=0.88 secs] 

//第五步 重新标记
2021-04-26T21:01:47.879+0800: 12.632: [GC (CMS Final Remark) [YG occupancy: 97724 K (153344 K)]2021-04-26T21:01:47.879+0800: 12.632: [Rescan (parallel) , 0.0454485 secs]2021-04-26T21:01:47.924+0800: 12.678: [weak refs processing, 0.0033182 secs]2021-04-26T21:01:47.928+0800: 12.681: [class unloading, 0.0073723 secs]2021-04-26T21:01:47.935+0800: 12.688: [scrub symbol table, 0.0048092 secs]2021-04-26T21:01:47.940+0800: 12.693: [scrub string table, 0.0021463 secs][1 CMS-remark: 35588K(353920K)] 133312K(507264K), 0.0638955 secs] [Times: user=0.08 sys=0.00, real=0.07 secs] 

//第六步 清理
2021-04-26T21:01:47.943+0800: 12.696: [CMS-concurrent-sweep-start]
2021-04-26T21:01:48.012+0800: 12.765: [CMS-concurrent-sweep: 0.069/0.069 secs] [Times: user=0.13 sys=0.01, real=0.07 secs] 

//第七步 重置
2021-04-26T21:01:48.012+0800: 12.765: [CMS-concurrent-reset-start]
2021-04-26T21:01:48.029+0800: 12.782: [CMS-concurrent-reset: 0.017/0.017 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
```





- 碎片化可能会导致的问题

  - ​     -XX:+UseCMSCompactAtFullCollection             -XX:CMSFullGCsBeforeCompaction=0            
  - 两个参数是针对cms垃圾回收器碎片做优化的，CMS是不会移动内存的， 运行时间长了，会产生很多内存碎片， 导致没有一段连续区域可以存放大对象，出现”**promotion failed**”、”**concurrent mode failure**”, 导致fullgc，启用UseCMSCompactAtFullCollection 在FULL GC的时候， 对年老代的内存进行压缩。-XX:CMSFullGCsBeforeCompaction=0 则是代表多少次FGC后对老年代做压缩操作，默认值为0，代表每次都压缩, 把对象移动到内存的最左边，可能会影响性能,但是可以消除碎片；
    106.641: [GC 106.641: [ParNew (promotion failed): 14784K->14784K(14784K), 0.0370328 secs]106.678: [CMS106.715: [CMS-concurrent-mark: 0.065/0.103 secs] [Times: user=0.17 sys=0.00, real=0.11 secs]
    (concurrent mode failure): 41568K->27787K(49152K), 0.2128504 secs] 52402K->27787K(63936K), [CMS Perm : 2086K->2086K(12288K)], 0.2499776 secs] [Times: user=0.28 sys=0.00, real=0.25 secs]

- promotion failed：较高等级的内存区域，然而有时候会出现一些意外，导致对象晋升失败
  - MinorGC时，Eden区的对象晋升至Suvivor区，发现空间不足，直接晋升至Old区，但是Old区也放不下，导致对象的晋升失败，进而触发FGC。这个也会导致concurrent mode failed的报错。
- concurrent mode failed
  - 在CMS发生过程中，用户新创建的对象使old区直接满了。清理速度小于垃圾的新增速度。
  - 解决方法：
    - 降低cms触发百分比
    - 增加老年代大小
    - xx次cms后进行fullgc 清理垃圾

- **空间分配担保**
  -  在发生**Minor GC**之前，虚拟机会检查**老年代最大可用的连续空间**是否**大于新生代所有对象的总空间**
    - 如果大于，则此次**Minor GC是安全的**
    - 如果小于，则虚拟机会查看**HandlePromotionFailure**设置值是否允许担保失败。如果HandlePromotionFailure=true，那么会继续检查老年代最大可用连续空间是否大于**历次晋升到老年代的对象的平均大小**，如果大于，则尝试进行一次Minor GC，但这次Minor GC依然是有风险的；如果小于或者HandlePromotionFailure=false，则改为进行一次Full GC。



- 参考：https://blog.csdn.net/zqz_zqz/article/details/70568819
- CMS的详细说明：https://plumbr.io/handbook/garbage-collection-algorithms-implementations#concurrent-mark-and-sweep
- 很不错的优化：https://blog.csdn.net/shudaqi2010/article/details/102567067
- gcLog分析：https://www.cnblogs.com/zhangxiaoguang/p/5792468.html
- metaspace的问题：https://www.jianshu.com/p/b448c21d2e71
- 几个大的问题  https://www.jianshu.com/p/573b5c6b8e89





