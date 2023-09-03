package com.teamProject.cart.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.teamProject.cart.model.CartDTO;
import com.teamProject.cart.database.DBConnection;

public class OrderDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stm = null;
	private ResultSet rs = null;
	private static OrderDAO instance;

	private void connect() {
		try {
			conn = DBConnection.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OrderDAO() {
		connect();
	}

	public static OrderDAO getInstance() {
		if (instance == null)
			instance = new OrderDAO();
		return instance;
	}

	public void clearOrderData(String orderNum) {
		// 주문번호 기준으로 주문 데이터 삭제
		// 중복 등록 방지.
		String sql = "DELETE FROM order_data WHERE orderNum = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, orderNum);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("clearOrderData()���� : " + ex);
		}

	}

	public boolean insertOrderData(OrderDataDTO dto) {
		int flag = 0;
		String sql = "INSERT INTO order_data VALUES (null, ?, ?, ?, ?, " + "?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, dto.getOrderNum());
			pstmt.setInt(2, dto.getCartId());
			pstmt.setString(3, dto.getProductId());
			pstmt.setString(4, dto.getProductName());
			pstmt.setInt(5, dto.getProductPrice());
			pstmt.setInt(6, dto.getCnt());
			pstmt.setInt(7, dto.getSumPrice());
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("insertOrderData() ���� : " + ex);
		}
		return flag != 0;
	}

	public ArrayList<OrderDataDTO> selectAllOrderData(String orderNum) {
		ArrayList<OrderDataDTO> dtos = new ArrayList<>();
		String sql = "SELECT * FROM order_data WHERE orderNum = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDataDTO dto = new OrderDataDTO();
				dto.setNum(rs.getInt("num"));
				dto.setOrderNum(rs.getString("orderNum"));
				dto.setCartId(rs.getInt("cartId"));
				dto.setProductId(rs.getString("productId"));
				dto.setProductName(rs.getString("productName"));
				dto.setProductPrice(rs.getInt("productPrice"));
				dto.setCnt(rs.getInt("cnt"));
				dto.setSumPrice(rs.getInt("sumPrice"));
				dtos.add(dto);

			}
		} catch (Exception ex) {
			System.out.println("selectAllOrderData() ���� : " + ex);
		}
		return dtos;
	}

	public int getTotalPrice(String orderNum) {
		int totalPrice = 0;
		String sql = "SELECT SUM(sumPrice) FROM order_data where orderNum = '" + orderNum + "'";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				totalPrice = rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("getTotalPrice() ���� : " + ex);
		}

		return totalPrice;
	}

	public boolean insertOrderInfo(OrderInfoDTO info) {
		int flag = 0;
		String sql = "INSERT INTO order_info VALUES (?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " + "?, ?, now(), ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, info.getOrderNum());
			pstmt.setString(2, info.getMemberId());
			pstmt.setString(3, info.getOrderName());
			pstmt.setString(4, info.getOrderTel());
			pstmt.setString(5, info.getOrderEmail());

			pstmt.setString(6, info.getReceiveName());
			pstmt.setString(7, info.getReceiveTel());
			pstmt.setString(8, info.getReceiveAddress());
			pstmt.setInt(9, info.getPayAmount());
			pstmt.setString(10, info.getPayMethod());

			pstmt.setString(11, info.getCarryNo());
			pstmt.setString(12, "orderFail");
			pstmt.setString(13, info.getDatePay());
			pstmt.setString(14, info.getDateCarry());
			pstmt.setString(15, info.getDateDone());

			flag = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("insertOrderInfo() ���� : " + ex);
		}
		return flag != 0;
	}

	public void clearOrderInfo(String orderNum) {
		String sql = "DELETE FROM order_info WHERE orderNum = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, orderNum);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("clearOrderInfo()���� : " + ex);
		}

	}

	public String getOrderProductName(String orderNum) {
		String orderProductName = null;
		int orderProductCnt = 0;
		String sql = "SELECT * FROM order_data WHERE orderNum = '" + orderNum + "'";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				if (orderProductCnt == 0) {
					orderProductName = rs.getString("ProductName");
				}
				orderProductCnt++;
			}
			orderProductName += " 외 " + (orderProductCnt - 1) + "건";
		} catch (Exception ex) {
			System.out.println("getOrderProductName() ���� : " + ex);
		}
		return orderProductName;
	}

	public OrderInfoDTO getOrderInfo(String orderNum) {
		OrderInfoDTO dto = new OrderInfoDTO();
		String sql = "SELECT * FROM order_info WHERE orderNum = '" + orderNum + "'";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				dto.setOrderNum(rs.getString(1));
				dto.setMemberId(rs.getString(2));
				dto.setOrderName(rs.getString(3));
				dto.setOrderTel(rs.getString(4));
				dto.setOrderEmail(rs.getString(5));

				dto.setReceiveName(rs.getString(6));
				dto.setReceiveTel(rs.getString(7));
				dto.setReceiveAddress(rs.getString(8));
				dto.setPayAmount(rs.getInt(9));
				dto.setPayMethod(rs.getString(10));

				dto.setCarryNo(rs.getString(11));
				dto.setOrderStep(rs.getString(12));
				dto.setDateOrder(rs.getString(13));
				dto.setDatePay(rs.getString(14));
				dto.setDateCarry(rs.getString(15));

				dto.setDateDone(rs.getString(16));

			}
		} catch (Exception ex) {
			System.out.println("getOrderInfo() ���� : " + ex);
		}
		return dto;
	}

	public boolean updateOrderInfoWhenProcessSuccess(OrderInfoDTO dto) {
		int flog = 0;
		String sql = "UPDATE order_info SET payMethod = ?, orderStep = ?, datePay = now() WHERE orderNum = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, dto.getPayMethod());
			pstmt.setString(2, dto.getOrderStep());
			pstmt.setString(3, dto.getOrderNum());
			flog = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("updateOrderInfoWhenProcessSuccess()���� : " + ex);
		}
		return flog == 1;
	}

	public int getListCount(String sessionId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		String sql;
		sql = "select count(*) from order_info where memberId = ?";

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sessionId);
			rs = pstmt.executeQuery();

			if (rs.next())
				x = rs.getInt(1);

		} catch (Exception ex) {
			System.out.println("getListCount() �� �� : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return x;
	}

	public List<OrderInfoDTO> getOrderInfoList(int page, int limit, String sessionId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int total_record = getListCount(sessionId);
		int start = (page - 1) * limit;
		int index = start + 1;

		String sql = "SELECT * FROM order_info where memberId = ?";

		ArrayList<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sessionId);
			rs = pstmt.executeQuery();

			while (rs.absolute(index)) {
				OrderInfoDTO orderInfo = new OrderInfoDTO();
				orderInfo.setOrderNum(rs.getString("orderNum"));
				orderInfo.setMemberId(rs.getString("memberId"));
				orderInfo.setOrderName(rs.getString("orderName"));
				orderInfo.setOrderTel(rs.getString("orderTel"));
				orderInfo.setOrderEmail(rs.getString("orderEmail"));
				orderInfo.setReceiveName(rs.getString("receiveName"));
				orderInfo.setReceiveTel(rs.getString("receiveTel"));
				orderInfo.setReceiveAddress(rs.getString("receiveAddress"));
				orderInfo.setPayAmount(rs.getInt("payAmount"));
				orderInfo.setPayMethod(rs.getString("payMethod"));
				orderInfo.setCarryNo(rs.getString("carryNo"));
				orderInfo.setOrderStep(rs.getString("orderStep"));
				orderInfo.setDateOrder(rs.getString("dateOrder"));
				orderInfo.setDatePay(rs.getString("datePay"));
				orderInfo.setDateCarry(rs.getString("dateCarry"));
				orderInfo.setDateDone(rs.getString("dateDone"));

				list.add(orderInfo);

				if (index < (start + limit) && index <= total_record)
					index++;
				else
					break;
			}
			return list;
		} catch (Exception ex) {
			System.out.println("getBoardList �� �� : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

	public List<OrderDataDTO> getOrderDataList(int page, int limit, String sessionId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int total_record = getListCount(sessionId);
		int start = (page - 1) * limit;
		int index = start + 1;

		String sql = "SELECT * FROM order_data where memberId = ?";

		ArrayList<OrderDataDTO> list = new ArrayList<OrderDataDTO>();

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sessionId);
			rs = pstmt.executeQuery();

			while (rs.absolute(index)) {
				OrderDataDTO orderData = new OrderDataDTO();
				orderData.setNum(rs.getInt("num"));
				orderData.setOrderNum(rs.getString("orderNum"));
				orderData.setCartId(rs.getInt("cartId"));
				orderData.setProductId(rs.getString("productId"));
				orderData.setProductName(rs.getString("productName"));
				orderData.setProductPrice(rs.getInt("productPrice"));
				orderData.setCnt(rs.getInt("cnt"));
				orderData.setSumPrice(rs.getInt("sumPrice"));

				list.add(orderData);

				if (index < (start + limit) && index <= total_record)
					index++;
				else
					break;
			}
			return list;
		} catch (Exception ex) {
			System.out.println("getBoardList �� �� : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}

}
