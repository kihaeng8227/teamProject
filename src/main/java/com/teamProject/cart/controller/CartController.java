package com.teamProject.cart.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamProject.cart.model.CartDAO;
import com.teamProject.admin.model.ProductsDAO;
import com.teamProject.cart.model.CartDTO;
import com.teamProject.admin.model.ProductsDTO;

@WebServlet(urlPatterns = {"/shop_db/addCart.jsp", "/shop_db/cart.jsp"})
public class CartController extends HttpServlet {

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
		
		System.out.println("cart command:" + command);
		
		if (command.contains("addCart.jsp")) { //��ٱ��� ���
			// �Ķ���ͷ� �Ѿ�� ���̵� ���� Ȯ��
			String productId = req.getParameter("productId");
			if (productId == null || productId.trim().equals("")) {
				resp.sendRedirect("./products.jsp");
				return;
			}
			HttpSession session = req.getSession();  //���� ����� ���� ����
			String orderNo = session.getId();
			String sessionMemberId = (String) session.getAttribute("sessionId");
			ProductsDAO dao = new ProductsDAO();
			CartDAO cartDAO = new CartDAO();
			
			// ���̵� ���� �������� ��ü�� ����
			ProductsDTO product = dao.getBoardByProductId(productId);
			if (product == null) {
				resp.sendRedirect("../exception/exceptionNoProductId.jsp");
			}
			boolean flag = cartDAO.updateCart(product, orderNo, sessionMemberId);
			
			resp.sendRedirect("product.jsp?productId=" + productId);
		}
		
		else if (command.contains("cart.jsp")) {
			
			CartDAO cartDAO = new CartDAO();
			
			HttpSession session = req.getSession();  //���� ����� ���� ����
			String orderNo = session.getId();
			cartDAO.updateCartBylogin(session);
			
			ArrayList<CartDTO> carts = cartDAO.getCartList(orderNo);
			req.setAttribute("carts", carts);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/shop_db/@cart.jsp");
			requestDispatcher.forward(req, resp);
		}
	}

	
}
