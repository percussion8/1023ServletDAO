//맴버관리하는 Dao클래스. 컨트롤러(서블릿)에서는 전달만 하고, db관련된거는 손을 놓기로 함

package green.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import green.util.DBConnectionPool;
import green.vo.Member;

@SuppressWarnings("unused")
public class MemberDao {
	//MemberList에서 가져온 내용
	DBConnectionPool connPool;
	
	//의존성 주입(Dependency Injection)내각 객체를 사용하지 않고
	//사용하는곳에서 객체를 생성하여 setter로 주입하여 사용함
	public void setConnection(DBConnectionPool connPool) { //호출하는 메서드 /세터
		this.connPool = connPool;
	}
	
	public List<Member> selectlist() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select mno, mname, email, cre_date from members order by mno asc");
			
			ArrayList<Member> members = new ArrayList<Member>();
			//데이터베이스에서 회원 정보를 가져와 Member에 담는다.
			//그리고 Member객체를 ArrayList에 추가한다
			
			while(rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("mno"))
						.setName(rs.getString("mname"))
						.setEmail(rs.getString("email"))
						.setCreatedDate(rs.getDate("cre_date"))	);
			}
			return members;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
		    try {if (stmt != null) stmt.close();} catch(Exception e) {}
		    //try {if (connection != null) connection.close();} catch(Exception e) {}
		}
		
	}
	
	public int insert (Member member) throws Exception {
		PreparedStatement stmt = null;
		Connection connection = null;
		try {
			connection =  connPool.getConnection();
			stmt = connection.prepareStatement("insert into members(email, pwd, mname, cre_date, mod_date) values (?, ?, ?, now(), now())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			return stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
	        //  try {if (connection != null) connection.close();} catch(Exception e) {}

		}
	}
	
	public int update(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("update members set email=?,mname=?,mod_date=now()"
					+ " where mno =? ");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			
			return stmt.executeUpdate();
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServletException();
		} finally {
			 try {if (stmt != null) stmt.close();} catch(Exception e) {}
		     // try {if (connection != null) connection.close();} catch(Exception e) {}
			 
		}
	}
	
	public int delete (int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate("delete from members where mno =" + no);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			 try {if (stmt != null) stmt.close();} catch(Exception e) {}
		      // try {if (connection != null) connection.close();} catch(Exception e) {}
			  
		}
	}
	
	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select mno, email, mname, cre_date from members where mno =" + no);
			
			if(rs.next()) {
				return new Member()
						.setNo(rs.getInt("mno"))
						.setEmail(rs.getString("email"))
						.setName(rs.getString("mname"))
						.setCreatedDate(rs.getDate("cre_date"));
			} else {
				throw new Exception("해당 번호를 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			// try {if (connection != null) connection.close();} catch(Exception e) {}
			try {if(stmt!=null) stmt.close();} catch(Exception e) {}
			try {if(rs!=null) rs.close();} catch(Exception e) {}

		}
	}
	
	public Member exist(String email, String password) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("select mname, email from members where email=? and pwd=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return new Member()
						.setName(rs.getString("mname"))
						.setEmail(rs.getString("email"));
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			try {if(rs!=null) rs.close();} catch(Exception e) {}
			try {if(stmt!=null) stmt.close();} catch(Exception e) {}
			//다 사용하면 Connection객체 반환, DBConnectionPool에 추가됨
			if(connection != null) connPool.returnConnection(connection);

		}
	}
	
	

}
