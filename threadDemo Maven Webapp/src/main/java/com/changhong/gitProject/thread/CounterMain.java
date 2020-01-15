package com.changhong.gitProject.thread;

public class CounterMain {

	public static void main(String args[]) throws InterruptedException{
		final AtomicCounter counter = new AtomicCounter(); 
		for(int i = 0; i <10; i++){
			new Thread(() ->{
				for(int j=0; j<10000 ;j++){

					counter.add();
				}
			}).start();
		}
		//线程睡眠6秒钟，等待所有线程执行完毕再打印I的值
		Thread.currentThread().sleep(6000);
		System.out.println(counter.i);
	}
}
