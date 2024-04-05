$(document).on("ajaxStart", function () {
  var blockBoard = $("<div></div>");
  blockBoard.addClass("process-ajax");
  blockBoard.css({
    display: "flex",
    "justify-content": "center",
    "align-items": "center",
    "background-color": "#0003",
    position: "fixed",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  });

  var img = $("<img />");
  img.attr(
    "src",
    "https://global.discourse-cdn.com/business7/uploads/streamlit/original/2X/2/247a8220ebe0d7e99dbbd31a2c227dde7767fbe1.gif"
  );
  img.css({
    width: "30%",
    height: "30%",
  });
  blockBoard.append(img);

  $("body").prepend(blockBoard);
});

$(document).on("ajaxComplete", function () {
  $(".process-ajax").remove();
});

$().ready(function () {
  $("a.deleteMe").on("click", function () {
    $.get("/ajax/member/delete-me", function (response) {
      var next = response.data.next;
      location.href = next;
    });
  });

  /*
   * 엔터키 눌렀을 때 form 전송안되도록 수정.
   * input의 data-submit 값이 true가 아닌 경우, 엔터키입력시 전송 안되도록 방지.
   */
  $("form")
    .find("input")
    .on("keydown", function (event) {
      if (event.keyCode === 13) {
        var noSubmit = $(this).data("no-submit");
        if (noSubmit !== undefined) {
          event.preventDefault();
        }
      }
    });
});

function search(pageNo) {
  var searchForm = $("#search-form");
  //var listSize = $("#list-size");
  $("#page-no").val(pageNo);

  searchForm.attr("method", "get").submit();
}
