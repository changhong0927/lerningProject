package com.changhong.gitProject.thread;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import sun.misc.Unsafe;

public class AtomicCounter {

	public volatile int i =0;
	//获取unsafe对象，目的是在add操作中通过cas操作来达到原子操作的目的
	private static Unsafe unsafe;
	//偏移量，unsafe类的cas操作需要通过偏移量来确定要修改的变量，实质上可以理解为该变量在内存中位置
	private static long valueOffset;
	
	static {
		//Unsafe类虽然提供了getUnsafe实例的方法，但是这个方法只能jdk使用，实际开发中不能使用
//		unsafe = Unsafe.getUnsafe();
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			unsafe = (Unsafe) theUnsafe.get(null);
			valueOffset = unsafe.objectFieldOffset(AtomicCounter.class.getDeclaredField("i"));
			 
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add(){

		//cas操作在正常情况下两个线程同时操作变量时必有一个线程操作失败，所以必须用自旋来保证操作执行成功
		for(;;){
			//自旋操作里面每次读取最新的值，然后对最新的值进行cas写入
			int current = unsafe.getIntVolatile(this, valueOffset);
			if(unsafe.compareAndSwapInt(this, valueOffset, current, current+1)){
				break;
			}
		}
		
	}
}
