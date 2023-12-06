package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	public static Connection getConnection() {
		
		Connection conn=null; // JDBC와의 연결 객체 초기화
		
		String url="jdbc:oracle:thin:@localhost:1521:xe"; // localhost는 ip 주소, xe는 SID 192.168.25.42
		String id="RYU";
		String pw="1234";
		String driver="oracle.jdbc.driver.OracleDriver";
		
		try {
			Class.forName(driver); // JDBC 드라이버 검색
			conn=DriverManager.getConnection(url, id, pw); // DBC URL -> DB 연결
			System.out.println("DB연결 완료");
		}catch(ClassNotFoundException e){ // JDBC driver 부재시 예외처리
			System.out.println("예외처리 1:"+e.getMessage()); // 예외 메시지 (console) 인쇄
			e.printStackTrace();
		}catch(Exception e) { // 예외상황
			System.out.println("예외처리 2:\"+e.getMessage()");
			e.printStackTrace();
		}
		
		return conn;
	}
	public static void close(Connection c, PreparedStatement p, ResultSet r) {
		try {
			if(r!=null)r.close();
			if(p!=null)p.close();
			if(c!=null)c.close();
		}catch(Exception e) {}
	}
	public static void close(Connection c, PreparedStatement p) {
		try {
			if(p!=null)p.close();
			if(c!=null)c.close();
		}catch(Exception e) {}
	}

	public static void main(String[] args) {
		getConnection();
	}
}