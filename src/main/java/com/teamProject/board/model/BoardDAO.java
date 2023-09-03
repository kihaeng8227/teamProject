package com.teamProject.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.teamProject.board.database.DBConnection;


public class BoardDAO {
    // DAO(Data Access Object) 는 데이터 베이스의 data에 접근하기 위한 객체
    // 싱글턴 타입으로 작성.
    private static BoardDAO instance;

    public BoardDAO() {  // 검색하면서 수정한 거 private >>> public 으로

    }

    public static BoardDAO getInstance() {
        if (instance == null)
            instance = new BoardDAO();
        return instance;
    }

    //board 테이블의 레코드 개수
    public int getListCount(String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int x = 0;

        String sql;

        if (items == null && text == null)
            sql = "SELECT count(*) FROM board";
        else
            sql = "select count(*) from board WHERE " + items + " LIKE '%" + text + "%'";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next())
                x = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getListCount()에러 : " + ex);
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

    //board 테이블의 레코드 가져오기
    public ArrayList<BoardDTO> getBoardList(int page, int limit, String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getListCount(items, text);
        int start = (page - 1) * limit;
        int index = start + 1;

        String sql;
        //검색
        if (items == null && text == null)
            sql = "SELECT * FROM board ORDER BY num DESC";
        else
            sql = "SELECT * FROM board where " + items + " like '%" + text + "%' ORDER BY num DESC ";

        ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // ResultSet .absolute(int index) : ResultSet 커서를 원하는 위치 (Index)의 검색행으로 이동하는 메서드.
            while (rs.absolute(index)) {
                BoardDTO board = new BoardDTO();
                board.setNum(rs.getInt("num"));
                board.setId(rs.getString("id"));
                board.setName(rs.getString("name"));
                board.setSubject(rs.getString("subject"));
                board.setContent(rs.getString("content"));
                board.setRegist_day(rs.getString("regist_day"));
                board.setHit(rs.getInt("hit"));
                board.setIp(rs.getString("ip"));
                list.add(board);

                if (index < (start + limit) && index <= total_record)
                    index++;
                else
                    break;
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getBoardList에러 : " + ex);
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
            System.out.println("getLoginNameById에러 : " + ex);
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

    public void insertBoard(BoardDTO board) { //게시물 등록

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();

            String sql = "INSERT INTO board VALUES(?, ?, ?, ?, ?, now(), ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, board.getNum());
            pstmt.setString(2, board.getId());
            pstmt.setString(3, board.getName());
            pstmt.setString(4, board.getSubject());
            pstmt.setString(5, board.getContent());
            pstmt.setInt(6, board.getHit());
            pstmt.setString(7, board.getIp());
            pstmt.setString(8, board.getFilename());
            pstmt.setLong(9, board.getFilesize());

            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("insertBoard() 에러 : " + ex);
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

    public BoardDTO getBoardByNum(int num, int page) { //글 선택

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardDTO board = null;

        updateHit(num);
        String sql = "SELECT * FROM board WHERE num = ? ";


        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new BoardDTO();
                board.setNum(rs.getInt("num"));
                board.setId(rs.getString("id"));
                board.setName(rs.getString("name"));
                board.setSubject(rs.getString("subject"));
                board.setContent(rs.getString("content"));
                board.setRegist_day(rs.getString("regist_day"));
                board.setHit(rs.getInt("hit"));
                board.setIp(rs.getString("ip"));
                board.setFilename(rs.getString("filename"));
            }
            return board;
        } catch (Exception ex) {
            System.out.println("insertBoard() 에러 : " + ex);
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

    private void updateHit(int num) {  //선택글의 조회수 증가하기
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE board SET hit = hit + 1 WHERE num =? ";
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("updateHit() 에러 : " + ex);
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

    public void updateBoard(BoardDTO board) {  //선택된 글 내용 수정하기
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            System.out.println(board.getFilename());
            String sql = "UPDATE board SET name=?, subject = ?, content = ?, filename =? WHERE num = ?";
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, board.getName());
            pstmt.setString(2, board.getSubject());
            pstmt.setString(3, board.getContent());
            pstmt.setString(4, board.getFilename());
            pstmt.setInt(5, board.getNum());

            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("updateBoard() 에러 : " + ex);
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

    public void deleteBoard(int num) {  //선택글 삭제하기
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM board WHERE num =? ";
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.executeQuery();

        } catch (Exception ex) {
            System.out.println("deleteBoard() 에러 : " + ex);
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

    public String getFileName(int num) {  //파일 이름 가져오기
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardDTO board = null;

        String sql = "select filename, filesize FROM board WHERE num =? ";


        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new BoardDTO();
                board.setFilename(rs.getString("filename"));
                board.setFilesize(rs.getLong("filesize"));
            }

        } catch (Exception ex) {
            System.out.println("getFileName() 에러 : " + ex);
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
        return board.getFilename();

    }

	public String deleteImg(int num) {  //������ �̹��� ����
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//BoardDTO board = new BoardDTO();
		
		try {  
			String sql = "UPDATE board SET filename = null WHERE num = ?";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
		} catch(Exception ex) {
			System.out.println("deleteImg() ���� : " + ex);
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
		return null;
	}
    
    
    
    
    
    
    
    //추가~!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public int getListCount(String sessionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int x = 0;

        String sql;
        sql = "select count(*) from board where id = ?";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionId);
            rs = pstmt.executeQuery();

            if (rs.next())
                x = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getListCount() 뿉 윭 : " + ex);
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





    public List<BoardDTO> getboardlist(int page, int limit, String sessionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getListCount(sessionId);
        int start = (page - 1) * limit;
        int index = start + 1;

        String sql = "SELECT * FROM board where id = ? ORDER BY num DESC";

        ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionId);
            rs = pstmt.executeQuery();

            // ResultSet .absolute(int index) : ResultSet 而ㅼ꽌瑜   썝 븯 뒗  쐞移  (Index) 쓽 寃  깋 뻾 쑝濡   씠 룞 븯 뒗 硫붿꽌 뱶.
            while (rs.absolute(index)) {
            	BoardDTO boardDTO = new BoardDTO();
                boardDTO.setNum(rs.getInt("num"));
                boardDTO.setId(rs.getString("id"));
                boardDTO.setName(rs.getString("name"));
                boardDTO.setSubject(rs.getString("subject"));
                boardDTO.setContent(rs.getString("content"));
                boardDTO.setRegist_day(rs.getString("regist_day"));
                boardDTO.setHit(rs.getInt("hit"));
                boardDTO.setIp(rs.getString("ip"));
                boardDTO.setFilename(rs.getString("filename"));
                boardDTO.setFilesize(rs.getInt("filesize"));
                list.add(boardDTO);

                if (index < (start + limit) && index <= total_record)
                    index++;
                else
                    break;
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getBoardList 뿉 윭 : " + ex);
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


