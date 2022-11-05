package com.yl.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author:letg(pz)
 * @Date: 2022/11/4 9:50
 * @Description:
 */


public class ServerDemo3 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel open = ServerSocketChannel.open();
        Selector selector = Selector.open();
        ServerSocketChannel socketChannel = open.bind(new InetSocketAddress(8999));
        socketChannel.configureBlocking(false);
        SelectionKey selectionKey = socketChannel.register(selector, 0, null);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    channel.configureBlocking(false);
                    System.out.println("key->>" + selectionKey);
                    SocketChannel accept = channel.accept();
                    System.out.println("连接->>" + accept);
                    SelectionKey register = accept.register(selector, 0, null);
                    register.interestOps(SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer); //如果是-1，客户端就正常断开
                        if (read < 0) {
                            next.cancel();
                        } else {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, read));
                            buffer.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        next.cancel(); //客户端断开需要取消key
                    }
                }


                iterator.remove();


            }
        }
    }
}
