<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello Boot!</title>
</head>
<body>
	<div>안녕하세요.</div>
	<div>Boot JSP 입니다.</div>
	<div>이 애플리케이션의 이름은 ${myname}입니다.</div>
	<ul>
		<li>생성날짜: ${createDate}</li>
		<li>생성자: ${author}</li>
		<li>버전: ${version}</li>
	</ul>
</body>
</html>