package com.changhong.gitProject.thread;

import java.lang.ref.Reference;

public class SynchronizedDemo {

	static int length = 0;
	public static void formatLink(LinkCase current){
		if(length<11){
			LinkCase ca = new LinkCase();
			ca.setValue(Math.random()*10);
			current.setNext(ca);
			length = length+1;
			formatLink(ca);
		}
	}
	
	public static void main(String args[]){
		//StringBuffer 由于内部是由synchronize关键字来保证线程安全的
		//在单线程情况下，这种频繁的加锁和释放锁的操作是没有意义的
		//jit编译期会在多次运行后进行锁消除的优化
//		StringBuffer sbuffer = new StringBuffer();
//		sbuffer.append("a");
//		sbuffer.append("a");
//		sbuffer.append("a");
//		sbuffer.append("a");
//		sbuffer.append("a");
//		sbuffer.append("a");
		
			LinkCase ca = new LinkCase();
			ca.setValue(Math.random());
			formatLink(ca);
			
			LinkCase c1 = new LinkCase();
			LinkCase p = new LinkCase();
			LinkCase q = null;
			c1 = ca;
			p=ca;
			while(c1.getNext() != null){
			if(q==null){
				
			}
			if(p.getNext().getValue()>=q.getValue()){
				q.setNext(p.getNext());
				
			}else{
				
			}
				
				
				
				
				
				
				
				
				
				
				System.out.println(c1.getValue());
				c1 = c1.getNext();
			}
	}
}
