package com.changhong.gitProject.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class ChanghongLock implements Lock{

	//一个原子类型的对象作为锁
	AtomicReference<Thread> lock = new AtomicReference<Thread>();
	//一个阻塞队列作为线程的挂起等待队列
	BlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();
	
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		//抢锁不成功，加入等待队列
		if(!tryLock()){
			waiters.offer(Thread.currentThread());
			while(true){//由于线程调用park方法，被挂起的线程存在伪唤醒的问题，所以需要用自旋来判断是否是伪唤醒
				if(waiters.peek() == Thread.currentThread()){//线程被唤醒之后判断是否是等待队列头部，如果是，则抢锁，不是，继续挂起

					if(tryLock()){//抢锁成功，线程出队
						waiters.poll();
						return;  
					}else{//失败，继续挂起
						LockSupport.park();
					}
					
				}else{
					
					//加入等待队列的线程还在运行中，需要将该线程挂起
					LockSupport.park();
				}
			}
			
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		//当当前的锁对象为null时，代表锁是空闲的，当前线程获得锁
		return lock.compareAndSet(null, Thread.currentThread());
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		//释放锁，即将锁对象置为null
		if(lock.compareAndSet(Thread.currentThread(), null)){//cas操作始终存在失败的问题
			
			//唤醒等待队列的队头线程
			Thread head = waiters.peek();//读取队头元素，但不出队列
			LockSupport.unpark(head);
		}
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
