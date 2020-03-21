package com.yefan.inventor.listener;

import com.yefan.inventor.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * 系统初始化监听器
 * @author yefan
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("===================系统初始化=====================");
        //初始化工作线程池和工作队列
        RequestProcessorThreadPool.init();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
