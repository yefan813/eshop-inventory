package com.yefan.inventor.thread;

import com.yefan.inventor.request.ProductInventoryCacheRefreshRequest;
import com.yefan.inventor.request.ProductInventoryDBUpdateRequest;
import com.yefan.inventor.request.Request;
import com.yefan.inventor.request.RequestQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;


/**
 * 执行请求工作线程
 */
@Slf4j
public class RequestProcessorThread implements Callable {

    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try{
            while (true) {
                Request request = queue.take();
                log.info("=======获取到请求====请求类型为:[{}]", request.getClass().getName());
                boolean forceRefresh = request.isForceRefresh();
                if(!forceRefresh){
                    quchong(request);
                }
                request.process();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private void quchong(Request request) {
        //做读请求去重
        RequestQueue requestQueue = RequestQueue.getInstance();
        Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
        if(request instanceof ProductInventoryDBUpdateRequest){
            flagMap.put(request.getProductId(),true);
        }else if(request instanceof ProductInventoryCacheRefreshRequest) {
            Boolean isExitUpdate = flagMap.get(request.getProductId());
            //如果不存在直接相当于是读数据库
            if (isExitUpdate == null) {
                flagMap.put(request.getProductId(),false);
            }
            //如果isExitUpdate不为空 且 为 true 说明之前有一个数据库更新请求
            if (isExitUpdate != null && isExitUpdate) {
                flagMap.put(request.getProductId(),false);
            }
            //如果isExitUpdate不为空 且 为 false 说明之前有一个数据库更新请求 + 数据库缓存刷新请求
            if (isExitUpdate != null && !isExitUpdate) {
                return;
            }

        }
    }
}
