package green_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import green_dao.MemberDao;
import green_vo.Member;

@SuppressWarnings("serial")
//@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 지역변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection)sc.getAttribute("conn");

			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			
			Member member = memberDao.selectOne(
					Integer.parseInt(request.getParameter("no"))
					);
			request.setAttribute("member", member);
			RequestDispatcher rd = request.getRequestDispatcher(
						"/NewMember/MemberUpdateForm.jsp"
					);
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
			//finally {
//			try {
//				if (rs != null)
//					rs.close();
//			} catch (Exception e) {
//			}
//			try {
//				if (stmt != null)
//					stmt.close();
//			} catch (Exception e) {
//			}
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (Exception e) {
//			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			Connection conn = (Connection)sc.getAttribute("conn");
			//MemberDao객체 생성, 참조변수 memberDao
			//setConnection메서드 호출 (파라미터는 상단의 conn)
			//update메서드 호출, 파라미터 member객체 생성과 setter체인
			//setter체인의 파라미터는 integer.ParseInt(request.getpara("no"), setname, setemail
			//response.sendredirect
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			memberDao.update(new Member()
					.setNo(Integer.parseInt(request.getParameter("no")))
					.setName(request.getParameter("name"))
					.setEmail(request.getParameter("email"))
					);
			response.setContentType("text/html;charset=utf-8");
			response.sendRedirect("list");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rdd = request.getRequestDispatcher(
						"/Error.jsp"
					);
			rdd.forward(request, response);
		}
		
		
		
//		request.setCharacterEncoding("utf-8");
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");// 드라이버 로딩
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/studydb?", // JDBC URL
//					"root", // DBMS 사용자 아이디
//					"1234"); // DBMS 사용자 암호
//			System.out.println("업데이트DB 접속 성공 " + conn);
//			stmt = conn.prepareStatement("update members set email=?, mname=?, mod_date=now() where mno=?");
//			stmt.setString(1, request.getParameter("email"));
//			stmt.setString(2, request.getParameter("name"));
//			stmt.setString(3, request.getParameter("no"));
//			stmt.executeUpdate();
//			response.sendRedirect("list");
//
//		} catch (Exception e) {
//			System.out.println("업데이트 실패");
//		} finally {
//			try {
//				if (stmt != null)
//					stmt.close();
//			} catch (Exception e) {
//			}
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (Exception e) {
//			}
//		}
	}

}
