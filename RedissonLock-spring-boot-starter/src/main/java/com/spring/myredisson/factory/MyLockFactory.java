package com.spring.myredisson.factory;

import com.spring.myredisson.constants.MyLockType;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static com.spring.myredisson.constants.MyLockType.*;

@Component
public class MyLockFactory {
    public final Map<MyLockType, Function<String,RLock>> factory;

    public MyLockFactory(RedissonClient redissonClient) {
        this.factory = new EnumMap<>(MyLockType.class);
        this.factory.put(RE_ENTRANT_LOCK, redissonClient::getLock);
        this.factory.put(FAIR_LOCK, redissonClient::getFairLock);
        this.factory.put(READ_LOCK, name -> redissonClient.getReadWriteLock(name).readLock());
        this.factory.put(WRITE_LOCK, name -> redissonClient.getReadWriteLock(name).writeLock());
        this.factory.put(SPIN_LOCK, redissonClient::getSpinLock);
    }

    public RLock getLock(MyLockType lockType, String name){
        return factory.get(lockType).apply(name);
    }
}
