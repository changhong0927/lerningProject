package com.changhong.socket;

import java.nio.ByteBuffer;

public class NioDemo {

	public static void main(String args[]){
		//java堆内存中
		ByteBuffer byteBuffer = ByteBuffer.allocate(5);
		//直接内存，在堆外申请
		ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(5);
		
		byteBuffer.put((byte)1);
		byteBuffer.put((byte)2);
		byteBuffer.put((byte)3);
		
		System.out.println("容量" + byteBuffer.capacity()+"   position"+ byteBuffer.position()+"   limit"+byteBuffer.limit());
		
		/*
		 * 由写模式转变为读模式，没有调用flip方法
		 * 导致position没有归零
		 */
		byteBuffer.rewind();
		byte a = byteBuffer.get();
		System.out.println("a:"+a);
		byte b = byteBuffer.get();
		System.out.println("b:"+b);
//		byte c = byteBuffer.get();
//		System.out.println("c:"+c);
		System.out.println("容量" + byteBuffer.capacity()+"   position"+ byteBuffer.position()+"   limit"+byteBuffer.limit());
	
		/**
		 * 调用flip方法，由写模式转变为读模式，将position的位置归零
		 * 以保证读的位置是从头开始的
		 * 
		 */
		byteBuffer.flip();
		byte d = byteBuffer.get();
		System.out.println("d:"+d);
		byte e = byteBuffer.get();
		System.out.println("e:"+e);
		System.out.println("容量" + byteBuffer.capacity()+"   position"+ byteBuffer.position()+"   limit"+byteBuffer.limit());
		// rewind() 重置position为0
        // mark() 标记position的位置
        // reset() 重置position为上次mark()标记的位置

	}
}
