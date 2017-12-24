package com.horizonx.ordersys.model;

import java.util.List;

public class OrderRequest {

	private String customer_id;
	private List<ProdRequest> list;
	
	
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public List<ProdRequest> getList() {
		return list;
	}
	public void setList(List<ProdRequest> list) {
		this.list = list;
	}	
	
	
}
