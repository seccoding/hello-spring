<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
  <head>
    <sec:csrfMetaTags />
    <meta charset="UTF-8" />
    <title>게시글 목록</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <style type="text/css">
      div.grid {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 28px 1fr auto 28px;
        row-gap: 10px;
      }
    </style>
    <script type="text/javascript" src="/js/boardlist.js"></script>
  </head>
  <body>
    <jsp:include page="../layout/layout.jsp" />
    <div class="grid">
      <div class="right-align">
        총 ${boardList.boardCnt}건의 게시글이 검색되었습니다.
      </div>

      <table class="table">
        <colgroup>
          <sec:authorize access="hasRole('ADMIN')">
            <col width="40px" />
          </sec:authorize>
          <col width="80px" />
          <col width="*" />
          <col width="150px" />
          <col width="80px" />
          <col width="150px" />
          <col width="150px" />
        </colgroup>
        <thead>
          <tr>
            <sec:authorize access="hasRole('ADMIN')">
              <th>
                <input type="checkbox" 
                      id="checked-all" 
                      data-target-class="target-board-id" />
              </th>
            </sec:authorize>
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
                  <sec:authorize access="hasRole('ADMIN')">
                    <td>
                      <input type="checkbox" 
                            class="target-board-id" 
                            name="targetBoardId" 
                            value="${board.id}"/>
                    </td>
                  </sec:authorize>
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

      <!-- Paginator 시작 -->
      <div>
        <form id="search-form">
          <input type="hidden" id="page-no" name="pageNo" value="0" />
          <select id="list-size" name="listSize">
            <option value="10" ${searchBoardVO.listSize eq 10 ? 'selected' : ''}>10개</option>
            <option value="20" ${searchBoardVO.listSize eq 20 ? 'selected' : ''}>20개</option>
            <option value="30" ${searchBoardVO.listSize eq 30 ? 'selected' : ''}>30개</option>
            <option value="50" ${searchBoardVO.listSize eq 50 ? 'selected' : ''}>50개</option>
            <option value="100" ${searchBoardVO.listSize eq 100 ? 'selected' : ''}>100개</option>
          </select>

          <select id="search-type" name="searchType">
            <option value="title" ${searchBoardVO.searchType eq 'title' ? 'selected' : ''}>제목</option>
            <option value="content" ${searchBoardVO.searchType eq 'content' ? 'selected' : ''}>내용</option>
            <option value="title_content" ${searchBoardVO.searchType eq 'title_content' ? 'selected' : ''}>제목 + 내용</option>
            <option value="email" ${searchBoardVO.searchType eq 'email' ? 'selected' : ''}>작성자</option>
          </select>

          <input type="text" name="searchKeyword" value="${searchBoardVO.searchKeyword}" />
          <button type="button" id="search-btn">검색</button>
          <button type="button" id="cancel-search-btn">초기화</button>

          <ul class="page-nav">
            <c:if test="${searchBoardVO.hasPrevGroup}">
              <li><a href="javascript:search(0);">처음</a></li>
              <li>
                <a
                  href="javascript:search(${searchBoardVO.prevGroupStartPageNo});"
                  >이전</a
                >
              </li>
            </c:if>

            <!-- Page 번호를 반복하며 노출한다. -->
            <c:forEach
              begin="${searchBoardVO.groupStartPageNo}"
              end="${searchBoardVO.groupEndPageNo}"
              step="1"
              var="p"
            >
              <li class="${searchBoardVO.pageNo eq p ? 'active' : ''}">
                <a href="javascript:search(${p});">${p+1}</a>
              </li>
            </c:forEach>

            <c:if test="${searchBoardVO.hasNextGroup}">
              <li>
                <a
                  href="javascript:search(${searchBoardVO.nextGroupStartPageNo});"
                  >다음</a
                >
              </li>
              <li>
                <a href="javascript:search(${searchBoardVO.pageCount - 1});"
                  >마지막</a
                >
              </li>
            </c:if>
          </ul>
        </form>
      </div>
      <!-- Paginator 끝 -->

      <!-- Spring Security Context에 인증정보(Authenticate 객체)가 있는지 확인 -->
      <!-- Spring Security로 로그인을 했다면. -->
      <sec:authorize access="isAuthenticated()">
        <div class="right-align">
          <a href="/board/write">게시글 등록</a>
            <!-- ROLE_ADMIN 권한을 가진 사용자 일 때만 아래 메뉴를 노출.-->
            <sec:authorize access="hasRole('ADMIN')">
              <a href="/board/excel/download2">엑셀 다운로드</a>
              <a id="deleteMassiveBoard" href="javascript:void(0);">일괄 삭제</a>
              <a id="uploadExcelfile" href="javascript:void(0);"
                >게시글 일괄 등록</a>
              <input type="file" id="excelfile" style="display: none" />
            </sec:authorize>
        </div>
      </sec:authorize>

    </div>
    <jsp:include page="../layout/layout_close.jsp" />
  </body>
</html>
