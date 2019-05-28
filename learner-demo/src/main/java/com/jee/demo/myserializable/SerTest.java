package com.jee.demo.myserializable;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 序列化对象
 *
 */
public class SerTest {
    public static void main(String[] args) throws Exception {
        //serialize();
        deserialize();
    }


    /**
     * 序列化对象到硬盘
     */
    public static void serialize() throws IOException {
        Task t = new Task();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D://tasks"));
        oos.writeObject(t);
        oos.close();
    }

    /**
     * 从硬盘中反序列化对象
     */
    public static void deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D://tasks"));
        ExecutorService pool = Executors.newCachedThreadPool();
        Task t = (Task) ois.readObject();
        pool.execute(t);
        ois.close();
        pool.shutdown();
    }
}

