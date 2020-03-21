package com.yefan.inventor.controller;

import com.yefan.inventor.model.ProductInventory;
import com.yefan.inventor.model.User;
import com.yefan.inventor.request.ProductInventoryCacheRefreshRequest;
import com.yefan.inventor.request.ProductInventoryDBUpdateRequest;
import com.yefan.inventor.request.Request;
import com.yefan.inventor.service.ProductInventoryService;
import com.yefan.inventor.service.RequestAsyncProcessService;
import com.yefan.inventor.service.UserService;
import com.yefan.inventor.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户Controller控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping
@Slf4j
public class ProductInventoryController {

	@Autowired
	private ProductInventoryService productInventoryService;

	@Autowired
	private RequestAsyncProcessService requestAsyncProcessService;

	@RequestMapping("/updateProductInventory")
	@ResponseBody
	public Response updateProductInventory(ProductInventory productInventory) {
		log.info("-----------------------更新商品库存-----------------productId[{}],cnt:[{}]"
				,productInventory.getProductId(),productInventory.getInventoryCnt());
		Response response = null;
		try {
			Request request = new ProductInventoryDBUpdateRequest(
					productInventory, productInventoryService);
			requestAsyncProcessService.process(request);
			response =  new Response(Response.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(Response.FAILED,e.getMessage());
		}
		return response;
	}
	
	@RequestMapping("/getProductInventory")
	@ResponseBody
	public ProductInventory getProductInventory(Integer productId) {
		try {
			Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);
			requestAsyncProcessService.process(request);


			long startTime = System.currentTimeMillis();
			long endTime = 0;
			long waitTime = 0;
			while (true) {
				if(waitTime > 200) {
					break;
				}
				ProductInventory productInventoryCache = productInventoryService.getProductInventoryCache(productId);
				if (productInventoryCache != null) {
					return productInventoryCache;
				}
				//继续等待
				else {
					Thread.sleep(20);
					endTime = System.currentTimeMillis();
					waitTime = endTime - startTime;
				}
			}
			//尝试从数据库读取数据
			ProductInventory productInventory = productInventoryService.findProductInventory(productId);
			if(null != productInventory){
				request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, true);
				requestAsyncProcessService.process(request);
				return productInventory;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ProductInventory(productId, -1L);
	}
	
}
