package green.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import green.dao.MemberDao;
import green.util.DBConnectionPool;

@WebListener //xml에 등록하지 않고 자동으로 실행이 되는 리스너
public class ContextLoaderListener implements ServletContextListener{

	DBConnectionPool connPool;
	public void contextDestroyed(ServletContextEvent sce) {

		//db커넥션 객체 해제
		//웹 어플리케이션 종료될때 호출되는 메서드
		connPool.closeAll();
	}
	
	public void contextInitialized(ServletContextEvent event) {
		//db커넥션 객체 준비
		//웹 어플리케이션 시작할때 호출됨. 공용객체를 준비해야 하면 이 메서드 사용
		try {
			ServletContext sc = event.getServletContext();
			connPool = new DBConnectionPool("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost:3306/studydb", "root", "2579");
			
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(connPool);
			sc.setAttribute("memberDao", memberDao);
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
