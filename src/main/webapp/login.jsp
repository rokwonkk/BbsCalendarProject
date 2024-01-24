<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- 쿠키를 사용할 수 있는 자바스크립트 -->
    <script src="http://lab.alexcican.com/set_cookies/cookie.js"></script>

    <style>
        html {
            font-family: "Noto Sans KR", sans-serif;
            font-size: 16px;
            font-weight: 400;
        }

        * {
            box-sizing: border-box;
        }

        form {
            margin: 16px auto;
            width: 360px;
        }

        label {
            color: #2c2c2c;
            display: block;
            margin: 16px 0 8px;
        }

        input {
            border: 1px solid #d1d1d1;
            color: #000000;
            display: block;
            font-size: 16px;
            line-height: 24px;
            margin: 8px 0;
            padding: 16px 24px;
            width: 100%;
        }

        .login-title {
            color: #4e4e4e;
            font-size: 36px;
            font-weight: 700;
            margin: 24px 0;
            text-align: center;
        }

        button, #login {
            background: #2c2c2c;
            border: none;
            color: #ffffff;
            display: block;
            font-size: 18px;
            font-weight: 700;
            margin: 24px 0;
            padding: 16px;
            width: 100%;
            cursor: pointer;
        }

        button {
            margin: 8px auto;
            width: 360px;
        }

        #chk_save_id {
            width: 5%;
            display: inline;
        }

        hr {
            width: 360px;
            margin: 0 auto;
        }
    </style>
</head>
<body>

<h1 class="login-title">로그인</h1>
<div class="center">
    <form action="./member" method="post">
        <input type="hidden" name="param" value="loginAf">

        <label for="id">아이디</label>
        <input id="id" name="id" placeholder="아이디를 기입해 주세요"/>

        <label for="pw">비밀번호</label>
        <input id="pw" name="pw" type="password" placeholder="비밀번호를 기입해 주세요">
        <input type="submit" id="login" name="login" value="로그인">

        id 저장 <input type="checkbox" id="chk_save_id">

        <a style="float: right; margin-right: 5px" href="./member?param=regi">회원가입</a>
    </form>
</div>
<hr/>
<button>구글</button>
<button>네이버</button>
<button>카카오</button>
<script>
    /*
        cookie : client저장, id저장, pw저장, String 자료형 저장
        session : server저장, login 정보
    */
    let user_id = $.cookie("user_id");	                    // 쿠키에서 id를 산출
    if (user_id != null) {	                                // 쿠키에 저장한 id가 있다
        $("#id").val(user_id);
        $("#chk_save_id").prop("checked", true);
    }

    $("#chk_save_id").click(function () {

        if ($("#chk_save_id").is(":checked") === true) {	    // 체크가 되었을 때

            if ($("#id").val().trim() === "") {	            // 빈문자였을 때
                alert('id를 입력해 주십시오');
                $("#chk_save_id").prop("checked", false);	// 체크박스를 off
            } else {	// cookie에 저장
                $.cookie("user_id", $("#id").val().trim(), {expires: 7, path: '/'});
            }
        } else {
            $.removeCookie("user_id", {path: '/'});
        }
    });
</script>
</body>
</html>