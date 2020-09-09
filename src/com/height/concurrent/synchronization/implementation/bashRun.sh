# 本脚本是基于mac的脚本，可以将demo SynchronizedDemoOne 生成文中的字节码反编译后的code。
# 通过分析反编译后的文件内容，了解Synchronized的实现原理

# 通过javac命令获取对应的class文件
javac -g ./SynchronizedDemoOne.java

# javap是jdk自带的反解析工具。
# 它的作用就是根据class字节码文件，反解析出当前类对应的code区（汇编指令）、
# 本地变量表、异常表和代码行偏移量映射表、常量池等等信息。
javap  -verbose  SynchronizedDemoOne >> SynchronizedDemoOne_code.txt