package com.changhong.gitProject.ForkAndJoin;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SemaphoreDemo {

	private Sync sync;
	public SemaphoreDemo(int max){

		sync = new Sync(max);
	}
	
	//获取信号量
	public void acquire(){
		sync.tryAcquireShared(1);
	}

	//设置信号量
	public void release(){
		sync.tryReleaseShared(1);
	}
	 

	//内部类实现AQS
	class Sync extends AbstractQueuedSynchronizer{
		//信号量最大值
		private int max;
		public Sync(int max){
			this.max = max;
		}
		
		//尝试加锁
		public int tryAcquireShared(int arg){
			int c = getState();
			int next =c + arg;
			if(next < max){
				if(compareAndSetState(c, next))
					return 1;
			}
			return -1;
		}
		
		public boolean tryReleaseShared(int arg){
			//自旋释放锁
			while(true){
				int c = getState();
				int next = c - arg;
				if(next == 0) return false;
				if(compareAndSetState(c, next)){
					return true;
				}
			}
		}
	}


}
