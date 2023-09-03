package com.teamProject.board.model;

import com.teamProject.board.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReportDAO {

    private static  ReportDAO instance;

    public static ReportDAO getInstance(){
        if(instance == null)
            instance = new ReportDAO();
        return  instance;
    }

    public boolean insertReport(ReportDTO report){

        Connection conn = null;
        PreparedStatement pstmt = null;
        int flag = 0;
        try{
            conn = DBConnection.getConnection();

            String sql = "INSERT INTO report VALUES(?, ?, ?, ?, ?, now())";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, report.getReportNum());
            pstmt.setInt(2, report.getBoardNum());
            pstmt.setString(3, report.getMemberId());
            pstmt.setString(4,report.getSubject());
            pstmt.setString(5, report.getReportContent());


            flag = pstmt.executeUpdate();
        } catch (Exception ex){
            System.out.println("insertreport() 에러 " + ex);
        } finally {
            try{
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    pstmt.close();
            } catch (Exception ex){
                throw  new RuntimeException(ex.getMessage());
            }
        }
        return  flag != 0;
    }
}
