package dao;

import db.DBClose;
import db.DBConnection;
import dto.MemberDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {

	private static MemberDao dao = null;
	
	private MemberDao() {
		DBConnection.initConnection();
	}
	
	public static MemberDao getInstance() {
		if(dao == null) {
			dao = new MemberDao();
		}
		return dao;
	}
	
	public int idcheck(String id) {
		/*
		String sql = " 	select count(*) "
				+ "		from member "
				+ "		where = '" + id + "' ";
		*/
		String sql = "select count(*) "
				+ "from member "
				+ "where id = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("idcheck 1/3 success");
				
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			System.out.println("idcheck 2/3 success");
			
			rs = psmt.executeQuery();			
			if(rs.next()) {
				count = rs.getInt(1);
			}		
			System.out.println("idcheck 3/3 success");
			
		} catch (SQLException e) {
			System.out.println("idcheck fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return count;
	}
	
	public boolean addMember(MemberDto mem) {
		
		String sql = " 	insert into member(id, pw, name, email, auth) "
				+ "		values(?, ?, ?, ?, 3) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("addMember 1/3 success");
				
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, mem.getId());
			psmt.setString(2, mem.getPw());
			psmt.setString(3, mem.getName());
			psmt.setString(4, mem.getEmail());
			System.out.println("addMember 2/3 success");
			
			count = psmt.executeUpdate();
			System.out.println("addMember 3/3 success");
			
		} catch (SQLException e) {
			System.out.println("addMember fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}

	public MemberDto login(String id, String pw){

		String sql = "select id, pw, name, email, auth " +
				"from member " +
				"where id=? and pw=?";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		MemberDto dto = null;
        try {
			conn = DBConnection.getConnection();
			System.out.println("login 1/3 success");

            psmt = conn.prepareStatement(sql);
			System.out.println("login 2/3 success");


			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();

			if (rs.next()) {
				String rsId = rs.getString("id");
				String rsPw = rs.getString("pw");
				String rsName = rs.getString("name");
				String rsEmail = rs.getString("email");
				int rsAuth = rs.getInt("auth");

				dto = new MemberDto(rsId, rsPw, rsName, rsEmail, rsAuth);
			}
			System.out.println("login 3/3 success");
		} catch (SQLException e) {
			System.out.println("login fail");
            throw new RuntimeException(e);
        } finally {
			DBClose.close(conn, psmt, rs);
		}

		return dto;
    }
}



