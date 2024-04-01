<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 목록</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <style type="text/css">
      div.grid {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 28px 28px 1fr 28px;
        row-gap: 10px;
      }
    </style>
  </head>
  <body>
    <div class="grid">
      <jsp:include page="../member/membermenu.jsp"></jsp:include>
      <div class="right-align">
        총 ${boardList.boardCnt}건의 게시글이 검색되었습니다.
      </div>

      <table class="table">
        <colgroup>
          <col width="80px" />
          <col width="*" />
          <col width="150px" />
          <col width="80px" />
          <col width="150px" />
          <col width="150px" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>이메일</th>
            <th>조회수</th>
            <th>등록일</th>
            <th>수정일</th>
          </tr>
        </thead>
        <tbody>
          <!--
            boardList의 내용이 존재한다면(1개 이상 있다면)
              내용을 반복하면서 보여주고
            boardList의 내용이 존재하지 않는다면
              "등록된 게시글이 없습니다. 첫 번째 글의 주인공이 되어보세요!" 를 보여준다.
            jstl > choose when otherwise ==> Java if ~ else if ~ else
          -->
          <!-- 조건식의 시작을 알림. -->
          <c:choose>
            <%-- boardList의 내용이 존재한다면(1개 이상 있다면) --%>
            <c:when test="${not empty boardList.boardList}">
              <%-- 내용을 반복하면서 보여주고 --%>
              <c:forEach items="${boardList.boardList}" var="board">
                <tr>
                  <td class="center-align">${board.id}</td>
                  <td class="left-align">
                    <a class="ellipsis" href="/board/view?id=${board.id}"
                      >${board.subject}</a
                    >

                    <c:if test="${not empty board.originFileName}">
                      <a href="/board/file/download/${board.id}"
                        >첨부파일 다운로드</a
                      >
                    </c:if>
                  </td>
                  <td class="center-align">${board.memberVO.name}</td>
                  <td class="center-align">${board.viewCnt}</td>
                  <td class="center-align">${board.crtDt}</td>
                  <td class="center-align">${board.mdfyDt}</td>
                </tr>
              </c:forEach>
            </c:when>
            <%-- boardList의 내용이 존재하지 않는다면 --%>
            <c:otherwise>
              <tr>
                <td colspan="6">
                  <a href="/board/write">
                    등록된 게시글이 없습니다. 첫 번째 글의 주인공이 되어보세요!
                  </a>
                </td>
              </tr>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
      <c:if test="${not empty sessionScope._LOGIN_USER_}">
        <div class="right-align">
          <a href="/board/excel/download">엑셀 다운로드</a>
          <a href="/board/write">게시글 등록</a>
        </div>
      </c:if>
    </div>
  </body>
</html>
