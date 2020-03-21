package com.yefan.inventor.service.impl;

import com.yefan.inventor.request.ProductInventoryCacheRefreshRequest;
import com.yefan.inventor.request.ProductInventoryDBUpdateRequest;
import com.yefan.inventor.request.Request;
import com.yefan.inventor.request.RequestQueue;
import com.yefan.inventor.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * 请求异步处理
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    @Override
    public void process(Request request) {
        try {
            //更具 Product 路由到对应的内存队列
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            //将请求放入到队列中，完成路由操作
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }
}
