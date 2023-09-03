package com.teamProject.admin.model;

import com.teamProject.admin.database.DBConnection;
import com.teamProject.board.model.BoardDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {
    /* DAO(Data Access Object)는 데이터베이스의 data에 접근하기 위한 객체 */
    /* 싱글턴 타입으로 작성. */
    private static ProductsDAO instance;

    public ProductsDAO() {

    }

    public static ProductsDAO getInstance() {
        if (instance == null)
            instance = new ProductsDAO();
        return instance;
    }


    public void insertProduct(ProductsDTO productsDTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();

            String sql = "INSERT into `products` values(?, ?, ?, ?, ?, ?, ?, now())";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productsDTO.getProductId());
            pstmt.setString(2, productsDTO.getProductName());
            pstmt.setInt(3, productsDTO.getProductPrice());
            pstmt.setString(4, productsDTO.getDescription());
            pstmt.setString(5, productsDTO.getCategory());
            pstmt.setInt(6, productsDTO.getProductsInStock());
            pstmt.setString(7, productsDTO.getFileName());

            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("insertProduct() 에러 : " + ex);
        } finally {
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
            sql = "SELECT count(*) FROM products";
        else
            sql = "select count(*) from products where " + items + " like '%" + text + "%'";

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

    public List<ProductsDTO> getProductList(int pageNum, int limit, String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getListCount(items, text);
        int start = (pageNum - 1) * limit;
        int index = start + 1;

        String sql;

        if (items == null && text == null)
            sql = "select * from products order by regist_day DESC";
        else
            sql = "select * from products where " + items + " like '%" + text + "%' order by regist_day DESC ";

        ArrayList<ProductsDTO> list = new ArrayList<ProductsDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // ResultSet.absolute(int index) : ResultSet 커서를 원하는 위치(Index)의 검색행으로 이동하는 메서드. 해당 인덱스가 존재하면 true반환. 없으면 false 반환.
            while (rs.absolute(index)) {
                ProductsDTO productsDTO = new ProductsDTO();
                productsDTO.setProductId(rs.getString("productId"));
                productsDTO.setProductName(rs.getString("productName"));
                productsDTO.setProductPrice(rs.getInt("productPrice"));
                productsDTO.setDescription(rs.getString("description"));
                productsDTO.setCategory(rs.getString("category"));
                productsDTO.setProductsInStock(rs.getInt("productsInStock"));
                productsDTO.setFileName(rs.getString("fileName"));
                productsDTO.setRegist_day(rs.getString("regist_day"));
                list.add(productsDTO);

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


    public ProductsDTO getBoardByProductId(String productId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ProductsDTO productsDTO = null;

        String sql = "select * from `products` where `productId` = ? ";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                productsDTO = new ProductsDTO();
                productsDTO.setProductId(rs.getString("productId"));
                productsDTO.setProductName(rs.getString("productName"));
                productsDTO.setProductPrice(rs.getInt("productPrice"));
                productsDTO.setDescription(rs.getString("description"));
                productsDTO.setCategory(rs.getString("category"));
                productsDTO.setProductsInStock(rs.getInt("productsInStock"));
                productsDTO.setFileName(rs.getString("fileName"));
                productsDTO.setRegist_day(rs.getString("regist_day"));
            }
            return productsDTO;
        } catch (Exception ex) {
            System.out.println("getBoardByProductId() 에러 : " + ex);
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


    public void updateProduct(ProductsDTO productsDTO, HttpServletRequest req) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql;
            if (productsDTO.getFileName() == null) {
                sql = "update `products` set `productId` = ?, `productName` = ?, `productPrice` = ?, `description` = ?, `category` = ?, `productsInStock` = ? where `productId` = ? ";
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, productsDTO.getProductId());
                pstmt.setString(2,productsDTO.getProductName());
                pstmt.setInt(3,productsDTO.getProductPrice());
                pstmt.setString(4,productsDTO.getDescription());
                pstmt.setString(5,productsDTO.getCategory());
                pstmt.setInt(6,productsDTO.getProductsInStock());
                pstmt.setString(7,req.getParameter("oldProductId"));
                pstmt.executeUpdate();
            } else {
                sql = "update `products` set `productId` = ?, `productName` = ?, `productPrice` = ?, `description` = ?, `category` = ?, `productsInStock` = ?, `fileName` = ? where `productId` = ? ";
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, productsDTO.getProductId());
                pstmt.setString(2,productsDTO.getProductName());
                pstmt.setInt(3,productsDTO.getProductPrice());
                pstmt.setString(4,productsDTO.getDescription());
                pstmt.setString(5,productsDTO.getCategory());
                pstmt.setInt(6,productsDTO.getProductsInStock());
                pstmt.setString(7,productsDTO.getFileName());
                pstmt.setString(8,req.getParameter("oldProductId"));
                pstmt.executeUpdate();
            }
            System.out.println("id = " + productsDTO.getProductId());

        } catch (Exception e) {
            System.out.println("updateProduct() 에러 : " + e);
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

    public void deleteProduct(String productId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String sql = "delete from `products` where productId = ? ";

            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, productId);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("deleteProduct() 에러 : " + ex);
        } finally {
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

    public void updateCnt(List<ProductsDTO> productsDTOList) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            for (ProductsDTO product : productsDTOList) {
                String sql = "update `products` set `productsInStock` = `productsInStock` - ? where `productId` = ? ";
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, product.getProductsInStock());
                pstmt.setString(2, product.getProductId());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updateCnt() 에러 : " + e);
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

	public ProductsDTO getBoardByNum(String productId, int pageNum) {
		  Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        ProductsDTO productsDTO = null;

	        String sql = "SELECT * FROM products WHERE productId = ? ";


	        try {
	            conn = DBConnection.getConnection();
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, productId);
	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	productsDTO = new ProductsDTO();
	            	productsDTO.setProductId(rs.getString("productId"));
	            	productsDTO.setProductName(rs.getString("productName"));
	            	productsDTO.setProductPrice(rs.getInt("productPrice"));
	            	productsDTO.setDescription(rs.getString("description"));
	            	productsDTO.setProductsInStock(rs.getInt("productsInStock"));
	            	productsDTO.setFileName(rs.getString("fileName"));
	            	productsDTO.setRegist_day(rs.getString("regist_day"));
	            }
	            return productsDTO;
	        } catch (Exception ex) {
	            System.out.println("getBoardByNum() 에러 : " + ex);
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
	
	
	public boolean checkProductId(String productId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM `products` WHERE productId = ?";

        boolean flag = true; // 동일한 아이디가 없을 경우

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                flag = false; // 동일한 아이디가 있는 경우
            } else {
                flag = true;
            }
        } catch (Exception ex) {
            System.out.println("checkProductId() 에러 : " + ex);
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
        return flag;

    }
}
