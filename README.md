# MyMiniDubbo
#最近在学习Dubbo,跟着官方网站的源码，就萌生了一个写一个简单的Dubbo来记录我的Dubbo学习之旅，于是，就有了这个简单的Demo

#关于远程方法调用(RPC)的过程，在传输协议上都是基于TCP协议进行传输，除此之外Demo中还使用了一个register.txt文本文件来简单的模拟了一个远程中心，
所以，整个demo中使用到RPC的过程只有一个

#关于消费者向提供者发送方法信息并返回执行结果的这个过程，Demo中的RPC的传输方式提供了两种，一种是基于http协议的传输，在代码中使用了一个内嵌的tomcat来实现通过http传输数据，另一种则是基于Netty框架，通过Tcp协议(套接字)来传输信息。

#使用动态代理是代码更美观

#关于如何使用java的spi机制来进行配置加载将在下次提交完成,Dubbo实际上自己实现了一套spi机制。

