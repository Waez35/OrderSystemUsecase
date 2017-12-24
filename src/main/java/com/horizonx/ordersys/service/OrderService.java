package com.horizonx.ordersys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizonx.ordersys.model.Order;
import com.horizonx.ordersys.model.Orderdetail;
import com.horizonx.ordersys.model.ProdRequest;
import com.horizonx.ordersys.model.Product;
import com.horizonx.ordersys.model.OrderRequest;
import com.horizonx.ordersys.model.User;
import com.horizonx.ordersys.repository.OrderRepository;
import com.horizonx.ordersys.repository.OrderdetailsRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orRepo;
	
	@Autowired
	OrderdetailsRepository ordDetailsRepo;
	
	@Autowired
	ProductService prdService;
	
	@Autowired
	UserService userService;
	
	/**
	 * This method will create an order for a customer, calculates and returns the total order price.
	 * @param ordList
	 */
	@Transactional(rollbackFor = Exception.class)
	public double createOrder(List<Orderdetail> ordList) {								
		double price = 0;
		try{			
			Order order = ordList.get(0).getOrder();
			orRepo.save(order);			
			//save prod and other details
			for(Orderdetail o:ordList){				
				Product prd = o.getProduct(); 
				if(null != prd){
					Integer id =prd.getProductId();
					prd = prdService.getProduct(id);
					if(prd==null){throw new Exception("No Record found for Prdouct id ! : "+id);}
					price= price + (prd.getPrice()*o.getQuantity());
				}
			}			
			ordDetailsRepo.save(ordList);
			//save prod details	
		}catch(Exception e){
			e.printStackTrace();
		}			
		return price;
	}

	/**
	 * 	It returns Order ids for a customer	
	 * @param customer_id
	 * @return
	 */
	public List<Order> getOrders(String customer_id) {	
		return 	orRepo.searchByCustomerid(customer_id);	
	}

	/*
	 * It Construct the object
	 */
	public List<Orderdetail> prepareOrder(OrderRequest obj) {		
		String custId=obj.getCustomer_id();
		Order ord = buildOrderObject(custId);
		List<Orderdetail> list = new ArrayList<Orderdetail>();
		try{
			checkNull(custId);
			for(ProdRequest pr:obj.getList()){					
				Integer prod_id=pr.getProd_id();
				Integer quantity=pr.getQuantity();
				checkValid(prod_id,quantity);
				Orderdetail ordd = buildDetailObject(prod_id,quantity,ord);
				list.add(ordd);
			}
			System.out.println(new ObjectMapper().writeValueAsString(ord));	
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
    /**
     * Constructs the detail Obj
     * @param prod_id
     * @param quantity
     * @param ord
     * @return
     */
	private Orderdetail buildDetailObject(Integer prod_id, Integer quantity, Order ord) {
		Orderdetail ordd = new Orderdetail();
		Product p = new Product();
		p.setProductId(prod_id);
		ordd.setQuantity(quantity);
		ordd.setProduct(p);
		ordd.setOrder(ord);		
		return ordd;		
	}

	/**
	 * Constructs the Order Obj
	 * @param custId 
	 * @return Order
	 */
	private Order buildOrderObject(String custId){
		User usr=new User();
		usr.setUserId(custId);				
		Order order = new Order();
		order.setUser(usr);
		order.setOrderId((new Random().nextInt(33)+3290)); // To be done at the DB side.
		order.setOrderDate(new Date());
		return order;
	}
		
	private void checkNull(Object obj) throws Exception {
		if(null == obj) throw new Exception("Object NULL" );
	}
	
	private void checkValid(Integer prod_id, Integer quantity) throws Exception {
		if(!(quantity>0 && prod_id > 0 )){		
			throw new Exception("Quantity : "+quantity+" OR Prod Id :"+prod_id+ " Invalid !!");
		}
	}

	/**
	 * It will Create orders for each User with more than one products !!
	 *  #### Make sure you have User and Products in tables;
	 */
	public void createForAll() throws Exception {		
		List<User> users = userService.getAllUsers();	
		List<Product> products = prdService.getAllProd();	
	    if( users.size()>0 && products.size()>0 ){
	    	int plimit = products.size() >=2 ? 2 : 1;
	    	Product[] prds = new Product[plimit];
	    	for(int i=0;i<plimit;i++){
	    		prds[i]=products.get(i);
	    	}
	    	int ordCount=0;
			for(User u:users){	
				OrderRequest req = buildOrderReqObj(u.getUserId(),prds);			
				List<Orderdetail> orderDetails = prepareOrder(req);
				if(orderDetails.size()>0){
					double totalAmt = createOrder(orderDetails);
					ordCount++;					
				}			
			}	
			System.out.println(" @@@@ User Count ="+ users.size() +" Orders Created ="+ordCount);
	    }else{
	    	throw new Exception("Not enough Users or Products to Create Orders!! Users:"+users.size()+" Products:"+products.size());
	    }
	}

	private OrderRequest buildOrderReqObj(String userId, Product[] products) throws Exception {		
		OrderRequest obj = new OrderRequest();		
		List<ProdRequest> pList = new ArrayList<ProdRequest>();		
		obj.setCustomer_id(userId);			
		for(int i=0;i<products.length;i++){
			ProdRequest pr = new ProdRequest();
			pr.setProd_id(products[i].getProductId()); 
			pr.setQuantity(2+i);
			pList.add(pr);							
		}
		obj.setList(pList);
		return obj;
	}
	
}