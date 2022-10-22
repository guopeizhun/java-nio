package com.yl.nio.channel.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/22 14:47
 * @Description: 服务端
 */


public class SelectorDemonServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //切换为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //注册
        Selector sel = Selector.open();
        serverSocketChannel.register(sel, SelectionKey.OP_ACCEPT);

        while (sel.select() > 0) {
            Set<SelectionKey> selectionKeys = sel.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    //获取连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //切换到非阻塞
                    socketChannel.configureBlocking(false);
                    //注册
                    socketChannel.register(sel, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int i;
                    while ((i = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, i));
                        byteBuffer.clear();
                    }
                }
//                selectionKeys.remove(selectionKey);
            }
            iterator.remove();

        }
    }
}
