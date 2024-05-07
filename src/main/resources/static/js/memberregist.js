$().ready(function () {
  var alertDialog = $(".alert-dialog");

  // 배열.
  if (alertDialog && alertDialog.length > 0) {
    alertDialog[0].showModal();
  }

  $("#email").on("keyup", function () {
    // 서버에게 사용할 수 있는 이메일인지 확인 받는다.
    $.get(
      "/ajax/member/regist/available",
      { email: $(this).val() },
      function (response) {
        var available = response.available;
        if (available) {
          $("#email").addClass("available");
          $("#email").removeClass("unusable");
          $("#btn-regist").removeAttr("disabled");
        } else {
          $("#email").addClass("unusable");
          $("#email").removeClass("available");
          $("#btn-regist").attr("disabled", "disabled");
        }
      }
    );
  });

  $("#btn-login").on("click", function () {
    $(".error").remove();
    $("div.grid").removeAttr("style");

    $("#loginForm")
      .attr({
        action: "/member/login-proc",
        method: "post",
      })
      .submit();
  });
});
