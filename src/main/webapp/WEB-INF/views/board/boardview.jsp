<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 내용</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <style type="text/css">
      div.grid {
        display: grid;
        grid-template-columns: 80px 1fr;
        grid-template-rows: repeat(6, 28px) auto auto 1fr;
        row-gap: 10px;
      }
    </style>
    <script type="text/javascript" src="/js/boardview.js"></script>
  </head>
  <body>
    <jsp:include page="../layout/layout.jsp" />
    <h1>게시글 조회</h1>
    <div class="grid" data-id="${boardVO.id}">
      <label for="subject">제목</label>
      <div>${boardVO.subject}</div>

      <label for="name">작성자 이름</label>
      <div>${boardVO.memberVO.name}</div>

      <label for="viewCnt">조회수</label>
      <div>${boardVO.viewCnt}</div>

      <label for="originFileName">첨부파일</label>
      <div>
        <a href="/board/file/download/${boardVO.id}">
          ${boardVO.originFileName}
        </a>
      </div>

      <label for="crtDt">등록일</label>
      <div>${boardVO.crtDt}</div>

      <label for="mdfyDt">수정일</label>
      <div>${boardVO.mdfyDt}</div>

      <label for="content">내용</label>
      <div>${boardVO.content}</div>

      <div class="replies">
        <div>
          <button type="button" id="get-all-replies-btn">
            모든 댓글 불러오기
          </button>
        </div>
        <div class="reply-items"></div>
        <div class="write-reply">
          <textarea id="txt-reply"></textarea>
          <button id="btn-save-reply">등록</button>
          <button id="btn-cancel-reply">취소</button>
        </div>
      </div>

      <c:if
        test="${sessionScope._LOGIN_USER_.email eq boardVO.email || sessionScope._LOGIN_USER_.adminYn eq 'Y'}"
      >
        <div class="btn-group">
          <div class="right-align">
            <a href="/board/modify/${boardVO.id}">수정</a>
            <!--
            javascript:void(0);
            주로 anchor 태그의 href에 작성하는 코드.
            링크를 클릭했을 때, javascript를 이용해서 처리할 경우 위 처럼 작성을 한다.
            javascript:void(0); 이 코드는 anchor 태그의 링크 이동을 무시한다.
          -->
            <a class="delete-board" href="javascript:void(0);">삭제</a>
          </div>
        </div>
      </c:if>
    </div>
    <jsp:include page="../layout/layout_close.jsp" />
  </body>
</html>
