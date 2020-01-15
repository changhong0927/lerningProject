package com.changhong.gitProject.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

//	volatile int  i = 0;
	//有原子性问题
//	public  void add(){
//		i++;
//	}
	//synchronized关键字可以保证add操作是单线程的
//	public synchronized void add(){
//		i++;
//	}
	//ReentrantLock
//	Lock lock = new ReentrantLock();
//	public synchronized void add(){
//		lock.lock();
//		try{
//			
//			i++;
//		}finally{
//
//			lock.unlock();
//		}
//	}
	AtomicInteger i = new AtomicInteger(0);
	public void add(){
		//自动加1
		i.incrementAndGet();
	}
}
