#### JDK新特性

##### 资料来源

- oracle的官方文档 https://www.oracle.com/java/technologies/java-se-glance.html
- 文章 ：https://my.oschina.net/mdxlcj/blog/3010342
- 文章：https://www.huaweicloud.com/articles/a21f0a526bc2f7aa8b5cd933a199eadb.html
- 

| 版本  | 最大的变化 |      |      |
| :---- | ---------- | ---- | ---- |
| JDK8  |            |      |      |
| JDK9  |            |      |      |
| JDK11 |            |      |      |
| JDK16 |            |      |      |



每个版本新特性

- JDK5

  - 泛型
  - 增强循环 for (int i : array)
  - 自动拆箱和装箱
  - 枚举
  - 可变参数
  - 静态导入
  - 注解
  - 新的线程模型和并发库

- JDK6

  - 集合框架增强
  - 新的数组拷贝方法
  - scripting

- JDK7

  - 二进制前缀。整型可以直接用二进制表达。
  - 字面常量数字的下划线。int s = 1_2;用于理解，不影响值
  - switch支持String类型。
  - 泛型实例化类型的自动推断。
  - try-with-resources语句
  - 单个catch中捕获多个异常类型（用`|` 分割）并通过改进的类型检查重新抛出异常。

- JDK8

  - lambada表达式。运行把函数作为方法的参数
  - 方法引用。方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，可以使语言的构造更紧凑简洁，减少冗余代码。//TODO
  - 默认方法。默认方法允许将新功能添加到库的接口中，并确保兼容实现老版本接口的旧有代码。
  - 重复注解。重复注解提供了在同一声明或类型中多次应用相同注解类型的能力。
  - 类型注解。在任何地方都能使用注解，而不是在声明的地方。
  - 类型推断增强
  - 方法参数反射
  - Stream API 
  - 新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。Stream API集成到了Collections API里。
  - HashMap改进。
  - Date Time API。加强对日期和时间的处理
  - java.util
  - 并行数组排序
  - 标准的Base64编解码
  - 支持无符号运算
  - java.util.concurrent 包下增加了新的类和方法。
  - 删除了 永久代（PermGen）
  - 方法调用的字节码指令支持默认方法。

- JDK9

  - java模块系统
  - 新的版本号格式
  - java shell
  - 在`private instance methods`方法上可以使用`@SafeVarargs`注解。
  - diamond语法与匿名内部类结合使用。
  - 下划线`_`不能单独作为变量名使用。
  - 支持私有接口方法(您可以使用diamond语法与匿名内部类结合使用)。
  - Javadoc
  - 简化Doclet API。

  - 支持生成HTML5格式。
  - 加入了搜索框,使用这个搜索框可以查询程序元素、标记的单词和文档中的短语。
  - 支持新的模块系统
  - **增强了`Garbage-First(G1)`并用它替代`Parallel GC`成为默认的垃圾收集器**。
  - 统一了JVM 日志，为所有组件引入了同一个日志系统
  - 删除了JDK 8中弃用的GC组合。（DefNew + CMS，ParNew + SerialOld，Incremental CMS）
  - properties文件支持`UTF-8`编码,之前只支持`ISO-8859-1`
  - 支持`Unicode 8.0`，在JDK8中是`Unicode 6.2`

- JDK10

  - 局部变量类型推断（Local-Variable Type Inference）
  - `var`是一个保留类型名称，而不是关键字。所以之前使用`var`作为变量、方法名、包名的都没问题，但是如果作为类或接口名，那么这个类和接口就必须重命名了
  - var的使用场景：
    - 本地变量初始化
    - 增强for循环中。
    - 传统for循环中声明的索引变量。
    - Try-with-resources 变量。
  - `Optional`类添加了新的方法`orElseThrow`(无参数版)。相比于已经存在的get方法，这个方法更推荐使用。

- `JDK11

  - 字符串的增强
  - 支持`Unicode 10.0`,在jdk10中是8.0
  - 标准化HTTP Client
  - 编译器线程的延迟分配。添加了新的命令`-XX:+UseDynamicNumberOfCompilerThreads`动态控制编译器线程的数量。
  - 新的垃圾收集器—ZGC。一种可伸缩的低延迟垃圾收集器(实验性)。 https://blog.csdn.net/m0_38001814/article/details/88831037
  - Epsilon。一款新的实验性无操作垃圾收集器。Epsilon GC 只负责内存分配，不实现任何内存回收机制。这对于性能测试非常有用，可用于与其他GC对比成本和收益。
  - Lambda参数的局部变量语法。java10中引入的var字段得到了增强，现在可以用在lambda表达式的声明中。如果lambda表达式的其中一个形式参数使用了var，那所有的参数都必须使用var。

- JDK12

  - switch表达式：短路机制防止没有break的场景导致问题。

    switch (day) {
    case MONDAY, FRIDAY, SUNDAY -> System.out.println(6);
    case TUESDAY -> System.out.println(7);
    case THURSDAY, SATURDAY -> System.out.println(8);
    case WEDNESDAY -> System.out.println(9);
    }

  - Shenandoah GC

- JDK13

  - switch表达式升级

    static void howMany(int k) {
        System.out.println(
            switch (k) {
                case  1 -> "one"
                case  2 -> "two"
                default -> "many"
            }
        );
    }

  - 文本块的升级

  - 重新实现老的套接字接口api

  - 核心库/java.util中：L18N

  - 取消未使用的内存

- JDK14

  - switch优化至最终版

  - 垃圾回收机制：

    - 删除CMS
    - 弃用ParallelScavenge + SerialOld GC 的垃圾回收算法组合
    - 将 zgc 垃圾回收器移植到 macOS 和 windows 平台

  - Instanceof的模式匹配

    if (obj instanceof String s) { 

    ​    *// can use s here* } else {

    ​    *// can't use s here* }

  - 删除 安全库**java.security.acl API**

  - 货币格式优化：可以通过 NumberFormat.getCurrencyInstance(Locale)使用“ u-cf-account” Unicode区域设置扩展名来获得具有记帐样式的

- JDK15

  - 隐藏类。此功能可帮助需要在运行时生成类的框架。框架生成类需要动态扩展其行为，但是又希望限制对这些类的访问。隐藏类很有用，因为它们只能通过反射访问，而不能从普通字节码访问。此外，隐藏类可以独立于其他类加载，这可以减少框架的内存占用。这是一个新的功能
  - records关键词 第二版
  - 封闭类(sealed classes )：可以指定被哪些类继承的类
  - text blocks
  - EdDSA 数字签名算法
  - 准备禁用和废弃偏向锁
  - ZGC转正
  - Shenandoah转正

- JDK16 https://blog.csdn.net/bntX2jSQfEHy7/article/details/114984861

  - 支持类型匹配instanceof
  - record关键词
  - 全并发ZGC
  - 可弹性伸缩的元数据区
  - 支持unix套接字
  - 新的打包工具jpackage
  - 针对value-based类的编译warning提示
  - 对jdk内部方法提供强制的封装
  - 提供向量计算api
  - 对原生代码的调用提供更方便的支持
  - 提供操作外部内存的能力
  - 提供限制继承此类的sealed和permits

  

  