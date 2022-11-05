package com.yl.nio.channel.multiThreading;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:letg(pz)
 * @Date: 2022/11/5 10:24
 * @Description:多线程服务器
 */


public class Server {
    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8999));
        Worker[] workers = new Worker[5];

        for (int i = 0; i < 5; i++) {
            workers[i] = new Worker("worker-"+i);
        }

        AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();
            Set<SelectionKey> selectionKeys = boss.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isAcceptable()) {
                    System.out.println("建立连接中...");
                    SocketChannel sc = ssc.accept();
                    System.out.println("建立成功-->" + sc);
                    sc.configureBlocking(false);
                    System.out.println("建立连接中...");
                   workers[index.getAndIncrement() % workers.length].register(sc);
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile Boolean isStart = false;

        //        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        public Worker(String name) {
            this.name = name;
        }

        //初始化
        public void register(SocketChannel sc) throws IOException {
            if (!isStart) {
                thread = new Thread(this, name);
                selector = Selector.open();
                isStart = true;
                thread.start();
            }
            //向队列添加任务
//           queue.add(()->{
//               try {
//
//               } catch (ClosedChannelException e) {
//                   e.printStackTrace();
//               }
//           });
            selector.wakeup();
            sc.register(selector, SelectionKey.OP_READ, null);
        }

        @Override
        public void run() {
            try {
                selector.select();
//                Runnable task = queue.poll();
//                if(task != null){
//                    task.run();
//                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if (next.isReadable()) {
                        System.out.println("reading...");
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        SocketChannel channel = (SocketChannel) next.channel();
                        channel.read(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.capacity()));
                        System.out.println("readed...");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
