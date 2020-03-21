package com.yefan.inventor.request;

import com.yefan.inventor.model.ProductInventory;
import com.yefan.inventor.service.ProductInventoryService;

public class ProductInventoryCacheRefreshRequest implements Request {
    /**
     * 商品库存
     */
    private Integer productId;

    private ProductInventoryService productInventoryService;

    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = false;
    }

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService, boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }

    public boolean getForceRefresh() {
        return forceRefresh;
    }

    public void setForceRefresh(boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        //查询数据库
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        if(productInventory == null){
            return;
        }
        //写入缓存
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }
}
