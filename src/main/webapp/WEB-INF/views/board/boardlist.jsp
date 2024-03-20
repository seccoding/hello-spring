<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 목록</title>
  </head>
  <body>
    게시글 수: ${boardList.boardCnt} <br />
    조회한 게시글의 수: ${boardList.boardList.size()}
  </body>
</html>
