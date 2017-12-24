package com.horizonx.ordersys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horizonx.ordersys.model.Product;
import com.horizonx.ordersys.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository prodRepo;

	public void addProduct(Product prd) {		
		prodRepo.save(prd);		
	}
	
	public List<Product> getProductByName(String prdName) {		
		return prodRepo.findByNameLike(prdName);
	}

	/**
	 * It returns the product based on product id.
	 * @param productId
	 * @return
	 */
	public Product getProduct(Integer productId) {		
		return prodRepo.findOne(productId);
	}

	public List<Product> getAllProd() {
		List<Product> list = new ArrayList<Product>();
		prodRepo.findAll().forEach(list::add);
		return list;		
	}
	

}
