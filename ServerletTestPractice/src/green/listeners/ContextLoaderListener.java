package green.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import green.dao.MemberDao;
import green.util.DBConnectionPool;

@WebListener //xml�� ������� �ʰ� �ڵ����� ������ �Ǵ� ������
public class ContextLoaderListener implements ServletContextListener{

	DBConnectionPool connPool;
	public void contextDestroyed(ServletContextEvent sce) {

		//dbĿ�ؼ� ��ü ����
		//�� ���ø����̼� ����ɶ� ȣ��Ǵ� �޼���
		connPool.closeAll();
	}
	
	public void contextInitialized(ServletContextEvent event) {
		//dbĿ�ؼ� ��ü �غ�
		//�� ���ø����̼� �����Ҷ� ȣ���. ���밴ü�� �غ��ؾ� �ϸ� �� �޼��� ���
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
