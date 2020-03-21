package com.yefan.inventor.request;

import com.yefan.inventor.thread.RequestProcessorThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 请求排队的内存队列
 */
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queueList = new ArrayList<ArrayBlockingQueue<Request>>();


    /**
     * 标识 map
     */
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap();


    private static class Singleton {
        private static RequestQueue instance;
        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }


    /**
     * 利用JVM内部类初始化的机制
     * 保证多线程并发执行，内部类初始化也只发生一次
     * 保证是单例的
     * @return
     */
    public static RequestQueue getInstance() {
        return RequestQueue.Singleton.getInstance();
    }

    /**
     * 添加内存队列
     * @param queue
     */
    public void addQueue(ArrayBlockingQueue<Request> queue) {
        queueList.add(queue);
    }

    public int queueSize() {
       return queueList.size();
    }

    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queueList.get(index);
    }

    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }

}
