* @Mapper
* 分布式session
请求头，参数，cookie携带token，兼容APP和PC浏览器端
每次访问延长cookie有效期

* config
@Configuration


第四章
* 为什么秒杀还有建秒杀商品表，秒杀订单，不直接用商品表和订单表
因为促销形式多样，今天秒杀，明天可能1元购，商品表不能一直增加字段。因为新建秒杀表
* VO DTO
VO:视图对象
DTO：数据传输对象
* 数据库存价格一般存整数，不会存小数
* @{${goods.goodsImg}} @代表resources/static目录
* 数据库ID尽量别用自增，容易被人循环，用uuid被人笑话，可以参考snowflake算法，twitter出品

* 在自己Service不提倡引入别的dao，一般引入别的service
* 获取插入的ID 
@SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")


第五章
*JMeter压测
QPS


* DRUID是什么？
是阿里巴巴开源平台上一个数据库连接池实现，它结合了C3P0、DBCP、PROXOOL等DB池的优点，同时加入了日志监控，可以很好的监控DB池连接和SQL的执行情况，可以说是针对监控而生的DB连接池，据说是目前最好的连接池。
* centos redis的安装
* redis，从配置文件读取配置数据
* 利用KeyPrefix来管理redis的key

登录
* 两次MD5
* 参数检验->抛出异常
* 全局异常处理
接口流程中只要出现错误都可以随时抛异常
* 分布式session
WebMvcConfigurerAdapter
HandlerMethodArgumentResolver
* 

秒杀商品
snowflake算法
* 不提倡在service中调用别的dao,可以调用别的service，这样逻辑清晰些，而且别的Service可能对dao进行了包装，比如增加缓存


jmeter压测


### 页面优化
* 缓存
html页面缓存
@RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
@ResponseBody

操作逻辑
高性能网站设计之缓存更新的套路：https://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
有缓存取缓存，没缓存读数据库，渲染成html保存在缓存
一般分页只缓存前几页，因为大多数用户就点前几页
如果缓存有更新，要及时更新缓存

前后端分离：减少从服务器下载html的消耗，只在前端请求数据
1.解决超卖问题，sql中加入库存判断
update miaosha_goods set stock_count=stock_count -1 where goods_id=#{goodsId} and stock_count>0
2.加入userId,goodId唯一索引，有效避免秒杀多次

静态资源优化：
JS/CSS 压缩，减少流量 webpack打包
多个JS/CSS组合，减少请求次数 淘宝tengine软件
CDN就近访问

分库分表:阿里巴巴mycat

接口优化：
1.redis预减库存减少数据库访问
2.内存标记减少redis访问
3.请求入队列缓冲，异步下单，提升用户体验


接口优化思路：
1.系统初始化，将商品库存加载到redis
2.收到请求，redis预减库存，如库存不足则直接返回，反正进入3
3.请求入队，立即返回队列
4.请求出队，生成订单，减少库存
5.客户端轮询，是否秒杀成功


安全优化：
1.秒杀接口地址隐藏
2.数学公式验证码
分散用户请求，延长操作时间，减少并发量
3.接口限流防刷
Interceptor拦截器+AccessLimit自定义注解

HandlerInterceptorAdapter先执行
HandlerMethodArgumentResolver后执行
ThreadLocal线程安全，全局存储
利用response改造返回






20190926
用guest账号登录管理平台后创建一个admin账号，并给与权限。控制台直接创建的kingja账号不能用，不知道为什么
SpringBoot+rabbitmq收发教程



