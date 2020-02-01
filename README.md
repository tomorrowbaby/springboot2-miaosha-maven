# springboot2-miaosha-maven
SPringBoot2 商品秒杀项目，前后端分离。登陆模块，注册模块，商品模块，订单模块，秒杀模块。

# 技术栈
        maven + springboot2 + mybatis + Redis + mysql + ajax

# 项目前端 - 简单页面
地址：https://github.com/tomorrowbaby/miaosha-front

# 项目架构
  1.0 项目目前已经完成了基本的单体架构！
  由本地的 开发环境配置测试
        -常规功能模块
        
  2.0 项目的分布式搭建，由 Nginx 负载均衡，反向代理完成了水平扩展的目标
        由 单一的linux+docker 模拟完成  
        -Redis 实现分布式单点登录   
       利用 docker 实现分布式的模拟:  http://mmnnaa.com/?p=1192#more-1192
  
  3.0 目标：SOA架构
