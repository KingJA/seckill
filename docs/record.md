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