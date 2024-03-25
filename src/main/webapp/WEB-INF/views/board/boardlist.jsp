<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 목록</title>
    <style type="text/css">
      a:link,
      a:hover,
      a:active,
      a:visited {
        color: #333;
        text-decoration: none;
      }

      table.table {
        border-collapse: collapse;
        border: 1px solid #ddd;
        width: 100%;
        table-layout: fixed;
      }

      table.table > thead > tr {
        background-color: #fff;
      }

      table.table > thead th {
        padding: 10px;
        color: #333;
      }

      table.table th,
      table.table td {
        border-right: 1px solid #f0f0f0;
      }

      table.table th:last-child,
      table.table td:last-child {
        border-right: none;
      }

      table.table > tbody tr:nth-child(odd) {
        background-color: #f5f5f5;
      }

      table.table > tbody tr:hover {
        background-color: #fafafa;
      }

      table.table > tbody td {
        padding: 10px;
        color: #333;
      }

      table.table > tbody td[colspan] {
        text-align: center;
      }

      div.grid {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 28px 1fr 28px;
        row-gap: 10px;
      }

      div.grid div.right-align {
        text-align: right;
      }

      .center-align {
        text-align: center;
      }

      .left-align {
        text-align: left;
      }

      .ellipsis {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        display: inline-block;
        width: 100%;
      }
    </style>
  </head>
  <body>
    <div class="grid">
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
                  <td class="center-align">${board.email}</td>
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
      <div class="right-align"><a href="/board/write">게시글 등록</a></div>
    </div>
  </body>
</html>
