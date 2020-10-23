package green_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.SendResult;

import green_dao.MemberDao;
import green_vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(
				"/NewMember/MemberForm.jsp"
				);
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			Connection connection = (Connection)sc.getAttribute("conn");
			//MemberDao객체 생성, 참조변수 memberDao
			//setConnection메서드 호출 (파라미터는 상단의 conn)
			//memberDao의 insert메서드 호출
			//호출시 new Member객체 생성하여 setter체인으로 값 전달
			//response, sendRedirect("list")
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(connection);
			System.out.println("커넥션연결"); //됨
			memberDao.insert(new Member()
					.setName(request.getParameter("name"))
					.setPassword(request.getParameter("password"))
					.setEmail(request.getParameter("email"))
					);
			System.out.println("인서트메서드는 오케이");
			
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
		
//		try {
//			ServletContext sc = this.getServletContext();
//			conn = (Connection)sc.getAttribute("conn");
//			stmt =conn.prepareStatement("insert into members (email, pwd, mname, cre_date, mod_date) values(?,?,?, now(), now())");
//			stmt.setString(1, request.getParameter("email"));
//			stmt.setString(2, request.getParameter("password"));
//			stmt.setString(3, request.getParameter("name"));
//			stmt.executeUpdate();
//			//리다이렉트를 이용한 리프레시
//			response.sendRedirect("list"); //목록 보기로 이동함 
//	
//	} catch(Exception e) {
//		throw new ServletException();
//	}/*finally {
//		try {
//			if (stmt!=null) stmt.close();
//		}catch(Exception e) {}*/
//		try {
//			if (conn!=null) conn.close();
//		}catch(Exception e) {}
//	}
	}
}
