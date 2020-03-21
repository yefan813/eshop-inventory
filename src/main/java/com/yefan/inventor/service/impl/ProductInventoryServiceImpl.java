package com.yefan.inventor.service.impl;

import com.yefan.inventor.dao.RedisDAO;
import com.yefan.inventor.mapper.ProductInventoryMapper;
import com.yefan.inventor.model.ProductInventory;
import com.yefan.inventor.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.delete(key);
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.set(key, String.valueOf(productInventory.getInventoryCnt()));
    }

    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        Long inventCnt = 0L;
        String key = "product:inventory:" + productId;
        String cnt = redisDAO.get(key);
        if(cnt != null && !"".equals(cnt)){
            inventCnt = Long.valueOf(cnt);
            return new ProductInventory(productId,inventCnt);
        }
        return null;
    }
}
