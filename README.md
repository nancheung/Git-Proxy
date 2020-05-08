![GitProxy](https://img.shields.io/badge/Git%20Proxy-v0.0.1-green.svg)
![language](https://img.shields.io/badge/language-java-orange.svg)
![stars](https://img.shields.io/github/stars/nancheung97/Git-Proxy)
![platform](https://img.shields.io/badge/platform-linux%20%7C%20windows-lightgrey)
![license](https://img.shields.io/github/license/nancheung97/Git-Proxy)



# Git Proxy

Git Proxy是用于代理Git操作的一个项目。可以部署在网速通畅的机器上，通过GitProxy提交Git链接，GitProxy将给您一个带有Git信息的ZIP压缩包。



# Git Proxy诞生

> 我们都知道因为一些不可描述的原因，国内下载GitHub的速度非常慢。
> 虽然有挂代理、利用码云中转后再重新指定remote等方法加速下载，但是终归不是特别方便。
> 因此决定写一个小玩意来部署在网路畅通的小鸡上，提供在线接口提供代理Git的目的。

这个项目考虑到设计多线程间的同步、协调等问题，会是一个不错的技术挑战！



# 已实现功能：

1. 提供接口传入GitHub地址，异步下载打包后，返回Zip包下载地址；

1. 自动异步清理下载后的git文件和打包的zip文件等临时文件；

1. 下载任务进入异步队列。

   ……

# 待实现功能：
1. 自动初始化Git环境

1. 前端ui可视化操作（需前端）；

1. 多用户注册登录，管理自己的Git下载历史；

1. 下载后进入打包异步队列；

1. 用户可以查看任务状态：等待下载、下载中、等待打包、打包中、待下载等状态；

1. 可以配置git账号密码/密钥，能下载私有项目

1. 使用CDN缓存下载文件，节省服务器流量

    ……