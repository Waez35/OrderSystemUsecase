package com.horizonx.ordersys;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizonx.ordersys.model.Order;
import com.horizonx.ordersys.model.Orderdetail;
import com.horizonx.ordersys.model.Product;
import com.horizonx.ordersys.model.User;
import com.horizonx.ordersys.repository.OrderRepository;
import com.horizonx.ordersys.repository.OrderdetailsRepository;
import com.horizonx.ordersys.repository.ProductRepository;
import com.horizonx.ordersys.repository.UserRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderSystemApplicationTests {

	@Autowired
	UserRepository usrRepo;
	
	@Autowired
	ProductRepository prdRepo;
	
	@Autowired
	OrderRepository orRepo;
	
	@Autowired
	OrderdetailsRepository orderDRepo;
	
	@Autowired
	ObjectMapper mapper ;//= new ObjectMapper();
	
	//@Test
	public void addUser(){
		User usr = new User();
		usr.setUserId(RandomStringUtils.random(7, true, false)+"@"+RandomStringUtils.random(4, true, false)+".com");
		usr.setFirstName(RandomStringUtils.random(6, true, false));
		usr.setLastName(RandomStringUtils.random(8, true, false));
		usr.setDob("1982-05-"+new Random().nextInt(30));
		usrRepo.save(usr);		
		assertNotNull(usrRepo.findOne(usr.getUserId()));		
	}
	
	
	//@Test
	public void addAndFindProd() throws JsonProcessingException{		
		Product prd = new Product();
		prd.setProductId(new Random().nextInt(100)+500);
		prd.setPrice(new Random().nextDouble()+100);
		prd.setName("Some"+RandomStringUtils.random(3, true, true));	
		prdRepo.save(prd);				
		System.out.println(mapper.writeValueAsString(prd));
		assertNotNull(prdRepo.findOne(prd.getProductId()));	
	}
	
	
	//@Test
	public void createOrder() throws JsonProcessingException{		
		
		List<Orderdetail> details = new ArrayList<Orderdetail>();
		
		Order ord = new Order();	
		
		User usr = new User();
		String uid="198@sdLD.com";
		usr.setUserId(uid);
		
		ord.setOrderDate(new Date());				
		ord.setOrderId(new Random().nextInt(100)+500);				
		ord.setUser(usr);		
		System.out.println(mapper.writeValueAsString(ord));		
		
		//List <Product> prdlist = new ArrayList<Product>();

		Product p1 = new Product(); p1.setProductId(569); //prdRepo.findOne(569);
		Product p2 = new Product(); p2.setProductId(14);//prdRepo.findOne(14);
		Product p3 = new Product(); p3.setProductId(0); //prdRepo.findOne(0);				
				
		Orderdetail ordd1 = new Orderdetail();
		ordd1.setOrder(ord);	
		ordd1.setQuantity(2);		
		ordd1.setProduct(p1);
		details.add(ordd1);
		//ord.addOrderdetail(ordd);
		
		Orderdetail ordd2 = new Orderdetail();
		ordd2.setOrder(ord);	
		ordd2.setQuantity(4);
		ordd2.setProduct(p2);
		details.add(ordd2);

		//ord.addOrderdetail(ordd2);
		
		Orderdetail ordd3 = new Orderdetail();
		ordd3.setOrder(ord);	
		ordd3.setQuantity(6);
		ordd3.setProduct(p3);
		details.add(ordd3);
		
		orRepo.save(ord);
		orderDRepo.save(details);	
		
		System.out.println(mapper.writeValueAsString(details));		
		//System.out.println(mapper.writeValueAsString(l));
		
		//OrderBasket o = new OrderBasket();
		//o.setOrd(ord);
		//o.setDetails(l);
		//String s = mapper.writeValueAsString(o);
		//System.out.println(s);
		
		
		
		//#assertNotNull(orderDRepo.findAll();
	}
	
	
	@Test
	public void findOrders(){
		List<Order> list= new ArrayList<Order>();		
		list=orRepo.searchByCustomerid("A86");//.forEach(list::add);		
		System.out.println(list.size());
		for(Order o:list){
			System.out.print(" Customer Id: "+o.getUser().getUserId());
			System.out.print(" Order id :"+o.getOrderId());
			System.out.print(" Order Date:"+o.getOrderDate());
			System.out.println("");
		}
		assertThat(list.size()>2);
	}
	
	//@Test
	public void searchProdByName(){
		
		assertThat(prdRepo.findByNameLike("Some%").size()>0);
	}

}
