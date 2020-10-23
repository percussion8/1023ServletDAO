package green_servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.SetContextPropertiesRule;

import green_dao.MemberDao;
import green_vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			Connection conn = (Connection)sc.getAttribute("conn");
			
			//MemberDao객체 생성, 참조변수 memberDao
			//setConnection메서드 호출 (파라미터는 상단의 conn)
			//request.setAttribute메서드 호출
			//첫번째 매개변수 "members", 두번째 매개변수 "memberDao의 메서드 selectList호출한 결과
			//response.setContentTye("text/html; charet=utf-8")
			//requestDispa를 이용하여 member폴도하위의 memberlist.jsp연결 include형태로 전달
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			request.setAttribute("members", memberDao.selectList());
			response.setContentType("text/html;charset=utf-8");
			RequestDispatcher rd = request.getRequestDispatcher(
						"/NewMember/MemberList.jsp"
					);
			rd.include(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rdd = request.getRequestDispatcher(
						"/Error.jsp"
					);
			rdd.forward(request, response);
		}

		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
