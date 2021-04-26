### ZGC

#### 概述

​	ZGC（Z Garbage Collector）是一款由Oracle公司研发的，以低延迟为首要目标的一款垃圾收集器。它是基于动态Region内存布局，（暂时）不设年龄分代，使用了读屏障、染色指针和内存多重映射等技术来实现可并发的标记-整理算法的收集器。在JDK 11新加入，还在实验阶段，主要特点是：回收TB级内存（最大4T），停顿时间不超过10ms。

- 分Region

  - 小型Region（Small Region）：容量固定为2MB，用于放置小于256KB的小对象。

  - 中型Region（Medium Region）：容量固定为32MB，用于放置大于等于256KB但小于4MB的对象。·

  - 大型Region（Large Region）：容量不固定，可以动态变化，但必须为2MB的整数倍，用于放置4MB或以上的大对象。每个大型Region中只会存放一个大对象，最小容量可低至4MB，所有大型Region可能小于中型Region。大型Region在ZGC的实现中是不会被重分配的，因为复制一个大对象的代价非常高昂。

    

- 指针染色技术
  - 
- 





- 资料
  - ZGC处理：https://blog.csdn.net/xiaolyuh123/article/details/103937164