package com.horizonx.ordersys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.horizonx.ordersys.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{
	
    @Query("select o from Order as o join o.user as u where u.userId=?1 ")
	List <Order> searchByCustomerid(String customer_id);	
}
