package green_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


import green_vo.Member;
import util.DBconnectionPool;

public class MemberDao {
	DBconnectionPool connPool;
	
	public void setConnection(DBconnectionPool connPool) {
		this.connPool = connPool;
	}
	
	public List<Member> selectList() throws Exception{
		Connection connection  =null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select mno, mname, email, cre_date" + " from members" + " order by mno asc");
			ArrayList<Member> members = new ArrayList<Member>();
			//response.setContentType("text/html; charset=utf-8");
			//데이터베이스에서 회원정보를 가져와 Member에 담는다.
			//그리고 맴버객체에 Arrylist를 추가한다
			
			while (rs.next()) { // DB로부터 가져온 데이터 ResultSet에서 하나씩 가져옴
				members.add(new Member()
						.setNo(rs.getInt("mno"))
						.setName(rs.getString("mname"))
						.setEmail(rs.getString("email"))
						.setCreatedDate(rs.getDate("cre_date"))
						);
			}
				return members;
				
				/*request.setAttribute("members", members);
				RequestDispatcher rd = request.getRequestDispatcher(
						"/NewMember/MemberList.jsp"
						);
				rd.include(request, response);
				*/
		} catch (Exception e) {
			throw new ServletException(); // 예외를 던짐
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (Exception e) {
//			}
		}
	}
	
	public int insert(Member member) throws Exception{
		PreparedStatement stmt =null;
		Connection connection  =null;
		System.out.println(member.getName());
		System.out.println(member.getEmail());
		System.out.println(member.getPassword());
		
		
		try {
			connection = connPool.getConnection();
			stmt =connection.prepareStatement("insert into members (email, pwd, mname, cre_date, mod_date) values(?,?,?, now(), now())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			System.out.println("인서트메서드완료");
			return stmt.executeUpdate();
	
	} catch(Exception e) {
		throw new ServletException();
	} finally {
		try {
			if (stmt!=null) stmt.close();
		}catch(Exception e) {}
	}
		
		
	}
	
	public int update(Member member) throws Exception{
		PreparedStatement stmt = null;
		 Connection connection  =null;
		try {
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("update members set email=?, mname=?, mod_date=now() where mno=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();

		} catch (Exception e) {
			throw new ServletException("업데이트실패");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {}
		}
		
		
	}
	public Member selectOne(int no) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		 Connection connection  =null;
		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select mno,email,mname, cre_date from members "
					+ "where mno =" + no);
			if(rs.next()) {
				return new Member()
						.setNo(rs.getInt("mno"))
						.setEmail(rs.getString("email"))
						.setName(rs.getString("mname"))
						.setCreatedDate(rs.getDate("cre_date"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}
//		e.printStackTrace();
//		request.setAttribute("error", e);
//		RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//		rd.forward(request, response);
//		
//		stmt = conn.createStatement();
//		rs = stmt.executeQuery(
//				"select mno,email,mname, cre_date from members " + " where mno =" + request.getParameter("no"));
//		if (rs.next()) { // DB로부터 가져온 데이터 ResultSet에서 하나씩 가져옴
//			request.setAttribute("member", new Member()
//					.setNo(rs.getInt("mno"))
//					.setName(rs.getString("mname"))
//					.setEmail(rs.getString("email"))
//					.setCreatedDate(rs.getDate("cre_date"))
//			);
//		} else {
//			throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
//		}
//		RequestDispatcher rd = request.getRequestDispatcher(
//				"/NewMember/MemberUpdateForm.jsp"
//				);
//		rd.forward(request, response);
//	}
	
//	public int delete (int no) throws Exception{
//		Statement stmt = null;
//		try {
//			stmt = connection.
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	public int delete (int no) throws Exception {
		   Connection connection  =null;
		   Statement stmt = null;
		   try {
			   connection = connPool.getConnection();
			   stmt =connection.createStatement();
			   return stmt.executeUpdate("delete from members where mno =" + no);
			   
		   }catch(Exception e) {
			   throw e;		   
		   }finally {
			   
			   try {if (stmt != null) stmt.close();} catch(Exception e) {}
		      // try {if (connection != null) connection.close();} catch(Exception e) {}
			   
		   }
	}
	
		   public Member exist(String email, String password) throws Exception {
				Connection connection  =null;
				PreparedStatement stmt =null;
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
					}else {
						return null;
					}
					
				}catch(Exception e) {
					throw e;
				}finally {
					try {if(rs!=null) rs.close();} catch(Exception e) {}
					try {if(stmt!=null) stmt.close();} catch(Exception e) {}
					//다 사용하면 Connection 객체 반환, DBConnectionPool에 추가됨
					if(connection != null) connPool.returnConnection(connection);
				}
			}
	
}
