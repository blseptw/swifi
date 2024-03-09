package seoulpublicwifi.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import seoulpublicwifi.dbConn;
import seoulpublicwifi.dto.HistoryDto;

public class HistoryDao extends dbConn {
	public int insert(HistoryDto historyDto) {
		String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "test1";
        String dbPassword = "zerobase";
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        int affected = 0;

        try {
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " INSERT INTO TB_HISTORY (LAT, LNT, SRCH_DTTM) "
            		+ " VALUES (?, ?, NOW()); ";

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, historyDto.getLat());
            ps.setDouble(2, historyDto.getLnt());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("히스토리 데이터 삽입 성공");
            } else {
                System.out.println("히스토리 데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public int delete(int id) {
    	String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "test1";
        String dbPassword = "zerobase";
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        int affected = 0;

        try {
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " DELETE FROM TB_HISTORY WHERE ID = ?; ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("히스토리 데이터 삭제 성공");
            } else {
                System.out.println("히스토리 데이터 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public int count() {
        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "test1";
        String dbPassword = "zerobase";
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        int count = 0;

        try {
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " SELECT COUNT(*) FROM TB_HISTORY; ";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return count;
    }

    public List<HistoryDto> selectList() {
        List<HistoryDto> historyDtoList = new ArrayList<>();

        String url = "jdbc:mariadb://localhost:3306/testdb1";
        String dbUserId = "test1";
        String dbPassword = "zerobase";
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " SELECT * FROM TB_HISTORY ORDER BY ID DESC ";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                double lat = rs.getDouble("LAT");
                double lnt = rs.getDouble("LNT");
                Timestamp srchDttm = rs.getTimestamp("SRCH_DTTM");

                HistoryDto historyDto = new HistoryDto();
                historyDto.setId(id);
                historyDto.setLat(lat);
                historyDto.setLnt(lnt);
                historyDto.setSrchDttm(srchDttm);

                historyDtoList.add(historyDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return historyDtoList;
    }

}
