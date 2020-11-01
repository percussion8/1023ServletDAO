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
	
	public Connection getConnection() throws Exception {  //���� �� ����
		if(connList.size() > 0) {
			Connection conn = connList.remove(0);
			if(conn.isValid(10)) {
				return conn;
			}
		}
		return DriverManager.getConnection(url, username, password);
	}
	//getConnection �޼���, �Ķ���ʹ� ���� ��ȯŸ���� Connection Ÿ�� 
		//�Լ����ǹ� ���� throws Exception �߰� 
		//if ������ ������� connList�� ũ�⸦ �˾Ƴ��� size �޼��� ȣ���ϰ� �� ũ�Ⱑ 0���� ũ�� 
		//connList�� �̿��Ͽ� Connection ��ü 0��° Connection��ü ���� (remove(0) ȣ��)
		//remove(0) �޼��� ȣ�� �� ��ȯ�� ���� ������ ������ Connection ��ü�� �������� conn�� ����
		//�� if�� ��(��ø)���� conndml �޼��� isValid(10)�� ȣ���ϰ� ȣ������ ���̸� conn ��ȯ 
		//�ܺ� if������ return DriverManager.getConnection(url, username, password)
	
	public void returnConnection (Connection conn) throws Exception{
		connList.add(conn);
	}
	//returnConnection �޼��� ����, �Ķ���ʹ� Connection Ÿ���� conn
		//�Լ��� �����ʿ� throws Exception �߰�
		//����� ������� connList�� �޼��� addȣ��, ȣ��� �Ķ���� conn �߰�
	
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


