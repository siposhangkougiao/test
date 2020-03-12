package com.mtnz.util;

/*
    Created by xxj on 2018\6\30 0030.  
 */
public class Banzhuan {

    public static void main(String[] args) {
        // 一个工厂
        Factory factory = new Factory();
        /**
         * p1线程和p2线程都是由factory这个实例创建的
         * 那么p1调用外部类的getZhuanTou()方法就相当于调用的是factory这个实例的getZhuanTou(),同样的，p2调用的也是factory这个实例的getZhuanTou().
         * 那么这里就出现了两个线程同时访问factory的getZhuanTou()方法。
         * 而factory的getZhuanTou()方法又对zhuanTou这个属性进行了zhuanTou--操作。
         * 换句话说，两个线程同时访问了factory的数据zhuanTou.这时候就可能产生线程安全问题。
         */
        // 同一个工厂的两个工人
        Factory.Person p1 = factory.getPerson();
        Factory.Person p2 = factory.getPerson();
        p1.start();
        p2.start();
    }
}

// 工厂
class Factory {
    int zhuanTou = 20;// 一共20块砖头

    public int getZhuanTou() {
        if (zhuanTou == 0) {
            throw new RuntimeException(Thread.currentThread().getName()+ ",没有砖头搬了!");
        }
        Thread.yield();
        return zhuanTou--;

    }

    // 工人
    class Person extends Thread {
        // 不停的搬砖
        public void run() {
            while (true) {
                // 获取线程名（工人名） 及 剩下砖头数
                System.out.println(getName() + "搬了第" + getZhuanTou() + "块砖头");
                // 当线程的run方法中出现了异常，且我们没有 解决，那么该线程终止并死亡。但不会影响 当前进程中的其他线程。
                Thread.yield();
            }
        }
    }

    // 获取工人
    public Person getPerson() {
        return new Person();
    }
}
