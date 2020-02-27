package com.changhong.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class BioSocketClient {

	private static Charset charset = Charset.forName("UTF-8");
	public static void main(String args[]) throws UnknownHostException, IOException{
		Socket s = new Socket("localhost", 9999);
		OutputStream out = s.getOutputStream();

		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入：");
		String msg = scanner.nextLine();
		out.write(msg.getBytes(charset)); // 阻塞，写完成
		scanner.close();
		s.close();

	}
}
