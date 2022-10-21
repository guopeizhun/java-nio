package com.yl.nio.channel.file;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Author:letg(pz)
 * @Date: 2022/10/2110:37
 * @Description: 通道之间的数据传输,from fileA  TO fileB
 */


public class FileChannelDemo3 {
    public static void main(String[] args) throws Exception{
        /**
         * transfer to
         */
//        RandomAccessFile fileA = new RandomAccessFile("C:\\testFile\\fileA.txt", "rw");
//        FileChannel channelA = fileA.getChannel();
//        RandomAccessFile fileB = new RandomAccessFile("C:\\testFile\\fileB.txt", "rw");
//        FileChannel channelB = fileB.getChannel();
//
//        long position = 0;
//        long size = channelA.size();
//        channelB.transferFrom(channelA,position,size);
//
//        System.out.prntln("transfer from completed");
////        channelA.close();i
//        channelB.close();


        /**
         * transfer to
         *
         */

        RandomAccessFile fileA = new RandomAccessFile("C:\\testFile\\fileA.txt", "rw");
        FileChannel channelA = fileA.getChannel();
        RandomAccessFile fileB = new RandomAccessFile("C:\\testFile\\fileB.txt", "rw");
        FileChannel channelB = fileB.getChannel();

        long position = 0;
        long size = channelA.size();
        channelA.transferTo(position,size,channelB);
        System.out.println("transfer to completed");
        channelA.close();
        channelB.close();


    }
}

