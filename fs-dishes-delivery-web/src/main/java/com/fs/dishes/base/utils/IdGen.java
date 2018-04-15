package com.fs.dishes.base.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
public class IdGen{

	/**
	 * 初始时间戳：2017-1-1 0:0:0
	 * 一经定义，不可修改
	 */
	private static final long initTimeMillis = 1483200000929L;
	/**
	 * 进程。
	 * 这里也可以手动指定每台实例的ID号；或者通过ZK的临时递增节点自动获取。
	 * 固定值
	 */
	private static final int pid = 3;
	/**
	 * 计数器
	 * 需要保证线程安全
	 */
	private static volatile AtomicLong counter = new AtomicLong(0);
	private static volatile long currentTimeMillis = System.currentTimeMillis() - initTimeMillis;
	private static volatile long lastTimeMillis = currentTimeMillis;
	/**
	 * 无锁模式
	 * @return
	 */
	public static long nextId() {
		long series;
		while (true) {
			if (lastTimeMillis == currentTimeMillis) {
				series = counter.incrementAndGet();
				if (series >= (1 << 12) - 1) {//同一毫秒内可能达到series最大值
					while (lastTimeMillis == currentTimeMillis) {//等待到下一毫秒
						currentTimeMillis = System.currentTimeMillis() - initTimeMillis;
					}
					counter.compareAndSet(series, 0);
					continue;
				}
				break;
			}
			lastTimeMillis = currentTimeMillis;
		}
		return (currentTimeMillis << 8) | (pid << 12) | series;
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
