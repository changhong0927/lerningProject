package com.changhong.gitProject.ForkAndJoin;

public class SemaphoreTestMain {

	
	static SemaphoreDemo semaphore = new SemaphoreDemo(5);
	public static void main(String args[]){
		for(int i=0;1<=100;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						semaphore.acquire();//获取信号量，实质上是抢锁
						
					}catch(Exception e){
						
					}
					
				}
			}).start();
		}
	}
}
