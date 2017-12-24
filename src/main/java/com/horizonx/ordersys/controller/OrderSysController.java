package com.horizonx.ordersys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.horizonx.ordersys.model.Order;
import com.horizonx.ordersys.model.Orderdetail;
import com.horizonx.ordersys.model.Product;
import com.horizonx.ordersys.model.OrderRequest;
import com.horizonx.ordersys.model.User;
import com.horizonx.ordersys.service.OrderService;
import com.horizonx.ordersys.service.ProductService;
import com.horizonx.ordersys.service.UserService;

@RestController
public class OrderSysController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	UserService usrService;
	
	@Autowired
	ProductService prdService;
	
	@RequestMapping(method=RequestMethod.POST, value="/addUser")
	public void addEmp(@RequestBody User usr ){
		usrService.addEmp(usr);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addProd")
	public void addProd(@RequestBody Product prd ){
		prdService.addProduct(prd);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findPrdByName/{name}")
	public List<Product> getProdByName(@PathVariable String name ){
			return prdService.getProductByName(name); 
	}
	
	/**
	 * It creates an order from 3 input parameter part of RequestOrder and returns the total price
	 *    1 - Customer Id - 1 
	 *    2 - Product Id  - Could be more than 1 
	 *    3 - Quantity    - Associated with each product    
	 * @param obj
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/createOrderWith3Inputs")
	public Double createOrder(@RequestBody OrderRequest obj ){		
		return orderService.createOrder(orderService.prepareOrder(obj));
	}
	
	/**
	 * It creates an order and returns the total price
	 * @param details
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/createOrder")
	public Double createOrder(@RequestBody List<Orderdetail> details ){		
		return orderService.createOrder(details);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/createOrdForEachUser")
	public void createForAllUsers()throws Exception{		
		orderService.createForAll();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/OrdersByCustId/{cust}")
	public List<Order> getOrdersByCustId(@PathVariable String cust){
		List<Order> l = orderService.getOrders(cust);
		for(Order o:l){
			System.out.println(o.getOrderId());
		}
		return l;
	}
	
}
