package com.yefan.inventor.service;


import com.yefan.inventor.model.ProductInventory;

/**
 *
 */
public interface ProductInventoryService {

    /**
     * update kucun
     * @param productInventory
     */
    void updateProductInventory(ProductInventory productInventory);


    /**
     * remove product inventory cache
     * @param productInventory
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    ProductInventory findProductInventory(Integer productId);

    void setProductInventoryCache(ProductInventory productInventory);


    ProductInventory getProductInventoryCache(Integer productId);
}
