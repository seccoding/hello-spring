$().ready(function () {
  var pageNumber = 0;
  $(document).on("scroll", function () {
    var scrollHeight = $(window).scrollTop();
    var documentHeight = $(document).height();
    var browserHeight = $(window).height();
    var scrollBottomPoint = scrollHeight + browserHeight + 30;

    var willFetchReply = scrollBottomPoint > documentHeight;
    if (willFetchReply) {
      loadReplies(boardId, pageNumber);
      console.log("댓글을 10개만 더 불러옵니다.");
    }
  });

  $(".delete-board").on("click", function () {
    var chooseValue = confirm(
      "이 게시글을 정말 삭제하시겠습니까?\n삭제작업은 복구할 수 없습니다."
    );

    var id = $(this).closest(".grid").data("id");
    if (chooseValue) {
      location.href = "/board/delete/" + id;
    }
  });

  var modifyReply = function (event) {
    var target = event.currentTarget;
    var reply = $(target).closest(".reply");
    var replyId = reply.data("reply-id");

    var content = reply.find(".content").text();
    $("#txt-reply").val(content);
    $("#txt-reply").focus();

    $("#txt-reply").data("mode", "modify");
    $("#txt-reply").data("target", replyId);
  };
  var deleteReply = function (event) {
    var target = event.currentTarget;
    var reply = $(target).closest(".reply");
    var replyId = reply.data("reply-id");

    $("#txt-reply").removeData("mode");
    $("#txt-reply").removeData("target");

    if (confirm("댓글을 삭제하시겠습니까?")) {
      $.get("/ajax/board/reply/delete/" + replyId, function (response) {
        var result = response.data.result;

        if (result) {
          loadReplies(boardId);
          $("#txt-reply").val("");
        }
      });
    }
  };
  var reReply = function (event) {
    var target = event.currentTarget;
    var reply = $(target).closest(".reply");
    var replyId = reply.data("reply-id");

    $("#txt-reply").data("mode", "re-reply");
    $("#txt-reply").data("target", replyId);
    $("#txt-reply").focus();
  };
  var recommendReply = function (event) {
    var target = event.currentTarget;
    var reply = $(target).closest(".reply");
    var replyId = reply.data("reply-id");

    $("#txt-reply").removeData("mode");
    $("#txt-reply").removeData("target");

    $.get("/ajax/board/reply/recommend/" + replyId, function (response) {
      var result = response.data.result;
      if (result) {
        loadReplies(boardId);
        $("#txt-reply").val("");
      }
    });
  };

  var loadReplies = function (boardId, pageNo) {
    var isNotUndefinedPageNo = pageNo !== undefined;
    var params = { pageNo: -1 };
    if (isNotUndefinedPageNo) {
      params.pageNo = pageNo;
    }

    $.get("/ajax/board/reply/" + boardId, params, function (response) {
      if (!isNotUndefinedPageNo) {
        //$(".reply-items").html("");
        pageNumber = response.data.paginate.pageCount - 1;
      }

      var count = response.data.count;
      var replies = response.data.replies;

      if (isNotUndefinedPageNo && count == response.data.paginate.listSize) {
        pageNumber++;
      }

      for (var i in replies) {
        var reply = replies[i];

        /***********************이미 불러온 댓글 수정*************************/
        // 이미 불러온 댓글인지 확인
        var appendedReply = $(".reply[data-reply-id=" + reply.replyId + "]");
        var isAppendedReply = appendedReply.length > 0;
        // 이미 불러온 댓글이며, 삭제가 안된 댓글일 경우
        if (isAppendedReply && reply.delYn === "N") {
          appendedReply.find(".content").text(reply.content);
          appendedReply
            .find(".recommend-count")
            .text("추천수: " + reply.recommendCnt);
          var modifyDate = appendedReply.find(".mdfydt");
          if (modifyDate) {
            modifyDate.text("(수정: " + reply.mdfyDt + ")");
          } else {
            var mdfyDtDom = $("<span></span>");
            mdfyDtDom.addClass("mdfydt");
            mdfyDtDom.text("(수정: " + reply.mdfyDt + ")");
            appendedReply.find(".datetime").append(mdfyDtDom);
          }
          continue;
        }
        // 이미 불러온 댓글인데, 삭제가 된 댓글일 경우
        else if (isAppendedReply && reply.delYn === "Y") {
          appendedReply.text("삭제된 댓글입니다.");
          appendedReply.css({
            color: "#F33",
          });
          continue;
        }
        // 이미 불러온 댓글인데, 탈퇴한 회원이 작성한 댓글일 경우
        else if (isAppendedReply && reply.memberVO.delYn === "Y") {
          appendedReply.text("탈퇴한 회원의 댓글입니다.");
          appendedReply.css({
            color: "#F33",
          });
          continue;
        }

        var appendedParentReply = $(
          ".reply[data-reply-id=" + reply.parentReplyId + "]"
        );

        /***********************새로운 댓글 추가*************************/
        // <div class="reply" data-reply-id="댓글번호" style="padding-left: (level - 1) * 40px">
        var replyDom = $("<div></div>");
        replyDom.addClass("reply");
        replyDom.attr("data-reply-id", reply.replyId);
        replyDom.data("reply-id", reply.replyId);
        replyDom.css({
          "padding-left": (reply.level === 1 ? 0 : 1) * 40 + "px",
          color: "#333",
        });

        if (reply.delYn === "Y") {
          replyDom.css({
            color: "#F33",
          });
          replyDom.text("삭제된 댓글입니다.");
        } else if (reply.memberVO.delYn === "Y") {
          replyDom.css({
            color: "#F33",
          });
          replyDom.text("탈퇴한 회원의 댓글입니다.");
        } else {
          // <div class="author">사용자명 (사용자이메일)</div>
          var authorDom = $("<div></div>");
          authorDom.addClass("author");
          authorDom.text(reply.memberVO.name + " (" + reply.email + ")");
          replyDom.append(authorDom);

          // <div class="recommend-count">추천수: 실제추천수</div>
          var recommendCountDom = $("<div></div>");
          recommendCountDom.addClass("recommend-count");
          recommendCountDom.text("추천수: " + reply.recommendCnt);
          replyDom.append(recommendCountDom);

          // <div class="datetime">
          var datetimeDom = $("<div></div>");
          datetimeDom.addClass("datetime");

          // <span class="crtdt">등록: 등록날짜</span>
          var crtDtDom = $("<span></span>");
          crtDtDom.addClass("crtdt");
          crtDtDom.text("등록: " + reply.crtDt);
          datetimeDom.append(crtDtDom);

          if (reply.crtDt !== reply.mdfyDt) {
            // <span class="mdfydt">(수정: 수정날짜)</span>
            var mdfyDtDom = $("<span></span>");
            mdfyDtDom.addClass("mdfydt");
            mdfyDtDom.text("(수정: " + reply.mdfyDt + ")");
            datetimeDom.append(mdfyDtDom);
          }
          replyDom.append(datetimeDom);

          // <pre class="content">댓글 내용</pre>
          var contentDom = $("<pre></pre>");
          contentDom.addClass("content");
          contentDom.text(reply.content);

          replyDom.append(contentDom);

          var loginEmail = $("#login-email").text();
          var roleAdmin = $("#role-admin");
          var isAdmin = roleAdmin && roleAdmin.length > 0;

          // <div>
          var controlDom = $("<div></div>");

          if (reply.email === loginEmail || isAdmin) {
            // <span class="modify-reply">수정</span>
            var modifyReplyDom = $("<span></span>");
            modifyReplyDom.addClass("modify-reply");
            modifyReplyDom.text("수정");
            modifyReplyDom.on("click", modifyReply);

            controlDom.append(modifyReplyDom);

            // <span class="delete-reply">삭제</span>
            var deleteReplyDom = $("<span></span>");
            deleteReplyDom.addClass("delete-reply");
            deleteReplyDom.text("삭제");
            deleteReplyDom.on("click", deleteReply);
            controlDom.append(deleteReplyDom);

            // <span class="re-reply">답변하기</span>
            var reReplyDom = $("<span></span>");
            reReplyDom.addClass("re-reply");
            reReplyDom.text("답변하기");
            reReplyDom.on("click", reReply);
            controlDom.append(reReplyDom);
          } else {
            // <span class="recommend-reply">추천하기</span>
            var recommendReplyDom = $("<span></span>");
            recommendReplyDom.addClass("recommend-reply");
            recommendReplyDom.text("추천하기");
            recommendReplyDom.on("click", recommendReply);
            controlDom.append(recommendReplyDom);

            // <span class="re-reply">답변하기</span>
            var reReplyDom = $("<span></span>");
            reReplyDom.addClass("re-reply");
            reReplyDom.text("답변하기");
            reReplyDom.on("click", reReply);
            controlDom.append(reReplyDom);
          }

          replyDom.append(controlDom);
        }

        // 일반 댓글은 reply-items의 자식으로 추가한다.
        if (!appendedParentReply.length > 0) {
          $(".reply-items").append(replyDom);
        }
        // 대댓글은 원 댓글의 자식으로 추가한다.
        else {
          appendedParentReply.append(replyDom);
        }
        /*
        <div class="reply" data-reply-id="댓글번호" style="padding-left: (level - 1) * 40px">
          <div class="author">사용자명 (사용자이메일)</div>
          <div class="recommend-count">추천수: 실제추천수</div>
          <div class="datetime">
            <span class="crtdt">등록: 등록날짜</span>
            <span class="mdfydt">(수정: 수정날짜)</span>
          </div>
          <pre class="content">댓글 내용</pre>
          <div>
            <span class="modify-reply">수정</span>
            <span class="delete-reply">삭제</span>
            <span class="re-reply">답변하기</span>

            <span class="recommend-reply">추천하기</span>
            <span class="re-reply">답변하기</span>
          </div>
        </div>
        */
      }
    });
  };

  var boardId = $(".grid").data("id");
  loadReplies(boardId, 0);

  $("#get-all-replies-btn").on("click", function () {
    loadReplies(boardId);
  });

  $("#btn-save-reply").on("click", function () {
    var reply = $("#txt-reply").val();
    var mode = $("#txt-reply").data("mode");
    var target = $("#txt-reply").data("target");

    if (reply.trim() !== "") {
      var body = { content: reply.trim() };
      var url = "/ajax/board/reply/" + boardId;

      if (mode === "re-reply") {
        body.parentReplyId = target;
      }

      if (mode === "modify") {
        url = "/ajax/board/reply/modify/" + target;
      }
    }

    $("#txt-reply").removeData("mode");
    $("#txt-reply").removeData("target");

    // CSRF token을 찾아 body 에 넣어준다.
    // body {content, parentReplyId, _csrf}
    var csrfParameterName = $("meta[name=_csrf_parameter]").attr("content");
    var csrfToken = $("meta[name=" + csrfParameterName + "]").attr("content");

    // body["_csrf"] = csrfToken;
    // body._csrf = csrfToken;
    body[csrfParameterName] = csrfToken;

    $.post("http://localhost:9090" + url, body, function (response) {
      var result = response.data.result;
      if (result) {
        loadReplies(boardId);
        $("#txt-reply").val("");
      } else {
        alert("댓글을 등록할 수 없습니다. 잠시 후 시도해주세요.");
      }
    });
  });
  $("#btn-cancel-reply").on("click", function () {
    $("#txt-reply").val("");
    $("#txt-reply").removeData("mode");
    $("#txt-reply").removeData("target");
  });
});
