package seoulpublicwifi;

import java.sql.*;

public class dbConn {
	 public void close(ResultSet rs) {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void close(PreparedStatement ps) {
	        try {
	            if (ps != null && !ps.isClosed()) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void close(Connection conn) {
	        try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

}
