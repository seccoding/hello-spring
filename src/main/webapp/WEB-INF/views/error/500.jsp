<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>에러가 발생했습니다.</title>
  </head>
  <body>
    <c:choose>
      <c:when test="${not empty email}">
        <h3>${email}은 ${message}</h3>
      </c:when>
      <c:otherwise>
        <h3>${message}</h3>
      </c:otherwise>
    </c:choose>
    <div>관리자에게 문의하세요!</div>
  </body>
</html>
