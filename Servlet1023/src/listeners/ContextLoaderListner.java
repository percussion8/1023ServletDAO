package listeners;

import java.awt.Event;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletException;

import green_dao.MemberDao;
import util.DBconnectionPool;

@WebListener
public class ContextLoaderListner implements ServletContextListener {

		DBconnectionPool connPool;
       public void contextDestroyed(ServletContextEvent sce)  { 
    	  //db커넥션 객체 해제
    	  //웹 애플리케이션 종료될 때 호출되는 메서드
    	    connPool.closeAll(); //자원반환
    	    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	//db커넥션 객체 준비
        //웹 애플리케이션 시작할 때 호출됨.공용 객체를 준비해야 하면 이 메서드 사용
        try {
          ServletContext sc = event.getServletContext();
          //Class.forName("com.mysql.cj.jdbc.Driver");
          connPool = new DBconnectionPool("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/studydb?serverTimezone=Asia/Seoul","root","1234" );
          System.out.println("DB 접속 성공 "+connPool);
         // sc.setAttribute("conn", connPool);
          MemberDao memberDao = new MemberDao();
          memberDao.setConnection(connPool);
          sc.setAttribute("memberDao", memberDao);
       } catch (Throwable e) {
          e.printStackTrace();
       }
     }
    
     //"jdbc:mysql://localhost:3306/studydb?serverTimezone=Asia/Seoul","root","1234"
    
	
}
