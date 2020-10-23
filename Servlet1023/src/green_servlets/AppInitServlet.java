package green_servlets;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
@WebServlet("/AppInitServlet")
public class AppInitServlet extends HttpServlet {
	
	public void init(ServletConfig config) throws ServletException {
		System.out.println("AppInitServlet ready...");
		super.init(config);
		try {
			ServletContext sc = this.getServletContext();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studydb?serverTimezone=Asia/Seoul", "root", "1234");
			System.out.println("DB 접속 성공 " + conn);
			sc.setAttribute("conn", conn);
		} catch (Throwable e) {
			throw new ServletException();
		}
	}


	public void destroy() {
		System.out.println("AppInitServlet ending...");
		super.destroy();
		Connection conn = (Connection) this.getServletContext().getAttribute("conn");
		try {
			if(conn != null && conn.isClosed()==false) {
				conn.close();
			}
		} catch (Exception e) {}
	}

}
