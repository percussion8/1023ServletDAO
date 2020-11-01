package green.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnectionPool {
	String url;
	String username;
	String password;
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String url, String user, String pass) throws Exception {
		this.url = url;
		this.password = pass;
		this.username = user;
		Class.forName(driver);
	}
	
	public Connection getConnection() throws Exception {  //이해 덜 됐음
		if(connList.size() > 0) {
			Connection conn = connList.remove(0);
			if(conn.isValid(10)) {
				return conn;
			}
		}
		return DriverManager.getConnection(url, username, password);
	}
	//getConnection 메서드, 파라미터는 없고 반환타입은 Connection 타입 
		//함수정의문 옆에 throws Exception 추가 
		//if 문으로 멤버변수 connList의 크기를 알아내는 size 메서드 호출하고 그 크기가 0보다 크면 
		//connList를 이용하여 Connection 객체 0번째 Connection객체 제거 (remove(0) 호출)
		//remove(0) 메서드 호출 후 반환된 값을 위에서 선언한 Connection 객체의 참조변수 conn에 저장
		//이 if문 내(중첩)에서 conndml 메서드 isValid(10)을 호출하고 호출결과가 참이면 conn 반환 
		//외부 if문에서 return DriverManager.getConnection(url, username, password)
	
	public void returnConnection (Connection conn) throws Exception{
		connList.add(conn);
	}
	//returnConnection 메서드 선언, 파라미터는 Connection 타입의 conn
		//함수의 오른쪽에 throws Exception 추가
		//기능은 멤버변수 connList의 메서드 add호출, 호출시 파라미터 conn 추가
	
	public void closeAll() {
		for(Connection conn : connList) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}


