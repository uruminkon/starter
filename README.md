# Design Pattern

#### 介绍
自定义封装组件，对框架或者其他技术进行相应的封装，用于方便日常开发并且封装为相应的starter

#### 应用
| 封装组件 | 说明 | 对应模块 |
| --- | --- | --- |
| Redisson分布式锁注解 | 通过注解和aop实现对Redisson的封装，由于Redisson存在不同的锁类型和锁获取失败策略，并且需要对锁名称需要灵活使用，采用简单工厂进行多类型锁生产；使用锁失败策略对获取锁失败后进行处理；对锁名称采用Spel表达式动态生成 | redissonlock |
