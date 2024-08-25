package io.github.kamarias.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ZooKeeperDistributedLockService extends AbstractDistributedLockService {

    private final CuratorFramework client;

    private static final String ROOT_PATH = "/distributedLock/";

    private final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperDistributedLockService.class);

    private final ThreadLocal<Boolean> REENTRANT_LOCK = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private final ThreadLocal<Boolean> threadLock = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private ThreadLocal<InterProcessMutex> lock;

    public ZooKeeperDistributedLockService(CuratorFramework client) {
        this.client = client;
    }


    @Override
    public boolean lock(String key, boolean reentrantLock, long expire, int retryTimes, long sleepMillis) {
        boolean result = false;
        InterProcessMutex lockInstance = new InterProcessMutex(client, ROOT_PATH + key);
        lock.set(lockInstance);
        // 可重入逻辑
        if (reentrantLock) {
            REENTRANT_LOCK.set(true);
            try {
                while ((!result) && --retryTimes > 0) {
                    result = lock.get().acquire(sleepMillis, TimeUnit.MILLISECONDS);
                    if (!result) {
                        LOGGER.debug("lock failed, retrying..." + retryTimes + 1);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("try reentrantLock Exception is", e);
            }
            threadLock.set(result);
            return result;
        }


        // 非可重入锁逻辑
        try {
            while ((!result) && --retryTimes > 0) {
                if (!threadLock.get()) {
                    result = lock.get().acquire(sleepMillis, TimeUnit.MILLISECONDS);
                    if (!result) {
                        LOGGER.debug("lock failed, retrying..." + retryTimes + 1);
                    }
                } else {
                    LOGGER.warn("lock failed, reentrantLock not allow");
                    return false;
                }
            }
        } catch (Exception e) {
            LOGGER.error("try not ReentrantLock Exception is", e);
        }
        threadLock.set(result);
        return result;
    }

    @Override
    public boolean releaseLock(String key) {
        boolean result = false;
        InterProcessMutex mutex = lock.get();
        try {
            mutex.release();
            result = true;
        } catch (Exception e) {
            LOGGER.error("try releaseLock Exception is", e);
        } finally {
            REENTRANT_LOCK.remove();
            threadLock.remove();
            lock.remove();
        }
        return result;
    }

}
