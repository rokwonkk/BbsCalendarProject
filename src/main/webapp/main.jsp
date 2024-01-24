<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String main = (String) request.getAttribute("main");
    if (main == null || main.isEmpty()) {
        main = "home";
    }
%>

<html>
<head>
    <title>Title</title>
</head>
<style>
    body {
        margin: 0 auto;
        width: 1000px;
    }
    header {
        margin: 0 auto;
        text-align: center;
        height: 100px;
        background-color: red;
    }
    nav {
        padding: 10px;
        background-color: yellow;
    }
    footer{
        margin: 0 auto;
        text-align: center;
        height: 100px;
        background-color: lightblue;
    }
</style>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<nav>
    <jsp:include page="navi.jsp"/>
</nav>
<main>
    <jsp:include page='<%=main + ".jsp"%>'/>
</main>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>