package com.changhong.gitProject.thread;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;


public class ThreadDemo {

	public static void main(String args[]){
		
		class node{
	        int num;
	        int id;
	        public int getNum() {
	            return num;
	        }
	        public void setNum(int num) {
	            this.num = num;
	        }
	        public int getId() {
	            return id;
	        }
	        public void setId(int id) {
	            this.id = id;
	        }
	        public node(int num, int id) {
	            this.num = num;
	            this.id = id;
	        }
	        public node() {
	        }
		}
		LinkedList<node> q1=new LinkedList<node>();
		node n1 = new node();
		n1.setId(1);
		n1.setNum(3);
		q1.add(n1);
		node n2 = new node();
		n2.setId(2);
		n2.setNum(4);
		q1.add(n2);
		node n3 = new node();
		n3.setId(3);
		n3.setNum(7);
		q1.add(n3);
		
		System.out.println(q1.peekFirst().getId());
		System.out.println(q1.peekLast().getId());
		BlockingQueue<Runnable> quene = new ArrayBlockingQueue<Runnable>(1000);
		ThreadPoolExecutor threadpool  = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,  quene);
		threadpool.shutdown();
		threadpool.setRejectedExecutionHandler(new CallerRunsPolicy());
		new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("22");
			}
		}.run();
			
	}
}
