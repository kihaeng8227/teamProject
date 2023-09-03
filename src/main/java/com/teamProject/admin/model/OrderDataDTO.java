package com.teamProject.admin.model;

public class OrderDataDTO {
	private int num;
	private String orderNum;
	private int cartId;
	private String productId;
	private String productName;
	private int productPrice;
	private int cnt;
	private int sumPrice;
	
	
	public OrderDataDTO(int num, String orderNum, int cartId, String productId, String productName, int productPrice,
			int cnt, int sumPrice) {
		this.num = num;
		this.orderNum = orderNum;
		this.cartId = cartId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.cnt = cnt;
		this.sumPrice = sumPrice;
	}
	public OrderDataDTO() {
		// TODO Auto-generated constructor stub
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	
	
}
