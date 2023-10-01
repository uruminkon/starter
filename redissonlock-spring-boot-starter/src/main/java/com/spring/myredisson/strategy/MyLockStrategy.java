package com.spring.myredisson.strategy;

import com.spring.myredisson.annotaion.MyLock;
import org.redisson.api.RLock;

public enum MyLockStrategy {
    SKIP_FAST(){
        @Override
        public boolean tryLock(RLock lock, MyLock prop) throws InterruptedException {
            return lock.tryLock(0, prop.leaseTime(), prop.timeUnit());
        }
    },
    FAIL_FAST(){
        @Override
        public boolean tryLock(RLock lock, MyLock prop) throws InterruptedException {
            boolean isLock = lock.tryLock(0, prop.leaseTime(), prop.timeUnit());
            if (!isLock) {
                throw new UnsupportedOperationException("请求太频繁");
            }
            return true;
        }
    },
    KEEP_TRYING(){
        @Override
        public boolean tryLock(RLock lock, MyLock prop) throws InterruptedException {
            lock.lock( prop.leaseTime(), prop.timeUnit());
            return true;
        }
    },
    SKIP_AFTER_RETRY_TIMEOUT(){
        @Override
        public boolean tryLock(RLock lock, MyLock prop) throws InterruptedException {
            return lock.tryLock(prop.waitTime(), prop.leaseTime(), prop.timeUnit());
        }
    },
    FAIL_AFTER_RETRY_TIMEOUT(){
        @Override
        public boolean tryLock(RLock lock, MyLock prop) throws InterruptedException {
            boolean isLock = lock.tryLock(prop.waitTime(), prop.leaseTime(), prop.timeUnit());
            if (!isLock) {
                throw new UnsupportedOperationException("请求太频繁");
            }
            return true;
        }
    },
    ;

    public abstract boolean tryLock(RLock lock, MyLock prop) throws InterruptedException;
}