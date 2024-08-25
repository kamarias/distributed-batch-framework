package io.github.kamarias.lock;

/**
 * 分布式锁接口抽象类
 * @author 王玉星
 */
public abstract class AbstractDistributedLockService implements DistributedLockService {

	// 锁过期时间（单位毫秒）
	private static final long EXPIRE_TIME = 30 * 1000L;

	// 锁重试次数
	private static final int RETRY_TIMES = Integer.MAX_VALUE;

	// 获取锁失败后，重试休眠时间（单位毫秒）
	private static final long SLEEP_MILLIS = 500L;

	// 可重入标志
	private static final boolean reentrantLock = false;

	@Override
	public boolean lock(String key) {
		return lock(key, reentrantLock, EXPIRE_TIME, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, boolean reentrantLock) {
		return lock(key, reentrantLock, EXPIRE_TIME, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, boolean reentrantLock, long expire, int retryTimes) {
		return lock(key, reentrantLock, EXPIRE_TIME, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, boolean reentrantLock, long expire) {
		return lock(key, reentrantLock, expire, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, boolean reentrantLock, int retryTimes, long sleepMillis) {
		return lock(key, reentrantLock, EXPIRE_TIME, retryTimes, sleepMillis);
	}

	@Override
	public boolean lock(String key, boolean reentrantLock, int retryTimes) {
		return lock(key, reentrantLock, EXPIRE_TIME, retryTimes, SLEEP_MILLIS);
	}
}
