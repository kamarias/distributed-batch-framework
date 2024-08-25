package io.github.kamarias.lock;

/**
 * 基于Redis的分布式锁接口
 * @author 王玉星
 */
public interface DistributedLockService {

	/**
	 * 获取锁
	 * @param key redis key
	 * @return 是否成功
	 */
	boolean lock(String key);


	/**
	 * 获取锁
	 * @param key 锁key
	 * @param reentrantLock 是否可重入
	 * @return 返回加锁情况
	 */
	boolean lock(String key, boolean reentrantLock);

	/**
	 * 获取锁
	 * @param key redis key
	 * @param retryTimes 获取锁重试次数
	 * @return 是否成功
	 */
	boolean lock(String key, boolean reentrantLock, int retryTimes);

	/**
	 * 获取锁
	 * @param key redis key
	 * @param retryTimes 获取锁重试次数
	 * @param sleepMillis 获取锁失败后 如果重试需要休眠的时间
	 * @return 是否成功
	 */
	boolean lock(String key, boolean reentrantLock, int retryTimes, long sleepMillis);

	/**
	 * 获取锁
	 * @param key redis key
	 * @param expire 锁的过期时间
	 * @return 是否成功
	 */
	boolean lock(String key, boolean reentrantLock, long expire);

	/**
	 * 获取锁
	 * @param key redis key
	 * @param expire 锁的过期时间
	 * @param retryTimes 获取锁重试次数
	 * @return 是否成功
	 */
	boolean lock(String key, boolean reentrantLock, long expire, int retryTimes);

	/**
	 * 获取锁
	 * @param key redis key
	 * @param expire 锁的过期时间
	 * @param retryTimes 获取锁重试次数
	 * @param sleepMillis 获取锁失败后 如果重试需要休眠的时间
	 * @return 是否成功
	 */
	boolean lock(String key, boolean reentrantLock, long expire, int retryTimes, long sleepMillis);

	/**
	 * 释放锁
	 * @param key redis key
	 * @return 是否成功
	 */
	boolean releaseLock(String key);
}
