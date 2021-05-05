### Netty

- 几个网络请求处理模型

  - BIO（**Acceptor模型**）
    - 执行流程
      - 服务端创建一个serverSocket来监听8000端口，然后创建一个**线程**，线程不断调用阻塞方法serverSocket.accept()获取新连接，当获得新的链接后，给每条链接创建一个线程，读取链接中的数据，以字节流的方式读取。然后去处理，最后返回字节流。
    - 缺点
      - 以字符流方式读写数据，一次性读取字节，读完后无法获取，需要自己缓存数据
      - 每次开辟一个线程去处理请求，每个请求都会开辟一个线程，始终会有不少线程被block，浪费资源。(2个地方会阻塞：accept的时候，和read的时候。)
    - 适合情况：链接较少
  - NIO（**Reactor模型**）
    - 执行流程
      - 一个连接进来，不再有一对一的线程不断监听是否有数据进来。而是注册到selector上，然后通过检查selector就可以批量监控数据可读的链接，进而读入数据。
    - 优缺点
      - 优：面向buffer读写，可以随意读取缓存中的数据，不需要自己缓存，一切只需要读写指针即可。
      - 缺：NIO比较复杂，入门难，需要实现自己的线程模型来充分发挥优势，而且需要定义协议拆包黏包，维护成本高，自身空轮询bug，可能导致cpu飚搞。
    - 适合情况：链接较多，但是每个链接较短
  - AIO（**Proactor模型**）
    - 执行流程：简化变成流程，有效的请求才启动线程，由操作系统完成后才通知服务端程序启动线程去处理。
    - 适合情况：连接较多，而且每个连接时间较长

- BIO/NIO/AIO

  - BIO:客户需要从请求开始到请求被处理结束，一直被阻塞。如同坐在理发店里进行等待。
  - NIO:客户取个号，可以去做其他的事情，没过一段时间要过来看看有没有到了。
  - AIO:去和理发师说一下，理发师有用了就到家里给你理发。

  

- 定义：Netty是一个基于异步事件驱动的网络框架，内部封装了NIO，拥有NIO的优点，避免了NIO的缺点。



- Reactor模型和Netty线程模型

  - 单线程模型

    - ![image-20210428112002168](/Users/height/Library/Application Support/typora-user-images/image-20210428112002168.png)

  - 多线程模型

    - ![image-20210428112025404](/Users/height/Library/Application Support/typora-user-images/image-20210428112025404.png)

  - 主从模型

    - ![image-20210428112145932](/Users/height/Library/Application Support/typora-user-images/image-20210428112145932.png)

  - netty模型

    - ![image-20210428112204734](/Users/height/Library/Application Support/typora-user-images/image-20210428112204734.png)

    

  
  
  
  ![image-20210502171932088](/Users/height/Library/Application Support/typora-user-images/image-20210502171932088.png)
  
- 整理 Buffer/Selector/Channel的关系

  - 每个Channel对应一个Buffer
  - Selector对应一个线程，一个线程可以对应多个Channel
  - 该图反应了三个Channel注册到该Selector
  - 程序切换到哪个Channel是通过事件进行控制的
  - Buffer是一个内存块，底层是一个数组
  - 数组读取写入是通过Buffer，这个不像BIO中的输入流/输出流，做不到双向，NIO的Buffer可以即支持读也可以写，只要进行filp方法切换。
  - Channel是双向的，可以返回底层操作系统的情况，都是双向的。



源码部分

- Selector
  - 实现类：KQueueSelectorImpl
- SocketChannel
  - ServerSocketChannel是为了accept，然后获取对应的SocketChannel。主要是server端为了分发channle设定的
  - SocketChannel是为了处理真实的各种请求









- 参考资料
  - NIO https://lishaojie1993.gitee.io/2020/04/03/nio/
  - 脑图NIO https://www.processon.com/view/6080ee7b6376891c53b7336c#map
  - NIO 和Netty ：https://www.processon.com/view/603df25af346fb55c9a8a8e5
  - NIO 和Netty解释：https://www.processon.com/view/601ca7bce0b34d41a73ea550
  - 源码解析：https://www.processon.com/view/602a942f6376891d5f83467f#map
  - 源码解释：https://www.processon.com/view/5f572bc1e0b34d6f59e612dc
  - https://www.processon.com/view/6034b9dd079129248a627c8f#map