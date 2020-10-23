package green_servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import green_vo.Member;


@SuppressWarnings("serial")
@WebServlet("/r")
public class CalcPlus extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = new Member();
		member.setEmail("ms960315@gmail.com");
		member.setName("Monica");
		member.setNo(10);
		request.setAttribute("a", "18");
		request.setAttribute("member", member);
		RequestDispatcher rd = request.getRequestDispatcher(
					"/jstl/JSTL08.jsp"
				);
		rd.forward(request, response);
			
			
	}
}
