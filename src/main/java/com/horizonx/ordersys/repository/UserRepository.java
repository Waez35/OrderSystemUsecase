package com.horizonx.ordersys.repository;

import org.springframework.data.repository.CrudRepository;

import com.horizonx.ordersys.model.User;

public interface UserRepository extends CrudRepository<User,String> {

}
