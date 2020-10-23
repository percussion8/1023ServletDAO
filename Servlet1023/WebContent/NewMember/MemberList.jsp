<%@ page import = "green_vo.Member" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
</head>
<body>
<jsp:include page="/Header.jsp" />
	<h1>회원 목록</h1>
	<h2><a href="add">신규 회원</a><br></h2>
	<c:forEach var="member" items="${members }">
	${member.no },
	<a href="update?no=${member.no }">${member.name }</a>,
	${member.email },
	${member.createdDate }
	<a href="update?no=${member.no }">[삭제]</a><br>
	</c:forEach>
<jsp:include page="/Tail.jsp" />
</body>
</html>