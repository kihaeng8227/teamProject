package com.teamProject.myPage.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.teamProject.admin.model.ProductsDTO;
import com.teamProject.myPage.database.DBConnection;

public class LikeDAO {
    private static LikeDAO instance;

    public LikeDAO() {

    }

    public static LikeDAO getInstance() {
        if (instance == null)
            instance = new LikeDAO();
        return instance;
    }


    public int getListCount(String sessionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int x = 0;

        String sql;
            sql = "select count(*) from jjim where memberId = ?";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionId);
            rs = pstmt.executeQuery();

            if (rs.next())
                x = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getListCount()�뿉�윭 : " + ex);
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

    //board �뀒�씠釉붿쓽 �젅肄붾뱶 媛��졇�삤湲�
    public ArrayList<LikeDTO> getBoardList(int page, int limit, String sessionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getListCount(sessionId);
        int start = (page - 1) * limit;
        int index = start + 1;

        String sql = "SELECT * FROM jjim where memberId = ? ORDER BY num DESC";

        ArrayList<LikeDTO> list = new ArrayList<LikeDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionId);
            rs = pstmt.executeQuery();

            // ResultSet .absolute(int index) : ResultSet 而ㅼ꽌瑜� �썝�븯�뒗 �쐞移� (Index)�쓽 寃��깋�뻾�쑝濡� �씠�룞�븯�뒗 硫붿꽌�뱶.
            while (rs.absolute(index)) {
            	LikeDTO jjim = new LikeDTO();
            	jjim.setMemberId(rs.getString("memberId"));
            	jjim.setProductId(rs.getString("productId"));
            	jjim.setProductName(rs.getString("productName"));
            	jjim.setFileName(rs.getString("fileName"));
                list.add(jjim);

                if (index < (start + limit) && index <= total_record)
                    index++;
                else
                    break;
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getBoardList�뿉�윭 : " + ex);
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

    public String getLoginNameById(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String name = null;
        String sql = "SELECT * FROM member WHERE id = ? ";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next())
                name = rs.getString("name");

            return name;
        } catch (Exception ex) {
            System.out.println("getLoginNameById�뿉�윭 : " + ex);
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
    
    
    
    
	public boolean getLike(ProductsDTO product, String sessionId) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		
		int flag = 0;
		String productId = product.getProductId();
		String sql = "SELECT * FROM jjim WHERE memberId = ? AND productId = ?";
		try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sessionId);
			pstmt.setString(2, productId);
            rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return flag == 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
		
	}
	
	
	
	
	public int insertLike(ProductsDTO product, String memberId) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		
		//동일한 주문번호에 같은 productId만 있으면 update, 없으면 insert
		int flag = 0;
		String productId = product.getProductId();
		String sql = "SELECT * FROM jjim WHERE memberId = ? AND productId = ?";
		try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, productId);
            rs = pstmt.executeQuery();
			
			if (rs.next()) {
				System.out.println("이미 찜한 상품");
				return flag = 0;
				
				
				
			}
			else {
				sql = "INSERT INTO jjim VALUES (null, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				pstmt.setString(2, productId);
				pstmt.setString(3, product.getProductName());
				pstmt.setString(4, product.getFileName());
				flag = pstmt.executeUpdate();
				return flag = 1;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(flag);
		return flag;
		
		
	}
	
	
	
	
	public boolean deleteLike(ProductsDTO product, String memberId) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		
		int flag = 0;
		String productId = product.getProductId();
		String sql = "Delete FROM jjim WHERE memberId = ? AND productId = ?";
		try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, productId);
			flag = pstmt.executeUpdate();
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}


}
