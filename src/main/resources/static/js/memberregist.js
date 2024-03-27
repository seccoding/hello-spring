$().ready(function () {
  var alertDialog = $(".alert-dialog");

  // 배열.
  if (alertDialog && alertDialog.length > 0) {
    alertDialog[0].showModal();
  }

  $("#email").on("keyup", function () {
    // 서버에게 사용할 수 있는 이메일인지 확인 받는다.
    $.get(
      "/member/regist/available",
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

    $.post(
      "/member/login",
      {
        email: $("#email").val(),
        password: $("#password").val(),
      },
      function (response) {
        var errors = response.data.errors;
        var errorMessage = response.data.errorMessage;
        var next = response.data.next;

        // 파라미터 유효성 검사에 실패했을 경우.
        if (errors) {
          for (var key in errors) {
            var errorDiv = $("<div></div>");
            errorDiv.addClass("error");

            var values = errors[key];
            for (var i in values) {
              var errorValue = values[i];
              var error = $("<div></div>");
              error.text(errorValue);

              errorDiv.append(error);
            }

            $("input[name=" + key + "]").after(errorDiv);
          }

          if (errors.email && errors.password) {
            var emailFailCount = errors.email.length;
            var passwordFailCount = errors.password.length;

            $("div.grid").css({
              "grid-template-rows":
                "28px " +
                21 * emailFailCount +
                "px 28px " +
                21 * passwordFailCount +
                "px 1fr",
            });
          } else if (errors.email) {
            var emailFailCount = errors.email.length;
            $("div.grid").css({
              "grid-template-rows":
                "28px " + 21 * emailFailCount + "px 28px 1fr",
            });
          } else if (errors.password) {
            var passwordFailCount = errors.password.length;
            $("div.grid").css({
              "grid-template-rows":
                "28px 28px " + 21 * passwordFailCount + "px 1fr",
            });
          }
        }

        // 파라미터 유효성 검사는 패스
        // 이메일이나 패스워드가 잘못된 경우.
        if (errorMessage) {
          var errorDiv = $("<div></div>");
          errorDiv.addClass("error");
          errorDiv.text(errorMessage);

          $("#loginForm").after(errorDiv);
        }

        // 정상적으로 로그인에 성공한 경우.
        if (next) {
          location.href = next;
        }

        /* 
        response = {
            data: {
                errors: {
                    "email": []
                },
                errorMessage: "",
                next: "/board/list"
            }
        }
        */
      }
    );
  });
});
