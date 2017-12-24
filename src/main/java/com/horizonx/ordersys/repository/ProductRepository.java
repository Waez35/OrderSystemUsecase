package com.horizonx.ordersys.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.horizonx.ordersys.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

	List<Product> findByNameLike(String name);
}
