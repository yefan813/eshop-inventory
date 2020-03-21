package com.yefan.inventor.mapper;


import com.yefan.inventor.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

/**
 * 库存数 DAO
 */
public interface ProductInventoryMapper {

    /**
     * 更新库存数量
     *
     * @param productInventory
     */
    void updateProductInventory(ProductInventory productInventory);

    ProductInventory findProductInventory(@Param("productId") Integer productId);
}
