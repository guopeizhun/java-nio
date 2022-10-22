package com.yl.nio.channel.selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/22 14:24
 * @Description: 注册的通道必须为非阻塞的，filechannel为阻塞通道，不能注册
 */


public class SelectorDemo1 {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //bind
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //register
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //查询已经就绪的通道
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        for (SelectionKey selectionKey : selectionKeys) {
            if(selectionKey.isAcceptable()){
                if(selectionKey.isReadable()){
                    System.out.println("可读");
                }
                if(selectionKey.isConnectable()){
                    System.out.println("可连接");
                }
                //......
            }
        }

    }
}
