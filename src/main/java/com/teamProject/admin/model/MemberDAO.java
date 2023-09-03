package com.teamProject.admin.model;

import com.teamProject.admin.database.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private static MemberDAO instance;

    private MemberDAO() {

    }

    public static MemberDAO getInstance() {
        if (instance == null)
            instance = new MemberDAO();
            return instance;
    }


    public void updateUserState(HttpServletRequest req) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
                String sql = "update `member` set `state` = ? where `id` = ? ";
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, req.getParameter("state"));
                pstmt.setString(2, req.getParameter("id"));
                pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateUserState() 에러 : " + e);
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

    public int getListCount(String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int x = 0; // 레코드 개수 저장할 변수.

        String sql;

        if (items == null && text == null)
            sql = "SELECT count(*) FROM member";
        else
            sql = "select count(*) from member where " + items + " like '%" + text + "%'";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next())
                x = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getListCount() 에러: " + ex);
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


    public List<MemberDTO> getMemberList(int pageNum, int limit, String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getListCount(items, text);
        int start = (pageNum - 1) * limit;
        int index = start + 1;

        String sql;

        if (items == null && text == null)
            sql = "select * from member order by regist_day DESC";
        else
            sql = "select * from member where " + items + " like '%" + text + "%' order by regist_day DESC ";

        ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // ResultSet.absolute(int index) : ResultSet 커서를 원하는 위치(Index)의 검색행으로 이동하는 메서드. 해당 인덱스가 존재하면 true반환. 없으면 false 반환.
            while (rs.absolute(index)) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setId(rs.getString("id"));
                memberDTO.setPassword(rs.getString("password"));
                memberDTO.setName(rs.getString("name"));
                memberDTO.setBirth(rs.getString("birth"));
                memberDTO.setGender(rs.getString("gender"));
                memberDTO.setMail(rs.getString("mail"));
                memberDTO.setAddress(rs.getString("address"));
                memberDTO.setPhone(rs.getString("phone"));
                memberDTO.setReceive_mail(rs.getString("receive_mail"));
                memberDTO.setReceive_phone(rs.getString("receive_phone"));
                memberDTO.setAgreement(rs.getString("agreement"));
                memberDTO.setRegist_day(rs.getString("regist_day"));
                memberDTO.setState(rs.getInt("state"));

                list.add(memberDTO);

                if (index < (start + limit) && index <= total_record)
                    index++;
                else
                    break;
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getProductList() 에러 : " + ex);
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
