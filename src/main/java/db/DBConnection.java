package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static void initConnection(){
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Driver Loading Success");
	}

	public static Connection getConnection(){
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/ssginc", "root", "root@mysql");
			System.out.println("MariaDB Connection Success");

		} catch (SQLException e) {
			System.out.println("MariaDB에 연결할 수 없습니다.");
			e.printStackTrace();
		}
		return conn;
	}
}
