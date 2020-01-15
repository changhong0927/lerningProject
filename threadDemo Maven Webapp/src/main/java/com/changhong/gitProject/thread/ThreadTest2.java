package com.changhong.gitProject.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest2 {

	 volatile int a=0;
	Thread lastthread = null;
	innerClass inner = new innerClass();
	public void out(){
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				while(true){
					inner.print();
					return;
				}
			}
		},"thread11");
		thread1.start();
		Thread thread2 = new Thread(new Runnable(){
			
			@Override
			public void run() {
				while(true){
					inner.print();
					return;
				}
			}
		},"thread12");
		thread2.start();
		lastthread=thread2;
	}
	public static void main(String args[]) throws InterruptedException{
		ThreadTest2 test = new ThreadTest2();
		test.out();
		
	}
	
	
	class innerClass {
		public synchronized void print(){
			++a;
			System.out.println("线程"+Thread.currentThread().getName()+"执行，a=" + a);
			try {
				System.out.println(lastthread.getName());
				lastthread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastthread = Thread.currentThread();
		}
	}
	
}
