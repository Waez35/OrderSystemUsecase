package com.horizonx.ordersys.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orderdetails database table.
 * 
 */
@Entity
@Table(name="orderdetails")
@NamedQuery(name="Orderdetail.findAll", query="SELECT o FROM Orderdetail o")
public class Orderdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private int quantity;

	@Id
	@GeneratedValue
	@Column(name = "serialsno")
	private int serialno;
	
	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

	public Orderdetail() {
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getSerialno() {
		return serialno;
	}

	public void setSerialno(int serialno) {
		this.serialno = serialno;
	}


}