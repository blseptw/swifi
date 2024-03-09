package seoulpublicwifi.dao;

import java.sql.*;
import java.util.*;

import seoulpublicwifi.dbConn;
import seoulpublicwifi.dto.BookmarkDto;

public class BookmarkDao extends dbConn {
	public int insert(BookmarkDto bookmarkDto) {
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

            String sql = " INSERT INTO TB_BOOKMARK (GROUP_ID, MGR_NO, REG_DTTM) VALUES (?, ?, NOW()) "
            			+ " ON DUPLICATE KEY UPDATE MGR_NO = ?, REG_DTTM = NOW(); ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookmarkDto.getGroupId());
            ps.setString(2, bookmarkDto.getMgrNo());
            ps.setString(3, bookmarkDto.getMgrNo());

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 데이터 삽입 성공");
            } else {
                System.out.println("북마크 데이터 삽입 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            close(conn);
        }
        
        return affected;
    }
	
	public BookmarkDto select(int id) {
        BookmarkDto bookmarkDto = new BookmarkDto();

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

            String sql = " SELECT * FROM TB_BOOKMARK WHERE ID = ?; ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id2 = rs.getInt("ID");
                int groupId = rs.getInt("GROUP_ID");
                String mgrNo = rs.getString("MGR_NO");
                Timestamp regDttm = rs.getTimestamp("REG_DTTM");

                bookmarkDto.setId(id2);
                bookmarkDto.setGroupId(groupId);
                bookmarkDto.setMgrNo(mgrNo);
                bookmarkDto.setRegDttm(regDttm);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        
        return bookmarkDto;
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

            String sql = " DELETE FROM TB_BOOKMARK WHERE ID = ?; ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("북마크 데이터 삭제 성공");
            } else {
                System.out.println("북마크 데이터 삭제 실패");
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

            String sql = " SELECT COUNT(*) FROM TB_BOOKMARK; ";

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
	
	public List<BookmarkDto> selectList() {
        List<BookmarkDto> bookmarkDtoList = new ArrayList<>();

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

            String sql = " SELECT TB_BOOKMARK.* " +
                    " FROM TB_BOOKMARK " +
                    " INNER JOIN TB_BOOKMARK_GROUP " +
                    " ON TB_BOOKMARK.GROUP_ID = TB_BOOKMARK_GROUP.ID " +
                    " ORDER BY TB_BOOKMARK.ID ASC, TB_BOOKMARK_GROUP.SEQUENCE ";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                int groupId = rs.getInt("GROUP_ID");
                String mgrNo = rs.getString("MGR_NO");
                Timestamp regDttm = rs.getTimestamp("REG_DTTM");

                BookmarkDto bookmarkDto = new BookmarkDto();
                bookmarkDto.setId(id);
                bookmarkDto.setGroupId(groupId);
                bookmarkDto.setMgrNo(mgrNo);
                bookmarkDto.setRegDttm(regDttm);

                bookmarkDtoList.add(bookmarkDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        
        return bookmarkDtoList;
    }

}
