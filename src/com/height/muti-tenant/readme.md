## 业务场景
 系统为了支持多租户的场景，需要把不同租户的数据做到隔离。
 
 ![avatar](https://outter.oss-cn-shanghai.aliyuncs.com/%E5%A4%9A%E7%A7%9F%E6%88%B7%E5%9C%BA%E6%99%AF.jpg)
 
 在这个场景中，接口和服务是统一部署的一套，不同租户的客户访问的都是统一的小程序和网站，但是会根据他们用户属性，来自动请求对应租户的数据。
 
 - 服务部署
   - 在公有云的场景下，系统仅部署一套，成本是比较低的。如果要给每个租户部署一套系统，从客户端和服务级别进行隔离，就变成了私有云，整体的成本是比较高的。
 - 数据隔离：由于系统是不隔离的，那么为了安全性，不同租户的数据肯定要做到隔离。数据隔离也有不少方案，可以分为3种：
   - 分库 ：不同的租户使用不同的库
   - 分schema ：使用数据库的schema机制
   - 通过字段区分 ：在同一个表中，使用字段来区分
  
    //TODO 分析
   
## 方案概述

## 流程说明
![avatar](https://outter.oss-cn-shanghai.aliyuncs.com/dubbo%20filer%E6%89%A7%E8%A1%8C%E9%A1%BA%E5%BA%8F.jpg)

![avatar](https://outter.oss-cn-shanghai.aliyuncs.com/%E5%A4%9A%E7%A7%9F%E6%88%B7%E6%B5%81%E7%A8%8B.jpg)

## 总结和资料
