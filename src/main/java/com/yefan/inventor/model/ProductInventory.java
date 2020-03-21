package com.yefan.inventor.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存数 Model
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {
    /**
     * 商品 id
     */
    private  Integer productId;

    /**
     * 库存数量
     */
    private Long inventoryCnt;

}
