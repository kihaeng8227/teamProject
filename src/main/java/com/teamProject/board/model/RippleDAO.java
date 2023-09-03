package com.teamProject.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.teamProject.board.database.DBConnection;

public class RippleDAO {
    private static RippleDAO instance;

    public static RippleDAO getInstance() {
        if(instance == null)
            instance = new RippleDAO();
        return instance;
    }

    public boolean insertRipple(RippleDTO ripple) {  //댓글 등록 boolean형으로

        Connection conn = null;
        PreparedStatement pstmt = null;
        int flag = 0;
        try {
            conn = DBConnection.getConnection();

            String sql = "INSERT INTO ripple VALUES(?, ?, ?, ?, ?, ?, now(), ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ripple.getRippleId());
            pstmt.setString(2, ripple.getBoardName());
            pstmt.setInt(3, ripple.getBoardNum());
            pstmt.setString(4, ripple.getMemberId());
            pstmt.setString(5, ripple.getName());
            pstmt.setString(6, ripple.getContent());
            pstmt.setString(7, ripple.getIp());

            flag = pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("insertripple() 에러 : " + ex);
        } finally {
            try {
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    conn.close();
            } catch(Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return flag != 0;
    }
    public ArrayList<RippleDTO> getRippleList(String boardName, int boardNum){ //댓글 목록
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM ripple WHERE boardName=? AND boardNum = ?";
        ArrayList<RippleDTO> list = new ArrayList<>();

        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, boardName);
            preparedStatement.setInt(2, boardNum);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                RippleDTO ripple = new RippleDTO();
                ripple.setRippleId(resultSet.getInt("rippleId"));
                ripple.setBoardName(resultSet.getString("boardName"));
                ripple.setBoardNum(resultSet.getInt("boardNum"));
                ripple.setMemberId(resultSet.getString("memberId"));
                ripple.setName(resultSet.getString("name"));
                ripple.setContent(resultSet.getString("content"));
                ripple.setInsertDate(resultSet.getString("insertDate"));
                ripple.setIp(resultSet.getString("ip"));
                list.add(ripple);
            }
        } catch(Exception ex) {
            System.out.println("getRippleList() 에러: " +ex);
        }finally {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(preparedStatement != null)
                    preparedStatement.close();
                if(connection != null)
                    connection.close();
            } catch(Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return list;
    }

    public boolean deleteRipple(RippleDTO ripple) {  //댓글 삭제하기 boolean 형으로
        Connection conn = null;
        PreparedStatement pstmt = null;
        int flag = 0;

        try {
            String sql = "DELETE FROM ripple WHERE rippleId =? ";
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ripple.getRippleId());

            flag = pstmt.executeUpdate();

        } catch(Exception ex) {
            System.out.println("deleteBoard() 에러 : " + ex);
        } finally {
            try {
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    conn.close();
            } catch(Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return flag != 0;

    }
}



