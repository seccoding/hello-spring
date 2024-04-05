<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>페이지를 찾을 수 없습니다.</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
  </head>
  <body>
    <h3>${message}</h3>
    <a href="/board/search">게시글 목록으로 가기</a>
  </body>
</html>
