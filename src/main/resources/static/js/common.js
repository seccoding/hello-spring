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
  var aside = $("#aside");

  if (aside && aside.length > 0) {
    $.get("/ajax/menu/list", function (response) {
      var menuList = response.data.menu;

      var menuListDom = $("<ul></ul>");
      menuListDom.css({ "list-style-type": "none", padding: 0 });

      for (var i in menuList) {
        var menu = menuList[i];

        var menuId = menu.menuId;
        var parentMenuId = menu.parentMenuId;
        var menuUrl = menu.menuUrl;

        var menuDom = $("<li></li>");
        menuDom.data("menu-id", menuId);
        menuDom.css({ "font-weight": "bold", cursor: "pointer" });
        menuDom.data("url", menuUrl);
        menuDom.data("parent-menu-id", parentMenuId);

        if (parentMenuId) {
          menuDom.css({
            "font-weight": "normal",
            "padding-left": "1rem",
          });
        }

        menuDom.text(menu.menuName);

        menuDom.on("click", function () {
          var url = $(this).data("url");

          if (url) {
            location.href = $(this).data("url");
          }
        });

        menuListDom.append(menuDom);
      }

      aside.append(menuListDom);
    });
  }

  $("#checked-all").on("change", function () {
    // 영향을 받을 다른 체크박스를 조회한다.
    var targetClass = $(this).data("target-class");

    // checked-all의 체크 상태를 가져온다.
    // 체크가 되어있다면 true, 아니라면 false
    var isChecked = $(this).prop("checked");

    $("." + targetClass).prop("checked", isChecked);
  });

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
