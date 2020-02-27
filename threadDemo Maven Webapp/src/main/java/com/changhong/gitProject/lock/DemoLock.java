package com.changhong.gitProject.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DemoLock {

	static Lock lock = new ReentrantLock();
	
	public static void main(String args[]) throws InterruptedException{
		lock.lock();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//一直尝试拿锁，直到成功
//				lock.lock();
//				System.out.println("抢到了");
				
				//一直尝试，直到有别的线程中断
				try {
					lock.lockInterruptibly();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//只尝试一次，成功与否都只尝试一次
//				boolean result = lock.tryLock();
//				System.out.println("是否抢到锁" + result);
				
				//在设定时间内一直尝试，超过时间没有成功则放弃抢锁
//				try {
//					boolean result1 = lock.tryLock(8000, TimeUnit.MILLISECONDS);
//					System.out.println("是否抢到锁" + result1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				
			}
		});
		thread.start();
		Thread.currentThread().sleep(6000);
		thread.interrupt();
		
		Map<String,String> map = new HashMap<String, String>();
		Map<String,String> concurrentHashMap = new ConcurrentHashMap<String, String>();
		
	}
}
