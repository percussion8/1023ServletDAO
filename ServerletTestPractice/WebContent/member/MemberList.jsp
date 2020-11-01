<%@ page import = "green.vo.Member" %>
<%@ page import = "java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
</head>
<body>
<jsp:include page ="/Header.jsp" />
<h1>회원목록</h1>
<p><a href="add">신규회원</a></p>
<c:forEach var="member" items="${members }">
	${member.no },
	<a href="update?no=${member.no }">${member.name }</a>,
	${member.email },
	${member.createdDate }
	<a href="delete?no=${member.no }">[삭제]</a><br>
</c:forEach>
<jsp:include page="/Tail.jsp" />

</body>
</html>