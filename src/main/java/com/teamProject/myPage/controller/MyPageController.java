package com.teamProject.myPage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamProject.board.model.BoardDAO;
import com.teamProject.board.model.BoardDTO;
import com.teamProject.myPage.model.LikeDAO;
import com.teamProject.myPage.model.LikeDTO;
import com.teamProject.cart.model.OrderDAO;
import com.teamProject.cart.model.OrderDataDTO;
import com.teamProject.cart.model.OrderInfoDTO;
import com.teamProject.admin.model.ProductsDAO;
import com.teamProject.admin.model.ProductsDTO;

@WebServlet("*.my")
public class MyPageController extends HttpServlet {
	static final int LISTCOUNT = 5;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String RequestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = RequestURI.substring(contextPath.length());

		resp.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");

		System.out.println(command);

		if (command.contains("/jjimListAction.my")) {
			try {
				requestjjimList(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher rd = req.getRequestDispatcher("../shop_db/jjimList.jsp");
			rd.forward(req, resp);

		} else if (command.contains("/addjjim.my")) {
			try {
				requestAddjjim(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (command.contains("/deletejjim.my")) {
			try {
				requestDeletejjim(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher rd = req.getRequestDispatcher("../shop_db/jjimListAction.my");
			rd.forward(req, resp);

		} else if (command.contains("/orderList.my")) {
			try {
				requestOrderList(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (command.contains("/myPostList.my")) {
			try {
				requestMyPostList(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}

	private void requestMyPostList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("sessionId");
		
		if (sessionId == null) {
			resp.sendRedirect("../member/loginMember.jsp?");
		}

		BoardDAO dao = BoardDAO.getInstance();
		List<BoardDTO> boardlist = new ArrayList<BoardDTO>();

		int pageNum = 1;
		int limit = 5;

		if (req.getParameter("pageNum") != null)
			pageNum = Integer.parseInt(req.getParameter("pageNum"));

		int total_record = dao.getListCount(sessionId);
		boardlist = dao.getboardlist(pageNum, limit, sessionId);
		int total_page;

		if (total_record % limit == 0) {
			total_page = total_record / limit;
			Math.floor(total_page);
		} else {
			total_page = total_record / limit;
			Math.floor(total_page);
			total_page = total_page + 1;
		}
		req.setAttribute("limit", limit);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("total_page", total_page);
		req.setAttribute("total_record", total_record);
		req.setAttribute("boardlist", boardlist);
		req.setAttribute("sessionId", sessionId);
		
		RequestDispatcher rd = req.getRequestDispatcher("../shop_db/myPost.jsp");
		rd.forward(req, resp);

	}

	private void requestOrderList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("sessionId");

		if (sessionId == null) {
			resp.sendRedirect("../member/loginMember.jsp?");
		}

		OrderDAO dao = OrderDAO.getInstance();
		List<OrderInfoDTO> orderinfolist = new ArrayList<OrderInfoDTO>();
		List<OrderDataDTO> orderDatalist = new ArrayList<OrderDataDTO>();

		int pageNum = 1;
		int limit = 5;

		if (req.getParameter("pageNum") != null)
			pageNum = Integer.parseInt(req.getParameter("pageNum"));

		int total_record = dao.getListCount(sessionId);
		orderinfolist = dao.getOrderInfoList(pageNum, limit, sessionId);
		orderDatalist = dao.getOrderDataList(pageNum, limit, sessionId);
		int total_page;

		if (total_record % limit == 0) {
			total_page = total_record / limit;
			Math.floor(total_page);
		} else {
			total_page = total_record / limit;
			Math.floor(total_page);
			total_page = total_page + 1;
		}
		req.setAttribute("limit", limit);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("total_page", total_page);
		req.setAttribute("total_record", total_record);
		req.setAttribute("orderinfolist", orderinfolist);
		req.setAttribute("orderDatalist", orderDatalist);
		req.setAttribute("sessionId", sessionId);

		RequestDispatcher rd = req.getRequestDispatcher("../shop_db/orderList.jsp");
		rd.forward(req, resp);

	}

	private void requestAddjjim(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("sessionId");

		String productId = req.getParameter("productId");

		ProductsDAO dao = new ProductsDAO();

		ProductsDTO product = dao.getBoardByProductId(productId);
		if (product == null) {
			resp.sendRedirect("exceptionNoProductId.jsp");
		}

		String memberId = (String) session.getAttribute("sessionId");

		LikeDAO likeDAO = new LikeDAO();

		likeDAO.insertLike(product, memberId);

		resp.sendRedirect("./product.jsp?productId=" + productId);

		req.setAttribute("sessionId", sessionId);
		req.setAttribute("productId", productId);

	}

	private void requestjjimList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("sessionId");
		
		if (sessionId == null) {
			resp.sendRedirect("../member/loginMember.jsp?");
		}

		LikeDAO dao = LikeDAO.getInstance();
		List<LikeDTO> boardlist = new ArrayList<LikeDTO>();

		int pageNum = 1;
		int limit = 3;

		if (req.getParameter("pageNum") != null)
			pageNum = Integer.parseInt(req.getParameter("pageNum"));

		int total_record = dao.getListCount(sessionId);
		boardlist = dao.getBoardList(pageNum, limit, sessionId);
		int total_page;

		if (total_record % limit == 0) {
			total_page = total_record / limit;
			Math.floor(total_page);
		} else {
			total_page = total_record / limit;
			Math.floor(total_page);
			total_page = total_page + 1;
		}
		req.setAttribute("limit", limit);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("total_page", total_page);
		req.setAttribute("total_record", total_record);
		req.setAttribute("jjimlist", boardlist);
		req.setAttribute("sessionId", sessionId);
		
	}

	private void requestDeletejjim(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		LikeDAO likedao = LikeDAO.getInstance();
		ProductsDAO productdao = new ProductsDAO();

		String productId = req.getParameter("productId");
		ProductsDTO productDTO = productdao.getBoardByProductId(productId);

		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("sessionId");

		likedao.deleteLike(productDTO, sessionId);

	}
}
