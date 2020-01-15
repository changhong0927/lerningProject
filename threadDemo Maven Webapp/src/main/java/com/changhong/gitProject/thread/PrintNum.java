package com.changhong.gitProject.thread;

public class PrintNum {



    private long num=0l;
    //打印奇数
    public  synchronized  long printJ() throws InterruptedException {
        //当num 是偶数时 ，线程等待
        while(num % 2==0) {
         try {
            this.wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
        }
        System.out.println("打印奇数");
        System.out.println(Thread.currentThread().getName()+" :"+ num);
        num++;
        this.notify();
        return num;
    }
    //打印偶数
    public synchronized void printO() throws InterruptedException {
        //当num 是奇数时 ，线程等待
        while(num % 2==1) {
         try {
            this.wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
        }
        System.out.println("打印偶数");
        System.out.println(Thread.currentThread().getName()+" :"+ num);
        num++;
        this.notify();
    }

}
