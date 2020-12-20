## 业务场景
 系统为了支持多租户的场景，需要把不同租户的数据做到隔离。
 
 
 
 在这个场景中，接口和服务是统一部署的一套，不同租户的客户访问的都是统一的小程序和网站，但是会根据他们用户属性，来自动请求对应租户的数据。
 
 - 服务部署
   - 在公有云的场景下，系统仅部署一套，成本是比较低的。如果要给每个租户部署一套系统，从客户端和服务级别进行隔离，就变成了私有云，整体的成本是比较高的。
 - 数据隔离：由于系统是不隔离的，那么为了安全性，不同租户的数据肯定要做到隔离。数据隔离也有不少方案，可以分为3种：
   - 1.分库 ：不同的租户使用不同的库
   - 2.分schema ：使用数据库的schema机制
   - 3.通过字段区分 ：在同一个表中，使用字段来区分
   - 总结：从隔离的安全性讲，从1到3是逐步递减的，从实现成本来讲，从1到3也是逐步递减的。
   如果方案3能保证从代码层面对多租户的区分字段无感知，不管是从老项目迁移过来，还是新建新项目，也是能较好保证数据隔离性的。
   **例如，做一个查询操作，写业务代码的同学无需增加租户字段的筛选项。要做到租户数据的隔离尽量对业务代码无侵入**
   
## 方案概述
   按照上面第三个方案，进行改造。
   - 我们的项目技术栈是：**SpringBoot+Dubbo+MyBatis，数据库是用的mysql**
   - 大概的系统架构如下：
  ![avatar](https://outter.oss-cn-shanghai.aliyuncs.com/%E5%A4%9A%E7%A7%9F%E6%88%B7%E5%9C%BA%E6%99%AF.jpg)
  所以为了让业务层无感知，我们需要从**客户端->controller->service->dao**都有对应的解决方案。
  - 客户端：在客户端我们使用cookie来记录当前用户所在的租户。在用户手动选择租户时，植入cookie，后续每次请求都会带上cookie。
  - controller层：使用interceptor来获取客户端的cookie，然后把租户id放入ThreadLocal中。
  - service层：使用dubbo的**RpcContext**、自定义**Filter**和**ThreadLocal**对象，来解决RPC过程中的租户id传递。
  - dao层：使用mybatis的多租户插件，解决租户id在sql中自动注入。
## 流程说明
各个层级大致的流程如下图所示：
![avatar](https://outter.oss-cn-shanghai.aliyuncs.com/%E5%A4%9A%E7%A7%9F%E6%88%B7%E6%B5%81%E7%A8%8B.jpg)

其中，dubbo的filter执行顺序如下：
![avatar](https://outter.oss-cn-shanghai.aliyuncs.com/dubbo%20filer%E6%89%A7%E8%A1%8C%E9%A1%BA%E5%BA%8F.jpg)

## 总结和资料
