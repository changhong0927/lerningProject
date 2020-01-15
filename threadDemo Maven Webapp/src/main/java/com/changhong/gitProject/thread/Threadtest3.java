package com.changhong.gitProject.thread;

public class Threadtest3 {

	public static void main(String[] args) {
        test();
    }
    static long sum=0l;
    Boolean falg=false;
    public static void test() {
        PrintNum printNum = new PrintNum();
        System.out.println("開始進入th1線程--");
        Thread th1=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        printNum.printO();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("sum:"+sum);
                }}
        });
        th1.start();

        System.out.println("開始進入th2線程--");
        Thread th2=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        sum= printNum.printJ();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(sum==100) {
                        break;
                    }
                }
            }});
        th2.start();
    }
}
