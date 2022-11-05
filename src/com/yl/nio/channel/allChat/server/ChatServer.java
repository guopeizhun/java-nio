package com.yl.nio.channel.allChat.server;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/24 13:38
 * @Description:
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天室服务器
 */
public class ChatServer {

    private void startServer() throws Exception {
        //创建selector
        Selector selector = Selector.open();
        //创建ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //绑定端口
        channel.bind(new InetSocketAddress(8888));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功");
        //循环等待连接
        for (; ; ) {
            int readChannels = selector.select();
            if (readChannels == 0) {
                continue;
            }

            //获取可用的channel
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isAcceptable()) {

                    acceptOpertor(channel, selector);
                }
                if (next.isReadable()) {
                    readOperator(channel, next, selector);
                    //todo

                }
            }
        }
        //根据就绪状态完成业务
    }

    private void readOperator(ServerSocketChannel channel, SelectionKey selectionKey, Selector selector) throws Exception {
        //获取已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int readLen;
        String msg = "";
        while ((readLen = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            msg += Charset.forName("UTF-8").decode(buffer);
            socketChannel.register(selector, SelectionKey.OP_READ);

            if (msg.length() > 0) {
                System.out.println(msg);
                castToOtherClient(msg, selector, socketChannel);
            }
        }

    }

    private void castToOtherClient(String msg, Selector selector, SocketChannel socketChannel) throws IOException {
        Set<SelectionKey> selectionKeys = selector.keys();
        for (SelectionKey key : selectionKeys) {
         Channel channel =  key.channel();
         if(channel instanceof SocketChannel && channel != socketChannel){
             ((SocketChannel) channel).write(Charset.forName("UTF-8").encode(msg));
         }
        }
    }

    private void acceptOpertor(ServerSocketChannel channel, Selector selector) throws Exception {
        //接入窗台，创建SocketChanne
        SocketChannel accept = channel.accept();
        //非阻塞模式
        accept.configureBlocking(false);
        //把channel注册到selector上
        accept.register(selector, SelectionKey.OP_READ);

        accept.write(Charset.forName("UTF-8").encode("欢迎你进入聊天室"));
    }

    public static void main(String[] args) {
        try {
            new ChatServer().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
