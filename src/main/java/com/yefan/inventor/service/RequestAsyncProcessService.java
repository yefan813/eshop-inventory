package com.yefan.inventor.service;

import com.yefan.inventor.request.Request;

/**
 * 请求异步执行
 */
public interface RequestAsyncProcessService {
    void process(Request request);
}
