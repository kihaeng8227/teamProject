package com.teamProject.cart.model;

public class OrderInfoDTO {
	private String orderNum;
	private String memberId;
	private String orderName;
	private String orderTel;
	private String orderEmail;
	private String receiveName;
	private String receiveTel;
	private String receiveAddress;
	private String payMethod;
	private int payAmount;
	private String carryNo;
	private String orderStep;
	private String dateOrder;
	private String datePay;
	private String dateCarry;
	private String dateDone;
	
	public OrderInfoDTO(String orderNum, String memberId, String orderName, String orderTel, String orderEmail,
			String receiveName, String receiveTel, String receiveAddress,int payAmount, String payMethod, String carryNo,
			String orderStep, String dateOrder, String datePay, String dateCarry, String dateDone) {
		this.orderNum = orderNum;
		this.memberId = memberId;
		this.orderName = orderName;
		this.orderTel = orderTel;
		this.orderEmail = orderEmail;
		this.receiveName = receiveName;
		this.receiveTel = receiveTel;
		this.receiveAddress = receiveAddress;
		this.payAmount = payAmount;
		this.payMethod = payMethod;
		this.carryNo = carryNo;
		this.orderStep = orderStep;
		this.dateOrder = dateOrder;
		this.datePay = datePay;
		this.dateCarry = dateCarry;
		this.dateDone = dateDone;
	}

	public OrderInfoDTO() {
		// TODO Auto-generated constructor stub
	}

	

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderTel() {
		return orderTel;
	}

	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}

	public String getOrderEmail() {
		return orderEmail;
	}

	public void setOrderEmail(String orderEmail) {
		this.orderEmail = orderEmail;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveTel() {
		return receiveTel;
	}

	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	
	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getCarryNo() {
		return carryNo;
	}

	public void setCarryNo(String carryNo) {
		this.carryNo = carryNo;
	}

	public String getOrderStep() {
		return orderStep;
	}

	public void setOrderStep(String orderStep) {
		this.orderStep = orderStep;
	}

	public String getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(String dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getDatePay() {
		return datePay;
	}

	public void setDatePay(String datePay) {
		this.datePay = datePay;
	}

	public String getDateCarry() {
		return dateCarry;
	}

	public void setDateCarry(String dateCarry) {
		this.dateCarry = dateCarry;
	}

	public String getDateDone() {
		return dateDone;
	}

	public void setDateDone(String dateDone) {
		this.dateDone = dateDone;
	}
	
	
	
}
