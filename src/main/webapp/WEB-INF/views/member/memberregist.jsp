<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>회원가입</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <style type="text/css">
      div.grid {
        display: grid;
        grid-template-columns: 120px 1fr;
        grid-template-rows: repeat(4, 28px) 1fr;
        row-gap: 10px;
      }
      .available {
        background-color: #0f03;
      }
      .unusable {
        background-color: #f003;
      }
    </style>
    <script type="text/javascript" src="/js/memberregist.js"></script>
  </head>
  <body>
    <c:if test="${not empty errorMessage}">
      <dialog class="alert-dialog">
        <h1>${errorMessage}</h1>
      </dialog>
    </c:if>
    <h1>회원가입</h1>
    <form action="/member/regist" method="post">
      <sec:csrfInput />
      <div class="grid">
        <label for="email">이메일</label>
        <input type="email" name="email" id="email" value="${memberVO.email}" />

        <label for="name">이름</label>
        <input type="text" name="name" id="name" value="${memberVO.name}" />

        <label for="password">비밀번호</label>
        <input
          type="password"
          name="password"
          id="password"
          value="${memberVO.password}"
        />

        <label for="confirmPassword">비밀번호 확인</label>
        <input
          type="password"
          name="confirmPassword"
          id="confirmPassword"
          value="${memberVO.confirmPassword}"
        />

        <div class="btn-group">
          <div class="right-align">
            <input id="btn-regist" type="submit" value="등록" />
          </div>
        </div>
      </div>
    </form>
  </body>
</html>
