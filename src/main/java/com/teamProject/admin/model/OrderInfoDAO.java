package com.teamProject.admin.model;

import com.teamProject.admin.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoDAO {
    private static OrderInfoDAO instance;

    private OrderInfoDAO() {

    }

    public static OrderInfoDAO getInstance() {
        if (instance == null)
            instance = new OrderInfoDAO();
        return instance;
    }

    public ArrayList<String> getOrderNumByOrderStep() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from order_info where `orderStep` = 'payReceive' order by `datePay` ";

        ArrayList<String> orderNumList = new ArrayList<String>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                orderNumList.add(rs.getString("orderNum"));
            }
            return orderNumList;
        } catch (Exception ex) {
            System.out.println("getOrderNumByOrderStep() 에러 : " + ex);
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

    public List<OrderInfoDTO> getOrderInfoListByOrderNum(String item) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select * from order_info where orderNum = " + item + " order by datePay";

        ArrayList<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();


        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
                orderInfoDTO.setOrderNum(rs.getString("orderNum"));
                orderInfoDTO.setMemberId(rs.getString("memberId"));
                orderInfoDTO.setOrderName(rs.getString("orderName"));
                orderInfoDTO.setOrderTel(rs.getString("orderTel"));
                orderInfoDTO.setOrderEmail(rs.getString("orderEmail"));
                orderInfoDTO.setReceiveName(rs.getString("receiveName"));
                orderInfoDTO.setReceiveTel(rs.getString("receiveTel"));
                orderInfoDTO.setReceiveAddress(rs.getString("receiveAddress"));
                orderInfoDTO.setPayAmount(rs.getInt("payAmount"));
                orderInfoDTO.setPayMethod(rs.getString("payMethod"));
                orderInfoDTO.setCarryNo(rs.getString("carryNo"));
                orderInfoDTO.setOrderStep(rs.getString("orderStep"));
                orderInfoDTO.setDateOrder(rs.getString("dateOrder"));
                orderInfoDTO.setDatePay(rs.getString("datePay"));
                orderInfoDTO.setDateCarry(rs.getString("dateCarry"));
                orderInfoDTO.setDateDone(rs.getString("dateDone"));
                list.add(orderInfoDTO);
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getOrderInfoListByOrderNum() 에러 : " + ex);
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

    public OrderInfoDTO getInfoDtoByOrderNum(String item) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();

        String sql = "select * from order_info where orderNum = '" + item + "'";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                orderInfoDTO.setOrderNum(rs.getString("orderNum"));
                orderInfoDTO.setMemberId(rs.getString("memberId"));
                orderInfoDTO.setOrderName(rs.getString("orderName"));
                orderInfoDTO.setOrderTel(rs.getString("orderTel"));
                orderInfoDTO.setOrderEmail(rs.getString("orderEmail"));
                orderInfoDTO.setReceiveName(rs.getString("receiveName"));
                orderInfoDTO.setReceiveTel(rs.getString("receiveTel"));
                orderInfoDTO.setReceiveAddress(rs.getString("receiveAddress"));
                orderInfoDTO.setPayAmount(rs.getInt("payAmount"));
                orderInfoDTO.setPayMethod(rs.getString("payMethod"));
                orderInfoDTO.setCarryNo(rs.getString("carryNo"));
                orderInfoDTO.setOrderStep(rs.getString("orderStep"));
                orderInfoDTO.setDateOrder(rs.getString("dateOrder"));
                orderInfoDTO.setDatePay(rs.getString("datePay"));
                orderInfoDTO.setDateCarry(rs.getString("dateCarry"));
                orderInfoDTO.setDateDone(rs.getString("dateDone"));
            }
            return orderInfoDTO;
        } catch (Exception ex) {
            System.out.println("getOrderInfoListByOrderNum() 에러 : " + ex);
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

    public void updateOrderStep(String orderNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
                String sql = "update `order_info` set `orderStep` = ? where `orderNum` = ? ";
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "shippingProgress");
                pstmt.setString(2, orderNum);
                pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateOrderStep() 에러 : " + e);
        }
        finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
