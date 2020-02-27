package com.changhong.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOSelectorServer {

	public static void main(String args[]){
		try {
			//1.创建serversocketchannel,并设置为非阻塞,绑定端口
			ServerSocketChannel serverSocket = ServerSocketChannel.open();
			serverSocket.configureBlocking(false);
			serverSocket.socket().bind(new InetSocketAddress(9999));
			System.out.println("服务端启动");
			//2.创建选择器selector，并将serversocketchannel的accept注册上去
			/**
			 * 这个selector里面负责监听包括channel和stream的事件
			 */
			Selector selector = Selector.open();
			/**
			 * register方法的前提：
			 * This method first verifies that this channel is open 
			 * and that the given initial interest set is valid（必须保证感兴趣的事件是所注册channel支持的）. 
			 */
			serverSocket.register(selector,SelectionKey.OP_ACCEPT, serverSocket);
			//3.事件轮询
			while(true){

				//select方法阻塞等待直到有监听的事件通知为止
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey key = iterator.next();
					iterator.remove();
					//由key来判断响应的事件
					
					if(key.isAcceptable()){//channel的accept事件
						ServerSocketChannel socekt = (ServerSocketChannel) key.attachment();
						SocketChannel socketChannel = socekt.accept();
						//当监听到有channel的accept事件时，建立连接，并将socketchannel的read时间注册到selector中
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
						System.out.println("新连接建立");
					}
					if(key.isReadable()){
						SocketChannel socketChannel = (SocketChannel) key.attachment();
						ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
						while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
                            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
                            if (requestBuffer.position() > 0) break;
                        }
                        if(requestBuffer.position() == 0) continue; // 如果没数据了, 则不继续后面的处理
                        requestBuffer.flip();
                        byte[] content = new byte[requestBuffer.limit()];
                        requestBuffer.get(content);
                        System.out.println(new String(content));
                        System.out.println("收到数据,来自：" + socketChannel.getRemoteAddress());
                        // TODO 业务操作 数据库 接口调用等等

                        // 响应结果 200
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "Hello World";
                        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                        while (buffer.hasRemaining()) {
                            socketChannel.write(buffer);
                        }

						
					}
					
				}
				selector.selectNow();
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
