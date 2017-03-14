package net.slipp.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

	public void executeUpdate(String sql, PrepareStatementSetter pss) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql); 
			pss.setParameters(pstmt);
			
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public void executeUpdate(String sql, Object... parameters) throws SQLException {
		executeUpdate(sql,  createPreparedStatementSetter(parameters));
	}
	
	public <T> T executeQuery(String sql, RowMapper<T> rm, PrepareStatementSetter pss) throws SQLException {
		List<T> list = list(sql, rm, pss);
		if (list.isEmpty()) return null;
		
		return list.get(0);
	}
	
	public <T> T executeQuery(String sql, RowMapper<T> rm, Object... parameters) throws SQLException {
		return executeQuery(sql, rm,  createPreparedStatementSetter(parameters));
	}
	
	public <T> List<T> list(String sql, RowMapper<T> rm, PrepareStatementSetter pss) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql); 
			pss.setParameters(pstmt);
			
			rs = pstmt.executeQuery();
			List<T> list = new ArrayList<T>();
			
			while (rs.next()) {
				T row = rm.mapRow(rs);
				list.add(row);
			}
			
			return list;
			
		} finally {
			if (rs != null) {
				rs.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public <T> List<T> list(String sql, RowMapper<T> rm, Object... parameters) throws SQLException {
		return list(sql, rm,  createPreparedStatementSetter(parameters));
	}

	private PrepareStatementSetter createPreparedStatementSetter(Object... parameters) {
		return new PrepareStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i+1, parameters[i]);
				}
			}
		};
	}
}
