<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 수정</title>
    <style type="text/css">
      /*div인데 클래스가 grid인것*/
      div.grid {
        display: grid;
        grid-template-columns: 80px 1fr;
        grid-template-rows: 28px 28px 28px 320px 1fr;
        row-gap: 10px;
      }

      div.grid > div.btn-group {
        grid-column: 1 / 3;
      }

      div.grid div.right-align {
        text-align: right;
      }

      label {
        padding-left: 10px;
      }

      button,
      input,
      textarea {
        padding: 10px;
      }

      input[type="file"] {
        padding: 0;
      }
    </style>
  </head>
  <body>
    <h1>게시글 수정</h1>
    <form
      action="/board/modify/${boardVO.id}"
      method="post"
      enctype="multipart/form-data"
    >
      <div class="grid">
        <label for="subject">제목</label>
        <input
          type="text"
          id="subject"
          name="subject"
          value="${boardVO.subject}"
        />

        <label for="subject">이메일</label>
        <input type="email" id="email" name="email" value="${boardVO.email}" />

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
  </body>
</html>
