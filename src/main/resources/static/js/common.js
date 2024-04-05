$().ready(function () {
  $("a.deleteMe").on("click", function () {
    $.get("/ajax/member/delete-me", function (response) {
      var next = response.data.next;
      location.href = next;
    });
  });
});

function search(pageNo) {
  var searchForm = $("#search-form");
  //var listSize = $("#list-size");
  $("#page-no").val(pageNo);

  searchForm.attr("method", "get").submit();
}
