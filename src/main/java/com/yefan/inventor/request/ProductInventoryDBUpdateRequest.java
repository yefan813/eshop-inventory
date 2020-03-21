package com.yefan.inventor.request;


import com.yefan.inventor.model.ProductInventory;
import com.yefan.inventor.service.ProductInventoryService;

/**
 * 数据更新请求
 */
public class ProductInventoryDBUpdateRequest implements Request {

    /**
     * 商品库存
     */
    private ProductInventory productInventory;

    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //删除redis 缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        //更新数据库
        productInventoryService.updateProductInventory(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}
