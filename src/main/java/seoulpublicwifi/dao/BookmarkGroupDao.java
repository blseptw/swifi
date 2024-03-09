package seoulpublicwifi.dao;

import java.sql.*;
import java.util.*;

import seoulpublicwifi.dbConn;
import seoulpublicwifi.dto.BookmarkGroupDto;

public class BookmarkGroupDao extends dbConn {
	public int insert(BookmarkGroupDto bookmarkGroupDto) {
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

            String sql = " INSERT INTO TB_BOOKMARK_GROUP (GROUP_NM, SEQUENCE, REG_DTTM) " 
            			+ " VALUES (?, ?, NOW()); ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, bookmarkGroupDto.getGroupNm());
            ps.setInt(2, bookmarkGroupDto.getSeq());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 그룹 데이터 삽입 성공");
            } else {
                System.out.println("북마크 그룹 데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        return affected;
    }

    public BookmarkGroupDto select(int id) {
        BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto();

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

            String sql = " SELECT * FROM TB_BOOKMARK_GROUP WHERE ID = ?; ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id2 = rs.getInt("ID");
                String groupNm = rs.getString("GROUP_NM");
                int seq = rs.getInt("SEQUENCE");
                Timestamp regDttm = rs.getTimestamp("REG_DTTM");
                Timestamp uptDttm = rs.getTimestamp("UPT_DTTM");

                bookmarkGroupDto.setId(id2);
                bookmarkGroupDto.setGroupNm(groupNm);
                bookmarkGroupDto.setSeq(seq);
                bookmarkGroupDto.setRegDttm(regDttm);
                bookmarkGroupDto.setUptDttm(uptDttm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return bookmarkGroupDto;
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
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        int affected = 0;

        try {
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);
            
            // 레코드 먼저 삭제
            String sql1 = "DELETE FROM TB_BOOKMARK WHERE GROUP_ID = ?";
            ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, id);
            ps1.executeUpdate();

            // 북마크 그룹 삭제
            String sql2 = "DELETE FROM TB_BOOKMARK_GROUP WHERE ID = ?";
            ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, id);
            affected = ps2.executeUpdate();

            if (affected > 0) {
                System.out.println("북마크 그룹 데이터 삭제 성공");
            } else {
                System.out.println("북마크 그룹 데이터 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps1);
            close(ps2);
            close(conn);
        }
        
        return affected;
    }

    public int update(BookmarkGroupDto bookmarkGroupDto) {
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

            String sql = " UPDATE TB_BOOKMARK_GROUP SET GROUP_NM = ?, SEQUENCE = ?, UPT_DTTM = NOW() WHERE ID = ?; ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, bookmarkGroupDto.getGroupNm());
            ps.setInt(2, bookmarkGroupDto.getSeq());
            ps.setInt(3, bookmarkGroupDto.getId());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 그룹 데이터 업데이트 성공");
            } else {
                System.out.println("북마크 그룹 데이터 업데이트 실패");
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

            String sql = " SELECT COUNT(*) FROM TB_BOOKMARK_GROUP; ";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        
        return count;
    }

    public List<BookmarkGroupDto> selectList() {
        List<BookmarkGroupDto> bookmarkGroupDtoList = new ArrayList<>();

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

            String sql = " SELECT * FROM TB_BOOKMARK_GROUP ORDER BY SEQUENCE ";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String GroupNm = rs.getString("GROUP_NM");
                int seq = rs.getInt("SEQUENCE");
                Timestamp regDttm = rs.getTimestamp("REG_DTTM");
                Timestamp uptDttm = rs.getTimestamp("UPT_DTTM");

                BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto();
                bookmarkGroupDto.setId(id);
                bookmarkGroupDto.setGroupNm(GroupNm);
                bookmarkGroupDto.setSeq(seq);
                bookmarkGroupDto.setRegDttm(regDttm);
                bookmarkGroupDto.setUptDttm(uptDttm);

                bookmarkGroupDtoList.add(bookmarkGroupDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        
        return bookmarkGroupDtoList;
    }
}
