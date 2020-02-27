package com.changhong.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioSocketServer {

	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	public static void main(String args[]){
		try {

			ServerSocket server = new ServerSocket(9999);
			while(!server.isClosed()){
				Socket socket = server.accept();
				System.out.println("链接成功");
				threadPool.execute(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						InputStream input;
						try {
							input = socket.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(input));
							String msg;
							while((msg = reader.readLine())!=null){
								if(msg.equals("")){
									break;
								}
								System.out.println(msg);
							}
							OutputStream out = socket.getOutputStream(); 	
							out.write("HTTP/1.1 200 OK\r\n".getBytes());
							out.write("Content-length: 12\r\n\r\n".getBytes());
							out.write("test request".getBytes());
							out.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
