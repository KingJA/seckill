第二章


第三章
* 密码双重MD5加密
加入common依赖
客户端MD5加密：明文+盐
服务端MD5加密：客户端MD5+随机盐
客户端MD5为了不使密码明文传输。但是JS加密是可以看到源码的，所有要用浏览器插件来提高安全性。
服务器MD5加密是为了防止数据库被黑后直接获取密码
* 集成登录HTML
* 分布式session，用redis缓存token和user
* JSR303参数验证
* 全局异常处理
validator 自定义验证注解@IsMobile
* 全局请求参数添加
* 如何登陆，以前的token怎么处理?
* WebConfig implements WebMvcConfigurer

第四章
* 秒杀没减少产品库存，只减少了秒杀产品库存

第五章
* 1.JMeter入门
    下载地址：http://jmeter.apache.org
* 2.自定义变量模拟多用户
* 3.JMeter命令行使用
* 4.Redis压测工具redis-benchmark
* 5.Spring Boot打WAR包

