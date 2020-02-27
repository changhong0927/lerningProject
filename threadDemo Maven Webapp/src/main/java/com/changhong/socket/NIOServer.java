package com.changhong.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NIOServer {

	
	public static void main(String args[]){
		
		try {
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(9999));
			List<SocketChannel > socketList = new ArrayList<SocketChannel >();
			
			while(true){
				//If this channel(ServerSocketChannel) is in non-blocking mode then this method(accept) will immediately 
				//return null if there are no pending connections
				SocketChannel socket = serverChannel.accept();
				if(socket!=null){
					socket.configureBlocking(false);
					socketList.add(socket);
				}else{
				
					//没有连接，轮询处理channellist 中的channel
					Iterator<SocketChannel> iterator = socketList.iterator();
					while(iterator.hasNext()){
						SocketChannel so = iterator.next();
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						if(so.read(byteBuffer)!=0){
							//为零代表当前的channel没有数据需要处理，跳过等会在处理
							//注意这里没有接收到数据并不是这个channel就要关闭了，而是客户端还没有发送数据过来、
							//所以当前channel先跳过，等哪次该channel被读取过了，那这个channel才被删除
							continue;
						}
						//有数据则继续从channnel中读取
						//read方法返回值：The number of bytes read, possibly zero, or -1 if the channel has reached end-of-stream
						while(so.isOpen() && so.read(byteBuffer)!=-1){
							// 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
                            if (byteBuffer.position() > 0) break;

						}
						//读完之后如果position=0则说明发了空数据(事实上这一步没有必要，和上面读的过程判断冲突)
						if(byteBuffer.position()==0)continue;
						byteBuffer.flip();
						byte[] bytes = new byte[byteBuffer.limit()];
						byteBuffer.get(bytes);
						System.out.println(new String(bytes));
                        System.out.println("收到数据,来自：" + so.getRemoteAddress());

                        // 响应结果 200
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "Hello World";
                        //将响应结果放入byte数组中转换为ByteBuffer
                        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                        //通过channel将bytebuffer中的数据写出去
                        while (buffer.hasRemaining()) {
                            so.write(buffer);
                        }

					}
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
