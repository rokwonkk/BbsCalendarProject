<%@ page import="dto.MemberDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberDto login = (MemberDto) session.getAttribute("login");
    if (login == null || login.getId().isEmpty()) {
        session.setAttribute("prevView", "bbsWrite");
%>
<script>
    alert("로그인해 주십시오");
    location.href = "./member?param=login";
</script>
<%
    }
%>
<html>
<head>
    <title>글쓰기</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .mytable {
            width: 800px;
            border: 1px solid lightgray;
        }

        th {
            border: 1px solid lightgray;
        }
    </style>
</head>
<body>
<h1>글쓰기</h1>
<div align="center">
    <form action="./bbs" method="post">
        <input type="hidden" name="param" value="bbsWriteAf">
        <table class="table mytable">
            <col width="200"/>
            <col width="400"/>
            <tr>
                <th>id</th>
                <td>
                    <% if (login != null && !login.getId().isEmpty()) { %>
                    <input type="text" class="form-control" name="id" size="50px" value="<%=login.getId()%>" readonly>
                    <% } %>
                </td>
            </tr>
            <tr>
                <th>제목</th>
                <td><input type="text" class="form-control" name="title" size="50px"></td>
            </tr>
            <tr>
                <th>내용</th>
                <td><textarea name="content" class="form-control" cols="50" rows="20"
                              placeholder="내용을 작성해주세요"></textarea></td>
            </tr>
        </table>
        <br/>
        <button type="submit" class="btn btn-primary">글작성완료</button>
    </form>
</div>
</body>
</html>