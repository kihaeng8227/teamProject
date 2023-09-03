package com.teamProject.cart.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.teamProject.cart.model.CartDAO;
import com.teamProject.cart.model.CartDTO;
import com.teamProject.cart.database.DBConnection;
import com.teamProject.cart.model.OrderDAO;
import com.teamProject.cart.model.OrderDataDTO;
import com.teamProject.cart.model.OrderInfoDTO;
import com.teamProject.admin.model.ProductsDAO;
import com.teamProject.admin.model.ProductsDTO;
import com.teamProject.cart.service.OrderStep;

@WebServlet("*.co")
public class OrderController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String RequestURI = req.getRequestURI(); // ��ü ��θ� ������
		String contextPath = req.getContextPath(); // ������Ʈ Path�� ������
		String command = RequestURI.substring(contextPath.length()); // ��ü��ο��� ������Ʈ Path ���� ��ŭ �ε��� ������ ���ڿ��� ������

		resp.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");

		System.out.println("command: " + command);
		System.out.println("orderNo : " + getOrderNo(req));
		
		if (command.contains("form.co")) { //주문서　／　배송　정보　입력　페이지
			//상단에　장바구니　출력
			setOrderData(req);
			
			//상단에　출력할　장바구니　목록
			ArrayList<OrderDataDTO> datas = getOrderData(getOrderNo(req));
			req.setAttribute("datas", datas);
			
			//장바구니 합계 금액
			int totalPrice = getTotalPrice(getOrderNo(req));
			req.setAttribute("totalPrice", totalPrice);
			
			req.getRequestDispatcher("/WEB-INF/order/form.jsp").forward(req, resp);
			 
		}
		
		else if (command.contains("pay.co")) { // �ֹ��� ���� ���� �� ���� ���� ���
			
			setOrderInfo(req); //�ֹ����� ����
			
			//��ٱ��� �հ� �ݾ�
			int totalPrice = getTotalPrice(getOrderNo(req));
			req.setAttribute("totalPrice", totalPrice);
			
		
			// �ֹ��� ���� ������
			OrderInfoDTO info = getOrderInfo(getOrderNo(req));
			req.setAttribute("info", info);
			
			// �ֹ���ǰ ���� �������� (�� : Lg gram �� 1��)
			String orderProductName = getOrderProductName(getOrderNo(req));
			req.setAttribute("orderProductName", orderProductName);
			
			req.getRequestDispatcher("/WEB-INF/order/pay.jsp").forward(req, resp);
			
		}
		else if (command.contains("success.co")) { // ���� ������ ���������� �� ���
			// ���� ��ħ�ÿ� ���� ���� API�� ����� ��û�� �ؼ� ���� �޽����� ���� �� ������
			// ó���� �Ŀ��� sendRedirect
			try {
				processSuccess(req); //ó��
				resp.sendRedirect("/order/orderDone.co");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else if (command.contains("orderDone.co")) { // ���� �Ϸ�
			// order_data�� �ִ� cartId �������� ��ٱ��Ͽ� �ִ� ��ǰ�� ����
			deleteCartWhenOrderDone(req);
			
			// ��� ����� ��ٱ��� ���
			ArrayList<OrderDataDTO> datas = getOrderData(getOrderNo(req));
			req.setAttribute("datas", datas);
			
			// �ֹ� ���� ������
			OrderInfoDTO info = getOrderInfo(getOrderNo(req));
			// �ֹ��ܰ踦 �ѱ۷�
			OrderStep orderStep = OrderStep.valueOf(info.getOrderStep());
			info.setOrderStep(orderStep.getStep());
			req.setAttribute("info", info);
			
			// �ֹ� ��ȣ ����
			HttpSession session = req.getSession();
			session.removeAttribute("orderNo");
					
			
			req.getRequestDispatcher("/WEB-INF/order/orderDone.jsp").forward(req, resp);
				
		}
	}


	private void deleteCartWhenOrderDone(HttpServletRequest req) {
		// �ֹ� ó���� �Ϸ�� �� order_data�� �ִ� cartId �������� ��ٱ��� ����
		OrderDAO dao = OrderDAO.getInstance();
		CartDAO cartDAO = new CartDAO();
		
		HttpSession session = req.getSession();
		
		ArrayList<OrderDataDTO> dtos = dao.selectAllOrderData(getOrderNo(req));
		for (OrderDataDTO dto : dtos) {
			try {
				cartDAO.deleteCartById(session.getId(), dto.getCartId());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void processSuccess(HttpServletRequest req) throws Exception {
		/* ������ ���������� ���� ��� ȣ��
		 * ���� �õ� �������� paymentData ��ü�� successUrl �Ӽ����� ����
		 * ���ٽÿ� orderId, paymentKey, amount �Ķ���� ������ ���� (���� : ���� url���� �������ε��� ������ ���ԵǾ� ���� ����
		 * 
		 * paymentKey: ������ Ű ��
		 * orderId : �ֹ� ID�Դϴ�. ����â�� �� �� requestPayment()�� ��� ���� ��
		 * amount : ������ ������ �ݾ�
		 * 
		*/
		String orderId = req.getParameter("orderId");
		System.out.println("orderId : " + orderId);
		String paymentKey = req.getParameter("paymentKey");
		System.out.println("paymentKey : " + paymentKey);
		String amount = req.getParameter("amount");
		System.out.println("amount : " + amount);
		
		
		  // ���� ���� API ȣ���ϱ� 
		 
		  // �佺���� ���� ��ũ��Ű�� ��� .�佺�ʿ����� �ش� ������ ������ ����
		  String secretKey = "test_sk_zXLkKEypNArWmo50nX3lmeaxYG5R"
		  		 + ":";
		  
		  // ��ũ��Ű�� ���ڵ�
		  Base64.Encoder encoder = Base64.getEncoder(); 
		  byte[] encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
		  String authorizations = "Basic "+ new String(encodedBytes, 0, encodedBytes.length);
		  
		  // �佺 ���� ���� API ȣ���ϱ�
		  // REST API������� ó��
		  
		  // ���� url�� paymentKey ����
		  URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);
		  
		  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		  // ��ũ��Ű ����
		  connection.setRequestProperty("Authorization", authorizations);
		  connection.setRequestProperty("Content-Type", "application/json");
		  connection.setRequestMethod("POST");
		  connection.setDoOutput(true);
	
		  JSONObject obj = new JSONObject();
		  obj.put("orderId", orderId);
		  obj.put("amount", amount);
		  
		  OutputStream outputStream = connection.getOutputStream();
		  outputStream.write(obj.toString().getBytes("UTF-8"));
		  //ȣ�� �� ��� �ڵ� ��������
		  int code = connection.getResponseCode();
		  
		  //  ��� �ڵ�� ���� ��� ó��
		  // code�� 200�����̸� �������� ó��
		  boolean isSuccess = code >= 200 && code < 300 ? true : false;
		  System.out.println("isSuccess : " + isSuccess);
		  
		  InputStream responseStream = isSuccess? connection.getInputStream(): connection.getErrorStream();
		  Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
	
		  JSONParser parser = new JSONParser();
		  // response ���� jsonObject�� ����
		  JSONObject jsonObject = (JSONObject) parser.parse(reader);
		  System.out.println("��� ������ : " + jsonObject.toJSONString());
		  responseStream.close();

		  if (isSuccess) { //������ ó��
			  System.out.println("�ֹ���ȣ orderId : " + jsonObject.get("orderId"));
			  System.out.println("������� method : " + jsonObject.get("method"));
			  System.out.println("���������Ͻ� approvedAt : " + jsonObject.get("approvedAt"));
			  
			  // ������ ��������� ���� ó��. ������ ������¿� ��Ÿ ���� �������� ��������
			  String method = (String) jsonObject.get("method");
			  
			  OrderInfoDTO dto = new OrderInfoDTO();
			  dto.setOrderNum((String) jsonObject.get("orderId")); //�ֹ���ȣ
			  dto.setPayMethod(method);
			  
			  if (method.equals("�������")) {
				  dto.setOrderStep(String.valueOf(OrderStep.orderReceive));
			  }
			  else {
				  dto.setOrderStep(String.valueOf(OrderStep.payReceive));
				  dto.setDatePay((String) jsonObject.get("approvedAt")); //�Ա��Ͻ�
			  }
			 processSuccessUpdate(dto);
		  }
		
	}

	private boolean processSuccessUpdate(OrderInfoDTO dto) {
		// dto �������� �ֹ� ���� ������Ʈ ����
		OrderDAO dao = OrderDAO.getInstance();
		return dao.updateOrderInfoWhenProcessSuccess(dto);
		
	}

	private OrderInfoDTO getOrderInfo(String orderNo) {
		OrderDAO dao = OrderDAO.getInstance();
		return dao.getOrderInfo(orderNo);
	}
	private String getOrderProductName(String orderNo) {
		OrderDAO dao = OrderDAO.getInstance();
		return dao.getOrderProductName(orderNo);
	}
	
	
	private void setOrderInfo(HttpServletRequest req) {
		OrderDAO dao = OrderDAO.getInstance();
		
		//�ߺ��� ���� ���� �ֹ���ȣ�� ����� ������ ����
		dao.clearOrderInfo(getOrderNo(req));
		
		//request ���� dto�� �����ؼ� dao�� ����
		
		OrderInfoDTO info = new OrderInfoDTO();
		
		info.setOrderNum(getOrderNo(req));
		info.setMemberId(getMemberId(req));
		info.setOrderName(req.getParameter("orderName"));
		info.setOrderTel(req.getParameter("orderTel"));
		info.setOrderEmail(req.getParameter("orderEmail"));
		info.setReceiveName(req.getParameter("receiveName"));
		info.setReceiveTel(req.getParameter("receiveTel"));
		info.setReceiveAddress("( " + req.getParameter("zipcode") + " ) " + req.getParameter("receiveAddress") + " " + req.getParameter("detailAddr"));
		info.setPayAmount(getTotalPrice(getOrderNo(req)));
		
		dao.insertOrderInfo(info);
	}

	private String getMemberId(HttpServletRequest req) {
		// ���ǿ� ����� ���̵� ������
		HttpSession session = req.getSession();
		return (String) session.getAttribute("sessionId");
	}

	private ArrayList<OrderDataDTO> getOrderData(String orderNo) {
		OrderDAO dao = OrderDAO.getInstance();
		ArrayList<OrderDataDTO> dtos = dao.selectAllOrderData(orderNo);
		return dtos;
	}

	private String getOrderNo(HttpServletRequest req) {
		/*  �ֹ� ��ȣ�� ���ǿ� �� orderNo���� ����.
		1) ���ǿ� orderNo���� ����� ���� ������ ��ȯ
		2) null�̸� ���� �� ���ǿ� ���� �� ��ȯ. */
		HttpSession session = req.getSession();	// ���� ����� ���� ����
		String orderNo = (String) session.getAttribute("orderNo");
		if(orderNo == null) {
			orderNo = generateOrderNo(req);
			session.setAttribute("orderNo", orderNo);
		}
		return orderNo;
	}

	private String generateOrderNo(HttpServletRequest req) {
		/* �ֹ���ȣ ����
		���� ��¥ �ð��� ���� Ű�� �������� �ֹ���ȣ ����.
		*/
		
		// ���� ��¥ �ð� ���ϱ�
		LocalDateTime now = LocalDateTime.now();
		String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
		HttpSession session = req.getSession();
		// �ֹ���ȣ���� ���� Ű ������ ������ ������ (-) �߰�.
		return nowStr + "-" + session.getId();
	}

	private void setOrderData(HttpServletRequest req) {
		// 장바구니에 있는 상품을 주문 데이터에 복사
		// 결제 금액을 장바구니가 아니라 주문 데이터 기준으로 계산
		
		OrderDAO dao = OrderDAO.getInstance();
		//주문 번호 가져오기
		String orderNo = (String) getOrderNo(req);
		System.out.println("주문 번호" + orderNo);
		
		// �ߺ��� ���� ���� �ֹ���ȣ�� ����� ������ ����
		dao.clearOrderData(orderNo);
		
		// ���� ���̵� �������� ��ٱ��Ͽ� �ִ� ��ǰ�� ������ ��
		CartDAO cartDAO = new CartDAO();
		HttpSession session = req.getSession();
		ArrayList<CartDTO> carts = cartDAO.getCartList(session.getId());
		System.out.println("carts" + carts);
		
		// CartList�� OrderData List�� ����
		ArrayList<OrderDataDTO> dtos = changeCartData(carts, orderNo);
		System.out.println("dtos" + dtos);
		
		// OrderData List�� ������ ���̽��� ����
		for(OrderDataDTO dto : dtos) {
			dao.insertOrderData(dto);
		}
		
	}
	

	private ArrayList<OrderDataDTO> changeCartData(ArrayList<CartDTO> carts, String orderNum) {
		ArrayList<OrderDataDTO> datas = new ArrayList<>();
		for(CartDTO cart : carts) {
			OrderDataDTO dto = new OrderDataDTO();
			dto.setOrderNum(orderNum);
			dto.setCartId(cart.getCartId());
			dto.setProductId(cart.getProductId());
			dto.setProductName(cart.getProductName());
			dto.setProductPrice(cart.getProductPrice());
			dto.setCnt(cart.getCnt());
			dto.setSumPrice(cart.getProductPrice() * cart.getCnt());
			datas.add(dto);
			
		}
		return datas;
	}
	
	private int getTotalPrice(String orderNo) {
		OrderDAO dao = OrderDAO.getInstance();
		return dao.getTotalPrice(orderNo);
	}
	
	
}
