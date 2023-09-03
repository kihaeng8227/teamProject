package com.teamProject.admin.model;

import com.teamProject.admin.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDataDAO {
    private static OrderDataDAO instance;

    private OrderDataDAO() {

    }

    public static OrderDataDAO getInstance() {
        if (instance == null)
            instance = new OrderDataDAO();
        return instance;
    }

    public List<OrderDataDTO> getOrderDataListByOrderNum(String item) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from order_data where orderNum = '" + item + "'";

        ArrayList<OrderDataDTO> list = new ArrayList<OrderDataDTO>();


        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderDataDTO orderDataDTO = new OrderDataDTO();
                orderDataDTO.setNum(rs.getInt("num"));
                orderDataDTO.setOrderNum(rs.getString("orderNum"));
                orderDataDTO.setCartId(rs.getInt("cartId"));
                orderDataDTO.setProductId(rs.getString("productId"));
                orderDataDTO.setProductName(rs.getString("productName"));
                orderDataDTO.setProductPrice(rs.getInt("productPrice"));
                orderDataDTO.setCnt(rs.getInt("cnt"));
                orderDataDTO.setSumPrice(rs.getInt("sumPrice"));
                list.add(orderDataDTO);
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getOrderDataListByOrderNum() 에러 : " + ex);
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

    public List<ProductsDTO> getProductDTOListByOrderNum(String orderNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from order_data where orderNum = '" + orderNum + "'";

        ArrayList<ProductsDTO> list = new ArrayList<ProductsDTO>();
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductsDTO productsDTO = new ProductsDTO();
                productsDTO.setProductId(rs.getString("productId"));
                productsDTO.setProductsInStock(rs.getInt("cnt"));
                list.add(productsDTO);
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getProductDTOListByOrderNum() 에러 : " + ex);
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
