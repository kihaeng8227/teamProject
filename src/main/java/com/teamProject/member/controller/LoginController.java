package com.teamProject.member.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamProject.member.database.DBConnection;
import com.teamProject.member.model.MemberDAO;
import com.teamProject.member.model.MemberDTO;

@WebServlet("*.lo")
public class LoginController extends HttpServlet {

	//MemberDAO memberDAO = MemberDAO.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String RequestURI = req.getRequestURI(); // ��ü ��θ� ������.
        String contextPath = req.getContextPath(); // ������Ʈ Path�� ������.
        String command =  RequestURI.substring(contextPath.length()); // ��ü��ο��� ������Ʈ Path ���� ��ŭ�� �ε��� ������ ���ڿ��� ������.

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
		
		
        
        // �α��� ������
        if(command.contains("/LoginPage.lo")) { // �α��� ������ ���
        	
            RequestDispatcher rd = req.getRequestDispatcher("/member/loginMember.jsp");
            if (rd != null) {
                rd.forward(req, resp);
            } else {
                // ���� ó�� �ڵ�
        		// rd = req.getRequestDispatcher("/error/404error.jsp");
            }
        }
        else if(command.contains("/Login.lo")) { // �α��� ��ư ������ ��
        	RequestDispatcher rd = null;
        	
        	try {
        		int loginResult = requestLoginMember(req, resp);
				if(loginResult == 0) { // state == 0 �Ϲ�ȸ�� �α���
					rd = req.getRequestDispatcher("/index.jsp");
					// ��ĥ�� �ε����� ���� �����
				}
				else if(loginResult == 1) { // state == 1 �̿� ���� ��
					rd = req.getRequestDispatcher("/member/loginFailed.jsp");
					rd.forward(req, resp);
				}
				else if(loginResult == 2) { // state == 2 Ż���� ȸ��
					rd = req.getRequestDispatcher("/member/loginFailed.jsp");
					rd.forward(req, resp);
				}
				else if(loginResult == 3) { // state == 3 ������ �α���
					rd = req.getRequestDispatcher("/admin/AdminProductsList.ad");
				}
				else {
					rd = req.getRequestDispatcher("/member/loginMember.jsp?error=1");
					rd.forward(req, resp);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        	
        	rd.forward(req, resp);
        }
        else if(command.contains("/AddMemberPage.lo")) { // ȸ�� ������������ �̵�
        	
        	RequestDispatcher rd = req.getRequestDispatcher("/member/addMember.jsp");
            rd.forward(req, resp);
        }
        else if(command.contains("/AddMember.lo")) { // ȸ������ ��ư Ŭ����
        	addMember(req, resp);
        	RequestDispatcher rd = req.getRequestDispatcher("/member/resultMember.jsp?msg=1");
            rd.forward(req, resp);
            
            
        }
        else if(command.contains("AjaxIdCheck.lo")) { // ȸ�� ���̵� �ߺ� Ȯ��
        	//idCheck(req, resp);
        	String result = idCheck(req, resp);
        	req.setAttribute("result", result);
        	RequestDispatcher rd = req.getRequestDispatcher("/member/popupIdCheck.jsp");
            rd.forward(req, resp);
        }
        else if(command.contains("UpdateMemberPage.lo")) { // ȸ�� �������� �̵�
        	moveUpdateMember(req, resp);
        	RequestDispatcher rd = req.getRequestDispatcher("/member/updateMember.jsp");
            rd.forward(req, resp);
        }
        else if(command.contains("UpdateMember.lo")) { // ȸ�� �����ϱ� ��ư Ŭ����
        	updateMember(req, resp);
        	RequestDispatcher rd = req.getRequestDispatcher("/member/resultMember.jsp?msg=0");
            rd.forward(req, resp);
        }
        else if(command.contains("Logout.lo")) { // �α׾ƿ� ��ư Ŭ�� ��
        	logoutMember(req, resp);
        	RequestDispatcher rd = req.getRequestDispatcher("/LoginPage.lo");
            rd.forward(req, resp);
        }
        else if(command.contains("WithdrawalMember.lo")) { // ȸ�� Ż�� ��ư Ŭ����
        	withdrawalMember(req, resp);
        	
        	RequestDispatcher rd = req.getRequestDispatcher("/LoginPage.lo");
            rd.forward(req, resp);
        }
        
        
	}

	

	private void withdrawalMember(HttpServletRequest req, HttpServletResponse resp) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        
        HttpSession session = req.getSession();
        
        String sessionId = session.getAttribute("sessionId").toString();
		
        
        
        try {
				
				String sql = "UPDATE member set state=2 where id=?";
				conn = DBConnection.getConnection();
				pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, sessionId);
	            pstmt.executeUpdate();
	            
	            logoutMember(req, resp);
	        
            
    } catch (Exception e) {
        System.out.println("withdrawalMember() ���� : " + e);
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

	private void logoutMember(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		
		session.invalidate();
	}

	private void updateMember(HttpServletRequest req, HttpServletResponse resp) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        HttpSession session = req.getSession();
    	
		
    	
    	
    	
    	String id = (String) session.getAttribute("sessionId");
    	//String id = request.getParameter("id"); �̷��� ���� ��ŷ ����
    	String password = req.getParameter("password");
    	String name = req.getParameter("name");
    	String gender = req.getParameter("gender");
    	String year = req.getParameter("birthyy");
    	String month = req.getParameterValues("birthmm")[0];
    	String day = req.getParameter("birthdd");
    	String birth = year + "/" + month + "/" + day;
    	String mail1 = req.getParameter("mail1");
    	String mail2 = req.getParameterValues("mail2")[0];
    	String mail = mail1 + "@" + mail2;
    	String zipcode = req.getParameter("zipcode");
    	String address1 = req.getParameter("address1");
    	String address2 = req.getParameter("address2");
    	String address = zipcode + "/" + address1 + "/" + address2;
    	String phone1 = req.getParameterValues("phone1")[0];
    	String phone2 = req.getParameter("phone2");
    	String phone3 = req.getParameter("phone3");
    	String phone = phone1 + "-" + phone2 + "-" + phone3;
    	String receive_mail = req.getParameter("mailYN");
    	String receive_phone = req.getParameter("smsYN");
    	String agreement = req.getParameter("termsYN");
    	
    	
    	
    	
    	
        
        try {
                String sql = "UPDATE member set password=?, name=?, birth=?, gender=?, mail=?,"
                		+ "address=?, phone=?, receive_mail=?, receive_phone=?, agreement=? where id=?";
                
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, req.getParameter("password"));
                pstmt.setString(2, req.getParameter("name"));
                pstmt.setString(3, birth);
                pstmt.setString(4, req.getParameter("gender"));
                pstmt.setString(5, mail);
                pstmt.setString(6, address);
                pstmt.setString(7, phone);
                pstmt.setString(8, receive_mail);
                pstmt.setString(9, receive_phone);
                pstmt.setString(10, agreement);
                pstmt.setString(11, req.getParameter("id"));
                pstmt.executeUpdate();
                
                req.setAttribute("msg", 0);
        } catch (Exception e) {
            System.out.println("updateMember() ���� : " + e);
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

	private String idCheck(HttpServletRequest req, HttpServletResponse resp) {
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rowCount = 0;
        String result = "true";
		
        String id = req.getParameter("id");
        
		try {
            String sql = "SELECT COUNT(*) AS cnt FROM member WHERE id=?";

            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
				rowCount = rs.getInt(1);
			}
			if(rowCount == 0) {
				result = "false";
				
			}
            
            
    } catch (Exception e) {
        System.out.println("idCheck() ���� : " + e);
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
		return result;
		
	}

	private void addMember(HttpServletRequest req, HttpServletResponse resp) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        
    	
    	String year = req.getParameter("birthyy");
    	String month = req.getParameterValues("birthmm")[0];
    	String day = req.getParameter("birthdd");
    	String birth = year + "/" + month + "/" + day;
    	
    	String mail1 = req.getParameter("mail1");
    	String mail2 = req.getParameterValues("mail2")[0];
    	String mail = mail1 + "@" + mail2;
    	String zipcode = req.getParameter("zipcode");
    	String address1 = req.getParameter("address1");
    	String address2 = req.getParameter("address2");
    	String address = zipcode + "/" + address1 + "/" + address2; 
    	String phone1 = req.getParameterValues("phone1")[0];
    	String phone2 = req.getParameter("phone2");
    	String phone3 = req.getParameter("phone3");
    	String phone = phone1 + "-" + phone2 + "-" + phone3;
    	
        
        try {
                String sql = "INSERT INTO member VALUES (?,?,?,?,?,?,?,?,?,?,?,now(),0)";
                conn = DBConnection.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, req.getParameter("id"));
                pstmt.setString(2, req.getParameter("password"));
                pstmt.setString(3, req.getParameter("name"));
                pstmt.setString(4, birth);
                pstmt.setString(5, req.getParameter("gender"));
                pstmt.setString(6, mail);
                pstmt.setString(7, address);
                pstmt.setString(8, phone);
                pstmt.setString(9, req.getParameter("mailYN"));
                pstmt.setString(10, req.getParameter("smsYN"));
                pstmt.setString(11, req.getParameter("termsYN"));
                pstmt.executeUpdate();
                
                req.setAttribute("msg", 1);
        } catch (Exception e) {
            System.out.println("addMember() ���� : " + e);
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
	
	
	private int requestLoginMember(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException {

		int result = 0;
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HttpSession session = req.getSession();
		
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		String sql = "SELECT state FROM member WHERE id = ? AND password = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) == 0) {
					session.setAttribute("sessionId", id);
					//session.setAttribute("sessionMemberName", rs.getString("name"));
					
					//CartDAO cartDAO = new CartDAO();
					//cartDAO.updateCartBylogin(session);
					
					//resp.sendRedirect("resultMember.jsp?msg=2");
					result = 0;
					return result;
				}
				else if(rs.getInt(1) == 1) {
					
					req.setAttribute("result", 1);
					result = 1;
					return result;
				}
				else if(rs.getInt(1) == 2) {
					
					req.setAttribute("result", 2);
					result = 2;
					return result;
				}
				else if(rs.getInt(1) == 3) {
					
					session.setAttribute("sessionId", id);
					result = 3;
					return result;
				}
				
				
			}
			else{
				//resp.sendRedirect("loginMember.jsp?error=1");
				result = 4;
				req.setAttribute("error", 1);
				
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	private void moveUpdateMember(HttpServletRequest req, HttpServletResponse resp) {
	      Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        HttpSession session = req.getSession();
	        MemberDTO memberDTO = new MemberDTO();
	        
	        String id = (String) session.getAttribute("sessionId");
	        
	        try {
	            String sql = "SELECT * FROM MEMBER WHERE ID=?";
	            
	            conn = DBConnection.getConnection();
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setString(1, id);
	            rs = pstmt.executeQuery();
	            
	            if(rs.next()) {
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
	            }
	            
	            req.setAttribute("memberDTO", memberDTO);
	            //req.setAttribute("sessionId", id);
	            
	            
	    } catch (Exception e) {
	        System.out.println("moveUpdateMember() 에러 : " + e);
	    }
	    finally {
	        try {
	            if (pstmt != null)
	                pstmt.close();
	            if (conn != null)
	                conn.close();
	            if (rs != null)
	               rs.close();
	        } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	        }
	    }
	        
	   }
	
}
