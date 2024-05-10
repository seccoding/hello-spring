<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 수정</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <style type="text/css">
      /*div인데 클래스가 grid인것*/
      div.grid {
        display: grid;
        grid-template-columns: 80px 1fr;
        grid-template-rows: 28px 28px 320px 1fr;
        row-gap: 10px;
      }
    </style>
    <script type="text/javascript" src="/js/boardmodify.js"></script>
  </head>
  <body>
    <c:if test="${not empty errorMessage}">
      <dialog class="alert-dialog">
        <h1>${errorMessage}</h1>
      </dialog>
    </c:if>

    <jsp:include page="../layout/layout.jsp" />

    <h1>게시글 수정</h1>
    <form
      action="/board/modify/${boardVO.id}"
      method="post"
      enctype="multipart/form-data"
    >
      <sec:csrfInput />
      <div class="grid">
        <label for="subject">제목</label>
        <input
          type="text"
          id="subject"
          name="subject"
          value="${boardVO.subject}"
        />

        <label for="file">첨부파일</label>
        <!-- input type=file의 value는 지정할 수 없다. -->
        <div>
          <input type="file" name="file" id="file" />
          현재 업로드된 파일: ${boardVO.originFileName}
        </div>

        <label for="content">내용</label>
        <textarea id="content" name="content" style="height: 300px">
${boardVO.content}</textarea
        >

        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </form>
    <jsp:include page="../layout/layout_close.jsp" />
  </body>
</html>
