package com.yefan.inventor.thread;


import com.yefan.inventor.request.Request;
import com.yefan.inventor.request.RequestQueue;

import java.util.concurrent.*;

/**
 * 请求处理线程：单例
 * @author yefan
 */
public class RequestProcessorThreadPool {

    private ExecutorService threadPool = new ThreadPoolExecutor(10,10,10,
            TimeUnit.HOURS,new LinkedBlockingDeque<>());



    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < 10; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            requestQueue.addQueue(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    private static class Singleton {
        private static RequestProcessorThreadPool instance;
        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }


    /**
     * 利用JVM内部类初始化的机制
     * 保证多线程并发执行，内部类初始化也只发生一次
     * 保证是单例的
     * @return
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    public static void init(){
        getInstance();
    }
}
