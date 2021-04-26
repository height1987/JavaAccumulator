### 工具集合

#### MAT

- 可以查看GC Root



#### JProfiler

- GC ROOT 溯源

#### Visualvm

- 可以用于dump 堆



### 命令行



#### JMap



#### JPS

- 通jps获得运行中的线程 JPS
- 然后通过Jinfo获得当前线程的命令参数  jinfo -flag UseG1GC 1515

#### Jinfo



### OOM的定位逻辑

- 可以在OOM的时候把内存做dump

  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${目录}

- 导入JProfiler 分析逻辑



