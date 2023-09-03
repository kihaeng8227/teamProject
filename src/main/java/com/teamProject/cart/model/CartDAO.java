package com.teamProject.cart.model;

import java.sql.*;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.teamProject.admin.model.ProductsDTO;
import com.teamProject.cart.database.DBConnection;
import com.teamProject.cart.model.CartDTO;

public class CartDAO {
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	// DB ���� �޼ҵ�
	private void connect() {
		try {
			connection = DBConnection.getConnection();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	public CartDAO()	{
			connect();
	}
	
	public boolean updateCart(ProductsDTO product, String orderNum, String memberId) {
		//������ �ֹ���ȣ�� ���� productId�� ������ update, ������ insert
		int flag = 0;
		String productId = product.getProductId();
		String sql = "SELECT cartId FROM cart WHERE orderNum = ? AND productId = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNum);
			preparedStatement.setString(2, productId);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int cartId = resultSet.getInt("cartId");
				sql = "UPDATE cart SET cnt = cnt + 1 WHERE cartId = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, cartId);
				flag = preparedStatement.executeUpdate();			
			}
			else {
				sql = "INSERT INTO cart VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, null);
				preparedStatement.setString(2, productId);
				preparedStatement.setString(3, product.getProductName());
				preparedStatement.setString(4, memberId);
				preparedStatement.setString(5, orderNum);
				preparedStatement.setInt(6, product.getProductPrice());
				preparedStatement.setInt(7, 1);
				preparedStatement.setString(8, product.getFileName());
				
				flag = preparedStatement.executeUpdate();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}
	
	public boolean updateCnt(ProductsDTO product, int cartId, String memberId, int cnt) {
		//������ �ֹ���ȣ�� ���� productId�� ������ update, ������ insert
		int flag = 0;
		String productId = product.getProductId();
		String sql = "SELECT cartId FROM cart WHERE productId = ?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, productId);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				sql = "UPDATE cart SET cnt = ? WHERE cartId = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, cnt);
				preparedStatement.setInt(2, cartId);
				flag = preparedStatement.executeUpdate();			
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}
	
	public ArrayList<CartDTO> getCartList(String orderNum) {
		ArrayList<CartDTO> cartArrayList = new ArrayList<>();
		String sql = "SELECT * FROM cart WHERE orderNum = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNum);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CartDTO cart = new CartDTO();
				cart.setCartId(resultSet.getInt("cartId"));
				cart.setProductId(resultSet.getString("productId"));
				cart.setProductName(resultSet.getString("productName"));
				cart.setProductPrice(resultSet.getInt("productPrice"));
				cart.setCnt(resultSet.getInt("cnt"));
				cart.setFileName(resultSet.getString("fileName"));
				cartArrayList.add(cart);
		
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return cartArrayList;
	}
	
	public boolean updateCartBylogin(HttpSession session) {
		int flag = 0;
		String orderNum = session.getId();
		String id = (String)session.getAttribute("sessionId");
		// ���� �α��ο� ���� ��ǰ ������Ʈ
		String sql = "UPDATE cart SET orderNum = ? WHERE memberId = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNum);
			preparedStatement.setString(2, id);
			flag = preparedStatement.executeUpdate();
			
			//�α��� ���� ���� ��ǰ ������Ʈ
			sql = "UPDATE cart SET memberId = ? WHERE orderNum = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, orderNum);
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag != 0;
	}
	public boolean deleteCartById(String orderNum, int cartId) {
		// ��ٱ��� ���� ����
		int flag = 0;
		String sql = "SELECT cartId FROM cart WHERE orderNum = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNum);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				sql = "DELETE FROM cart WHERE cartId = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, cartId);
				flag = preparedStatement.executeUpdate();			
			}

		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}
	public boolean deleteCartAll(String orderNum) {
		// ��ٱ��� ��ü ����
		int flag = 0;
		String sql = "SELECT * FROM cart WHERE orderNum = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNum);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				sql = "DELETE FROM cart WHERE orderNum = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, orderNum);
				flag = preparedStatement.executeUpdate();			
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}
	
	 
	public boolean deleteCartSel(String orderNum, String chkdId) throws SQLException {
		// ������ ��ǰ�� ����
		int flag = 0;
		String sql = "DELETE FROM cart WHERE orderNum = '" + orderNum + "' AND cartId IN (" + chkdId + ")";
		statement = connection.createStatement();
		flag = statement.executeUpdate(sql);
		return flag != 0;
	}
	

}
