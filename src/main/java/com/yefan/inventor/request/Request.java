package com.yefan.inventor.request;

public interface Request {
    void process();

    Integer getProductId();

    boolean isForceRefresh();
}
