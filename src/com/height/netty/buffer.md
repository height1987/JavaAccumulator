### Buffer

- 定义：本质上是一个可以读写数据的内存块，可以理解成是一个容器对象(含数组)，对象提供一组方法，可以轻松的使用，同时能追踪和记录缓存区状态的变化。
- 类别
  - IntBuffer/ShortBuffer/CharBuffer/FloatBuffer/DoubleBuffer/LongBuffer/ByteBuffer
  - 除了boolean，其他的都是有对应的类型
- 参数
  - capacity
    - 缓存总容量
  - limit
    - 缓存区当前的终点，操作不能超过这个位置
  - position
    - 读和写当前的位置，读和写都会修改这个位置
  - mark
    - 标记

- fileNioDemo

```
public static void main(String[] args) throws Exception {

    FileInputStream fileInputStream = new FileInputStream("1.txt");
    FileChannel inputChannel = fileInputStream.getChannel();

    FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
    FileChannel outputChannel = fileOutputStream.getChannel();

    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    while (true){
        byteBuffer.clear();
        int read = inputChannel.read(byteBuffer);
        if(read == -1){
            break;
        }
        byteBuffer.flip();
        outputChannel.write(byteBuffer);
    }

    fileInputStream.close();
    fileOutputStream.close();
}
```





- 资料

  - buffer和channel的梳理 https://www.bilibili.com/video/BV1DJ411m7NR?p=20&spm_id_from=333.788.b_6d756c74695f70616765.20

  

