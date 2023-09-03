package com.teamProject.cart.model;

public class CartDTO {
	
	private int cartId;
	private String productId; //��ǰ ���̵�
	private String productName;	// ��ǰ��
	private int productPrice; //��ǰ ����
	private int cnt; // ��ٱ��Ͽ� ���� ����
	private String memberId; // ȸ�� ���̵�
	private String orderNum; // �ֹ���ȣ
	private String fileName; //�̹���
	

	
	
	public CartDTO(int cartId, String productId, String productName, int productPrice, int cnt, String memberId,
			String orderNum, String fileName) {
		this.cartId = cartId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.cnt = cnt;
		this.memberId = memberId;
		this.orderNum = orderNum;
		this.fileName = fileName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public CartDTO() {
		// TODO Auto-generated constructor stub
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
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	
}
